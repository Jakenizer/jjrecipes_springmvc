package se.jjrecipes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.backing.RecipeBacking;
import se.jjrecipes.data.TagData;
import se.jjrecipes.data.UserData;
import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.entity.Ingredient.MeasureType;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.entity.User;
import se.jjrecipes.hibernate.HibernateUtil;
 
@Controller
public class RecipeController {

	
	@RequestMapping(value="/createRecipe", method=RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("name") String name, 
    		@RequestParam(value = "ingredient", required = false) String[] ingredients, 
    		@RequestParam("content") String content,
            @RequestParam("fileUpload") MultipartFile file){
		ModelAndView mv = new ModelAndView();
        try {

        	Recipe recipe = new Recipe();
        	recipe.setName(name);
        	recipe.setContent(content);
        	Set<Ingredient> ins = new HashSet<Ingredient>();
        	for (int i = 0; i < ingredients.length; i++) {
        		Ingredient in = new Ingredient();
        		in.setAmount(10);
        		in.setName(ingredients[i]);
        		in.setMeasureType(MeasureType.MSK);
        		in.setRecipe(recipe);
				ins.add(in);
			}
        	recipe.setIngredients(ins);
            if (!file.isEmpty()) {
            	byte[] bytes = file.getBytes();
            	recipe.setImage(bytes);
            }
    		SessionFactory sf = HibernateUtil.getSessionFactory();
    		Session ses = sf.openSession();
    		ses.beginTransaction();
    		
    		Long id = (Long) ses.save(recipe);
    		
            //Get image from database
    /*		Recipe avatar2 = (Recipe)ses.get(Recipe.class, id);
            byte[] bAvatar = avatar2.getImage();
     
            try{
                FileOutputStream fos = new FileOutputStream("D:\\tankat\\test.jpg"); 
                fos.write(bAvatar);
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    		*/
    		ses.getTransaction().commit();
    		
    		
            mv.setViewName("recipes");
            return mv;
        } catch (HibernateException | IOException e) {
        	mv.addObject("message", "Det bidde fel.");
        	mv.addObject("returnpage", "create_modify_recipe");
        	mv.addObject("exception", e.getMessage());
            mv.setViewName("error");
        	return mv;
        }
    }
	
	@RequestMapping("/create_modify_recipe")
	public ModelAndView recipe() {
		MeasureType[] types = Ingredient.MeasureType.values();
		List<String> typeList = new ArrayList<String>();
		for (MeasureType measureType : types) {
			typeList.add(measureType.getText());
		}
		ModelAndView mv = new ModelAndView("create_modify_recipe");
		mv.addObject("measuretypes", typeList);
		return mv;
	}
	
	@ModelAttribute
	public void prepareRecipe(RecipeBacking recept) {
		
	}
	
//	@RequestMapping("/tags") 
//	public ModelAndView tags() {
//		TreeSet<Tag> tags = TagData.getSortedList();
//		ModelAndView mv = new ModelAndView("tags");
//		mv.addObject("tags", tags);
//		return mv;
//	}
	
//	@RequestMapping("/nyTagg")
//	public ModelAndView newTag(@RequestParam("newTag") String tagName) {
//		SessionFactory sf = HibernateUtil.getSessionFactory();
//		Session ses = sf.openSession();
//		ses.beginTransaction();
//		
//		Tag tag = new Tag();
//		tag.setName(tagName);
//		ses.save(tag);
//		
//		ses.getTransaction().commit();
//		ModelAndView mv = new ModelAndView("redirect:tags");
//		return mv;
//	}
	
	@RequestMapping("/test")
	public ModelAndView testa(
			@RequestParam(value = "spunk", required = true, defaultValue = "one") String name) {
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session ses = sf.openSession();
		ses.beginTransaction();
		
		
		/*User u = new User();
		u.setFirstName(name);
		u.setLastName("svensson");*/
		
		Tag tag = new Tag();
		tag.setName("paj");
		ses.save(tag);
		/*
		Recipe r1 = new Recipe();
		r1.setName("meaty");
		r1.setContent("stek den");
		Ingredient i1 = new Ingredient();
		i1.setName("kalvfile");
		i1.setAmount(2);
		i1.setMeasureType(MeasureType.KILO);
		i1.setRecipe(r1);
		Set<Ingredient> ins = new HashSet<Ingredient>();
		ins.add(i1);
		r1.setIngredients(ins);
		

		Set<Tag> tags = new HashSet<Tag>();
		tags.add(tag);
		
		r1.setTags(tags);
		
		ses.save(r1);*/
		//Long inte = (Long) ses.save(u);
		
		ses.getTransaction().commit();
		
		//User user = UserData.findUser(3L);
		
		
				
		/*ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", "mopp");
		mv.addObject("name", name);*/
		ModelAndView mv = new ModelAndView("tags");
		return mv;
	}
}