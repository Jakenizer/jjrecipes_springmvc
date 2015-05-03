package se.jjrecipes.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.backing.RecipeBacking;
import se.jjrecipes.data.RecipeData;
import se.jjrecipes.data.TagData;
import se.jjrecipes.data.UserData;
import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.entity.Ingredient.MeasureType;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.entity.User;
import se.jjrecipes.exception.ImageException;
import se.jjrecipes.hibernate.HibernateUtil;
import se.jjrecipes.response.RecipeResponse;
 
@Controller
public class RecipeController {

//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public ModelAndView welcomePage() {
// 
//		ModelAndView model = new ModelAndView();
//		model.addObject("title", "out");
//		model.addObject("message", "This is outlog page!");
//		model.setViewName("j_spring_security_logout");
//		return model;
// 
//	}
 
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin/admin");
 
		return model;
 
	}

	@RequestMapping(value = "/list_and_search", method = RequestMethod.GET)
	public ModelAndView listAndSearch() {
		List<Recipe> recipes = RecipeData.listRecipes();
		ModelAndView mv = new ModelAndView("list_and_search");
		mv.addObject("recipes", recipes);
		return mv;
	}
	
	
	@RequestMapping(value = "/searchRecipe", method = RequestMethod.GET)
	public ModelAndView search() {
		
		ModelAndView mv = new ModelAndView("list_and_search");
		return mv;
	}
	
	@RequestMapping(value="/createRecipe", method=RequestMethod.POST)
   public ModelAndView handleFileUpload(@RequestParam("name") String name, 
    		@RequestParam(value = "ingredient", required = false) String[] ingredients, 
    		@RequestParam("content") String content,
            @RequestParam("fileUpload") MultipartFile file){
		
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		if (!extension.equals(".jpg") && !extension.equals(".png")) {
			throw new ImageException("Wrong file extension. jpg and png allowed, not " + extension);
		}
		
		BufferedImage reScaledImage;
		byte[] imageInBytes;
		try {
			BufferedImage image = ImageIO.read(file.getInputStream());
			if(image.getWidth() == 300) {
				reScaledImage = image;
			} else {
				reScaledImage = Scalr.resize(image, 300);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( reScaledImage, extension, baos );
			baos.flush();
			imageInBytes = baos.toByteArray();
			
		} catch (IOException e1) {
			throw new ImageException("Error while transforming image");
		}
		
		

		
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
	
	@ExceptionHandler(ImageException.class)
	public ModelAndView handleException(ImageException ex) {

		ModelAndView model = new ModelAndView("error");
		model.addObject("errorType", ex.getType());
		model.addObject("message", ex.getMessage());
		model.addObject("returnpage", "list_and_search");
 
		return model;
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
	
	@RequestMapping(value = "/test", method=RequestMethod.POST)
	public 	@ResponseBody RecipeResponse testa(@RequestParam("redId") String recid){
		Recipe recipe = RecipeData.findRecipe(Long.valueOf(recid));
		RecipeResponse resp = new RecipeResponse(recipe);
		//TODO: skapa eget json object och sätt värden från olika saker?
		return resp;
		//return new ModelAndView("NewFile");
		
		
	}
}