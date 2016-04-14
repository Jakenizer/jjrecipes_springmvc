package se.jjrecipes.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.data.IngredientData;
import se.jjrecipes.data.RecipeData;
import se.jjrecipes.data.TagData;
import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.entity.Ingredient.MeasureType;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.exception.ImageException;
import se.jjrecipes.form.RecipeForm;
import se.jjrecipes.function.Functions;
import se.jjrecipes.response.RecipeResponse;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
 
@Controller
public class RecipeController {
	
	@Autowired
    private ServletContext servletContext;

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView mv;
		if (auth.getPrincipal() == "anonymousUser")
			mv = new ModelAndView("login");
		else
			mv = new ModelAndView("home");
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	@RequestMapping(value = "/loginfail", method = RequestMethod.GET)
	public ModelAndView loginfailed() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("error", "Fel användarnamn eller lösenord");
		return mv;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
 
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView mv = new ModelAndView("admin/admin");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
	    boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")) ? true : false;
	    if (!isAdmin)
	    	mv.addObject("error", "Du saknar behörighet till denna sida");
	    	
		mv.addObject("title", "Spring Security Hello World");
		mv.addObject("message", "This is protected page!"); 
		return mv;
 
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
	public  ModelAndView create(@ModelAttribute RecipeForm form) {
		ModelAndView mv = new ModelAndView();
		try {
			Recipe recipe;
			if (form.getId() == null) {
				recipe = createNewRecipe(form);
			} else {
				//TODO: Kolla att ändringar har skett, annars uppdatera ej
				recipe = updateRecipe(form);
			}
    		mv.setViewName("redirect:recipe?id="+recipe.getId());
            return mv;
        } catch (HibernateException e) {
        	mv.addObject("message", "Error while saving recipe.");
        	mv.addObject("returnpage", "create_modify_recipe");
        	mv.addObject("exception", e.getMessage());
            mv.setViewName("error");
        	return mv;
        }
    }
	
	@RequestMapping(value="/modify_recipe", method=RequestMethod.GET)
	public  ModelAndView getModify(@RequestParam(value = "recipeID", required = true) long id) {
		ModelAndView mv = new ModelAndView("create_modify_recipe");
		Recipe recipe = RecipeData.get(Recipe.class, id);
		RecipeResponse recipeData = new RecipeResponse(recipe);
		mv.addObject("recipeData", recipeData);
		
		TreeSet<Tag> tags = TagData.getSortedList();
		MeasureType[] types = Ingredient.MeasureType.values();
		List<String> typeList = new ArrayList<String>();
		for (MeasureType measureType : types) {
			typeList.add(measureType.getText());
		}
		mv.addObject("tags", tags);
		mv.addObject("measuretypes", typeList);
		return mv;
	}
	

	@RequestMapping(value="/modify_recipe", method=RequestMethod.POST)
	public ModelAndView modifyRecipe(@ModelAttribute RecipeForm form) {
		ModelAndView mv = new ModelAndView("view_recipe");
		updateRecipe(form);
		
		
		return mv;
	}
	
	@RequestMapping("/create_recipe")
	public ModelAndView getCreateRecipe() {
		TreeSet<Tag> tags = TagData.getSortedList();
		
		MeasureType[] types = Ingredient.MeasureType.values();
		List<String> typeList = new ArrayList<String>();
		for (MeasureType measureType : types) {
			typeList.add(measureType.getText());
		}
		ModelAndView mv = new ModelAndView("create_modify_recipe");
		mv.addObject("tags", tags);
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
	
	@RequestMapping(value = "/recipe", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView getRecipe(@RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("view_recipe");
		
		Recipe recipe = RecipeData.get(Recipe.class, Long.valueOf(id));
		
		//TODO: hantera alla fel. nullpointers och filinläsningsfel
		RecipeResponse resp = new RecipeResponse(recipe);
		if (resp.getImage() == null) {
			URL resource = RecipeController.class.getClassLoader().getResource("image/recipe-default.jpg");
			InputStream inStream;
			try {
				inStream = resource.openStream();
				byte[] byteArray = IOUtils.toByteArray(inStream);
				resp.setImage(byteArray);
			} catch (IOException e) {
				mv.addObject("message", "Fel vid hämtning av defaultbild till recept");
				mv.addObject("exception", e.getMessage());
				mv.addObject("returnpage", "/JJRecipes/list_and_search");
				mv.setViewName("error");
				return mv;
			} 
		}
		mv.addObject("responseData", resp);
		return mv;
	}
	
	@RequestMapping(value = "/loadRecipe", method=RequestMethod.POST)
	public @ResponseBody RecipeResponse loadRecipe(@RequestParam("redId") String recid){
		
		
		Recipe recipe = RecipeData.get(Recipe.class, Long.valueOf(recid));		
		RecipeResponse resp = new RecipeResponse(recipe);
		if (resp.getImage() != null)
			return resp;
		
		URL resource = RecipeController.class.getClassLoader().getResource("image/recipe-default.jpg");
        
        
		InputStream inStream;
		try {
			 inStream = resource.openStream();
			//inStream = new FileInputStream(resource);
			byte[] byteArray = IOUtils.toByteArray(inStream);
			resp.setImage(byteArray);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		//TODO: skapa eget json object och sätt värden från olika saker?
		return resp;
		//return new ModelAndView("NewFile");
		
		
	}
	
	private Recipe createNewRecipe(RecipeForm form) {
		MultipartFile file = form.getFile();
		String name = form.getName();
		String content = form.getContent();
		String[] ingredients = form.getIngredients();
		List<String> tags = Arrays.asList(form.getTags());

		byte[] imageInBytes = null;
		if (!file.isEmpty()) {
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			if (!extension.equals("jpg") && !extension.equals("png")) {
				throw new ImageException("Wrong file extension. jpg and png allowed, not " + extension);
			}

			BufferedImage reScaledImage;
			try {
				BufferedImage image = ImageIO.read(file.getInputStream());
				if(image.getWidth() == 300 && image.getHeight() == 300) {
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

		}
		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setContent(content);
		Set<Ingredient> ins = new HashSet<Ingredient>();
		for (int i = 0; i < ingredients.length; i++) {
			String[] ingParts = ingredients[i].split(";;");

			Ingredient in = new Ingredient();
			in.setName(ingParts[0]);
			in.setAmount(Integer.valueOf(ingParts[1]));
			in.setMeasureType(MeasureType.fromString(ingParts[2]));
			in.setRecipe(recipe);
			ins.add(in);
		}
		recipe.setIngredients(ins);

		//create or get Tags and add
		Set<Tag> taggers = new HashSet<Tag>();
		for (String in : tags) {
			Tag aTag = TagData.get(Tag.class, Long.valueOf(in));
			if (aTag == null) {
				aTag = TagData.addTag(in);
			} 
			taggers.add(aTag);
		}
		recipe.setTags(taggers);

		if (imageInBytes!= null && imageInBytes.length > 0) {
			recipe.setImage(imageInBytes);
		}

		return RecipeData.addRecipe(recipe);
	}
	
	private Recipe updateRecipe(RecipeForm form) {
		Recipe recipe = RecipeData.get(Recipe.class, form.getId());
		recipe.setName(form.getName());
		recipe.setContent(form.getContent());
		recipe.setImage(recipe.getImage());
		
		//compare the collections tags and ingredients
		List<String> newTagsIn = Arrays.asList(form.getTags());
		Iterable<Long> longs = Iterables.transform(newTagsIn, Functions.stringsToLongs);
		HashSet<Tag> InTags = Sets.newHashSet(Iterables.transform(longs, Functions.idsToTags));
		recipe.setTags(InTags);

		Set<Ingredient> ins = new HashSet<Ingredient>();
		String[] inins = form.getIngredients();
		Set<Ingredient> staying = new HashSet<Ingredient>();
		for (int i = 0; i < inins.length; i++) {
			Ingredient in = new Ingredient();
			
			String[] ingParts = inins[i].split(";;");
			if (ingParts.length == 1 && NumberUtils.isNumber(ingParts[0])) {
				in = IngredientData.get(Ingredient.class, Long.valueOf(ingParts[0]));
				staying.add(in);
			} else if (ingParts.length == 3){
				in.setName(ingParts[0]);
				in.setAmount(Integer.valueOf(ingParts[1]));
				in.setMeasureType(MeasureType.fromString(ingParts[2]));
			}
			in.setRecipe(recipe);
			ins.add(in);
		}
		removeOrphanedIngredients(recipe.getIngredients(), staying);
		recipe.setIngredients(ins);
		Recipe updatedRecipe = RecipeData.updateRecipe(recipe);
		return updatedRecipe;
	}
	
	private void removeOrphanedIngredients(Set<Ingredient> existing, Set<Ingredient> newSet) {
		for (Ingredient ingredient : existing) {
			if (!newSet.contains(ingredient)) {
				ingredient.setRecipe(null);
				IngredientData.deleteById(Ingredient.class, ingredient.getId());
			}
		}
	}
}