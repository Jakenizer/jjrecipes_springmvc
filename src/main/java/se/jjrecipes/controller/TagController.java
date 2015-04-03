package se.jjrecipes.controller;

import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.data.TagData;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.hibernate.HibernateUtil;

@Controller
public class TagController {
	
	@RequestMapping("/tags") 
	public ModelAndView tags() {
		TreeSet<Tag> tags = TagData.getSortedList();
		ModelAndView mv = new ModelAndView("tags");
		mv.addObject("tags", tags);
		return mv;
	}

	@RequestMapping("/nyTagg")
	public ModelAndView newTag(@RequestParam("newTag") String tagName) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		ses.beginTransaction();
		
		Tag tag = new Tag();
		tag.setName(tagName);
		ses.save(tag);
		
		ses.getTransaction().commit();
		ModelAndView mv = new ModelAndView("redirect:tags");
		return mv;
	}
	
	@RequestMapping("/removeTag")
	public ModelAndView removeTag(@RequestParam("tagItem") String[] tagId) {
		for (String idString : tagId) {
			Long id = Long.valueOf(idString);
			TagData.deleteById(Tag.class, id);
		}
		
		ModelAndView mv = new ModelAndView("redirect:tags");
		return mv;
	}
}
