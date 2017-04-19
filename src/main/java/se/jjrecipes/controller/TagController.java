package se.jjrecipes.controller;

import java.util.List;
import java.util.TreeSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import se.jjrecipes.dao.RecipeDao;
import se.jjrecipes.dao.TagDao;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.form.TagForm;
import se.jjrecipes.util.GeneralUtil;

@Controller
public class TagController {
	private static Logger logger = LoggerFactory.getLogger(TagController.class); //slf4j logger

	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private RecipeDao recipeDao;
	
	@ModelAttribute
	public void alwaysAdd(Model model) {
		model.addAttribute("isAdmin", GeneralUtil.isAdmin());
	}
	
	@RequestMapping("/user/tags") 
	public ModelAndView tags() {
		TreeSet<Tag> tags = tagDao.getSortedList();
		ModelAndView mv = new ModelAndView("/user/tags");
		mv.addObject("tags", tags);
		logger.info("READING TAGS");
		return mv;
	}

	@RequestMapping("/nyTagg")
	public ModelAndView newTag(@Valid TagForm form, BindingResult result, RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView("redirect:/user/tags");
		if(result.hasErrors()) {
			FieldError fieldError = result.getFieldError();
			redirectAttrs.addFlashAttribute("error", fieldError.getDefaultMessage());
		} else {
			tagDao.addTag(form.getNewTag());
		}

		return mv;
	}
	
	@RequestMapping("/searchTag")
	public ModelAndView searchByTags(@RequestParam("selectedValues") List<Long> selectedValues) {
		/*List<Tag> tagsByIds = tagDao.getTagsByIds(selectedValues);
		Iterable<Long> tagIds = Iterables.transform(tagsByIds, Functions.tagToIds);
		ArrayList<Long> arrayList = Lists.newArrayList(tagIds);*/
		List<Recipe> recipes = recipeDao.findRecipesByTags(selectedValues);
		ModelAndView mv = new ModelAndView("/user/list_and_search");
		mv.addObject("recipes", recipes);
		return mv;
	}
	
	@RequestMapping(value="/removeTag", method = RequestMethod.POST)
	public ModelAndView removeTag(@RequestParam("tagId") long id) {
		tagDao.delete(id);
		return new ModelAndView("redirect:/user/tags");
	}
}
