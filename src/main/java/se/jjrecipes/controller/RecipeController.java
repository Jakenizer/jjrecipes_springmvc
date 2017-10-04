package se.jjrecipes.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import se.jjrecipes.dao.IngredientDao;
import se.jjrecipes.dao.RecipeDao;
import se.jjrecipes.dao.TagDao;
import se.jjrecipes.entity.Ingredient;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.exception.ImageException;
import se.jjrecipes.exception.JJRuntimeException;
import se.jjrecipes.form.RecipeForm;
import se.jjrecipes.function.Functions;
import se.jjrecipes.response.RecipeResponse;
import se.jjrecipes.util.GeneralUtil;
import se.jjrecipes.util.IngredientFromJSON;
import se.jjrecipes.util.JSONConverter;

import com.google.common.collect.Lists;
 
@Controller
public class RecipeController {
	private static Logger logger = LoggerFactory.getLogger(RecipeController.class); //slf4j logger
	
	@Autowired
	private RecipeDao recipeDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private IngredientDao ingredientDao;
	
	@RequestMapping(value = "/testRecipes", method = RequestMethod.GET)
	public String initUsersAndRoles(Model model) {
	/*	for (int i = 60; i < 90; i++) {
			RecipeForm r = new RecipeForm();
			r.setName("testRecept"+i);
			r.setContent("testinnehåll"+i);
			createNewRecipe(r);
		}*/
		
		return "redirect:/user/list_and_search/1";
	}
	
	/*
	@RequestMapping(value = "/initUsers", method = RequestMethod.GET)
	public String initUsersAndRoles(Model model) {
		User u1 = new User();
		u1.setFirstName("Jacob");
		u1.setLastName("Flarup");
		u1.setUserName("jackflamp");
		u1.setPassword("skruttan15");
		u1.setEnabled((byte) 1);
		UserData.addUser(u1);
		
		UserRole ur = new UserRole();
		ur.setRole("ROLE_USER");
		ur.setUsername("jackflamp");
		UserRoleData.addUserRole(ur);
		
		UserRole ur2 = new UserRole();
		ur2.setRole("ROLE_ADMINISTRATOR");
		ur2.setUsername("jackflamp");
		UserRole userRole = UserRoleData.addUserRole(ur2);
		
		model.addAttribute("ok", "user and roles added: " + userRole.getRole() +  " : " + userRole.getUsername());
		return "init_measuretypes";
		
	}*/
	
	@ModelAttribute
	public void alwaysAdd(Model model) {
		model.addAttribute("isAdmin", GeneralUtil.isAdmin());
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedin = auth.getName();
		logger.info("User with name: '" + loggedin + "' accessed default link");
		ModelAndView mv;
		if (auth.getPrincipal() == "anonymousUser")
			mv = new ModelAndView("redirect:/user/loginx");
		else
			mv = new ModelAndView("/user/home");
		return mv;
	}
	
	@RequestMapping(value = "/loginx", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/user/login");

		return mv;
	}

	@RequestMapping(value = "/loginfail", method = RequestMethod.GET)
	public ModelAndView loginfailed() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("error", "Fel användarnamn eller lösenord");
		/*String a = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		String b = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
		String c = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
		String d = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
		String e = System.getenv("OPENSHIFT_APP_NAME");
		mv.addObject("host", a);
		mv.addObject("port", b);
		mv.addObject("usname", c);
		mv.addObject("passw", d);
		mv.addObject("appname", e);*/

		return mv;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
 
	@RequestMapping(value = "/testar", method = RequestMethod.GET)
	public String testar() {
		return "";
	}

	@RequestMapping(value = "/user/list_and_search/{page}", method = RequestMethod.GET)
	public ModelAndView listAndSearch(@PathVariable(required = false) int page) {
		//Set<Recipe> recipes = RecipeData.sortedList();
		final int pageSize = 30;
		//List<Recipe> recipes = recipeDao.allRecipes();
		
		long count = recipeDao.countAll();
				
		int startPos = count <= pageSize ? 0 : (page - 1) * pageSize;
		//int endPos = (int) (startPos + 30 > count ? count : startPos + 30);
		
		List<Recipe> recipes2 = recipeDao.paginatedRecipes(startPos, 30);
		
		//List<Recipe> paged = recipes.subList(startPos, endPos);
		
		int chop = (int)Math.ceil(recipes2.size() / 2d);
		List<Recipe> row1 = recipes2.subList(0, chop);
		List<Recipe> row2 = recipes2.subList(chop, recipes2.size());
		
		int paginationSize = (int)Math.ceil(count / 30d);
		ModelAndView mv = new ModelAndView("/user/list_and_search");
		mv.addObject("row1", row1);
		mv.addObject("row2", row2);
		mv.addObject("paginationSize", paginationSize);
		return mv;
	}
	
	
	@RequestMapping(value = "/user/searchRecipe", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(name = "inputText", required = true) String text, 
							   @RequestParam(name = "page", required  = false, defaultValue = "1") Integer page) {	
		final int pageSize = 30;
		long count = recipeDao.countAllSearched(text);
		int startPos = count <= pageSize ? 0 : (page - 1) * pageSize;
		
		List<Recipe> recipes = recipeDao.paginatedSearch(text, startPos, pageSize);
				
		int chop = (int)Math.ceil(recipes.size() / 2d);
		List<Recipe> row1 = recipes.subList(0, chop);
		List<Recipe> row2 = recipes.subList(chop, recipes.size());
		
		int paginationSize = (int)Math.ceil(count / 30d);
		ModelAndView mv = new ModelAndView("/user/list_and_search");
		mv.addObject("row1", row1);
		mv.addObject("row2", row2);
		mv.addObject("paginationSize", paginationSize);
		return mv;
	}
	
	@RequestMapping(value="/user/createModifyRecipe", method=RequestMethod.POST)
	public  ModelAndView createModify(@Valid @ModelAttribute RecipeForm form, BindingResult result, RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();		
		
		if(result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fe : fieldErrors) {
				redirectAttrs.addFlashAttribute(fe.getField() + "_error", fe.getDefaultMessage());
			}
			mv.setViewName("redirect:/user/create_recipe");
			return mv;
		}
		
		try {
			Recipe recipe;
			if (form.getId() == null) {
				recipe = createNewRecipe(form);
			} else {
				//TODO: Kolla att Ändringar har skett, annars uppdatera ej
				recipe = updateRecipe(form);
			}
    		mv.setViewName("redirect:/user/recipe?id="+recipe.getId());
            return mv;
        } catch (HibernateException e) {
        	mv.addObject("message", "Error while saving recipe.");
        	mv.addObject("returnpage", "create_modify_recipe");
        	mv.addObject("exception", e.getMessage());
            mv.setViewName("error");
        	return mv;
        }
    }
	
	@RequestMapping(value="/recipe/{id}/edit", method=RequestMethod.POST)
	public  ModelAndView getModify(@PathVariable(required = true) long id) {
		ModelAndView mv = new ModelAndView("/user/create_modify_recipe");
		Recipe recipe = recipeDao.getRecipe(id);
		RecipeResponse recipeData = new RecipeResponse(recipe);
		mv.addObject("recipeData", recipeData);
		
		TreeSet<Tag> tags = tagDao.getSortedList();
		mv.addObject("tags", tags);
		return mv;
	}
	
	@RequestMapping("/user/create_recipe")
	public ModelAndView getCreateRecipe() {
		TreeSet<Tag> tags = tagDao.getSortedList();

		ModelAndView mv = new ModelAndView("/user/create_modify_recipe");
		mv.addObject("tags", tags);
		return mv;
	}
	
	@RequestMapping("/user/create_recipe2")
	public ModelAndView getCreateRecipeNew() {
		TreeSet<Tag> tags = tagDao.getSortedList();

		ModelAndView mv = new ModelAndView("/user/create_modify_recipe2");
		mv.addObject("tags", tags);
		return mv;
	}
	
	/*
	@RequestMapping(value = "/recipe/{id}", method={RequestMethod.GET})
	public ModelAndView getRecipe(@PathVariable(required = true) Long id) {*/
	@RequestMapping(value = "/recipe", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView getRecipe(@RequestParam("id") Long id){
	
		ModelAndView mv = new ModelAndView("/user/view_recipe");
		
		Recipe recipe = recipeDao.getRecipe(id);
		
		//Recipe recipe = RecipeData.get(Recipe.class, id);
		
		//TODO: hantera alla fel. nullpointers och filinlÃ¤sningsfel
		RecipeResponse resp = new RecipeResponse(recipe);
		if (resp.getImage() == null) {
			URL resource = RecipeController.class.getClassLoader().getResource("image/recipe-default.jpg");
			InputStream inStream; 
			try {
				inStream = resource.openStream();
				byte[] byteArray = IOUtils.toByteArray(inStream);
				resp.setImage(byteArray);
			} catch (Exception e) {
				mv.addObject("message", "Fel vid hämtning av defaultbild till recept");
				mv.addObject("exception", e.getMessage());
				mv.addObject("returnpage", "/JJRecipes/list_and_search");
				mv.setViewName("/user/error");
				return mv;
			} 
		}
		mv.addObject("responseData", resp);
		return mv;
	}
	/*
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
		
		
		
		return resp;
		//return new ModelAndView("NewFile");
		
		
	}*/
	
	private Recipe createNewRecipe(RecipeForm form) {
		MultipartFile file = form.getFile();
		String name = form.getName();
		String content = form.getContent();
		List<String> tags = Arrays.asList(form.getTags());

		Recipe recipe = new Recipe();
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
		if (imageInBytes!= null && imageInBytes.length > 0) {
			recipe.setImage(imageInBytes);
		}
		
		recipe.setName(name);
		recipe.setContent(content);
		
		
		List<String> ing = new ArrayList<String>(Arrays.asList(form.getIngredients()));
		List<IngredientFromJSON> ingredients = 
			ing.stream()
				.map(input -> stringToJson(input))
				.collect(Collectors.toList());
		
		Set<Ingredient> ins = 
			ingredients.stream()
				.map(jing -> jsonToIngredient(jing, recipe)).collect(Collectors.toSet());

		recipe.setIngredients(ins);

		//create or get Tags and add
		Set<Tag> taggers = new HashSet<Tag>();
		for (String in : tags) {
			Tag aTag = tagDao.getTag(Long.valueOf(in));
			if (aTag == null) {
				aTag = tagDao.addTag(in);
			} 
			taggers.add(aTag);
		}
		recipe.setTags(taggers);

		return recipeDao.add(recipe);
	}
	
	private Ingredient jsonToIngredient(IngredientFromJSON jing, Recipe r) {
		Ingredient in = new Ingredient();
		in.setContent(jing.getContent());
		in.setRecipe(r);
		return in;
	}
	
	private IngredientFromJSON stringToJson(String input) {
		IngredientFromJSON pojo = new IngredientFromJSON();
		if (NumberUtils.isNumber(input)) {
			pojo.setId(Long.valueOf(input));
		} else {
			pojo = JSONConverter.toIngredientPojo(input);
		}
		return pojo;
	}
	
	private Recipe updateRecipe(RecipeForm form) {
		Recipe recipe = recipeDao.getRecipe(form.getId());
		if (recipe == null) throw new IllegalArgumentException("Recipe with Id " + form.getId() + " not found.");
			
		recipe.setName(form.getName());
		recipe.setContent(form.getContent());
		recipe.setImage(recipe.getImage());
		
		//compare the collections tags and ingredients
		List<String> newTagsIn = Arrays.asList(form.getTags());
		Set<Tag> inTags = newTagsIn.stream().map(in -> idToTag(in)).collect(Collectors.toSet());
		//Iterable<Long> longs = Iterables.transform(newTagsIn, Functions.stringsToLongs);
		//HashSet<Tag> InTags = Sets.newHashSet(Iterables.transform(longs, Functions.idsToTags));
		recipe.setTags(inTags);

		List<IngredientFromJSON> ingredients = Lists.transform(Arrays.asList(form.getIngredients()), Functions.jsonToJava);

		if (ingredients != null) {
			Set<Ingredient> ins = new HashSet <Ingredient>();
			Set<Ingredient> staying = new HashSet<Ingredient>();
			for (IngredientFromJSON jing : ingredients) {
				Ingredient in = new Ingredient();

				if (jing.getId() != null) {
					in = ingredientDao.get(jing.getId());
					staying.add(in);
				} else {
					in.setContent(jing.getContent());
				}
				in.setRecipe(recipe);
				ins.add(in);
			}
			removeOrphanedIngredients(recipe.getIngredients(), staying);
			recipe.setIngredients(ins);
		}
		
		Recipe updatedRecipe = recipeDao.updateRecipe(recipe);
		return updatedRecipe;
	}
	
	private Tag idToTag(String id) {
		return tagDao.getTag(Long.valueOf(id));
	}
	
	private void removeOrphanedIngredients(Set<Ingredient> existing, Set<Ingredient> newSet) {
		for (Ingredient ingredient : existing) {
			if (!newSet.contains(ingredient)) {
				ingredient.setRecipe(null);
				ingredientDao.delete(ingredient.getId());
			}
		}
	}
	
	//--------------- Exception handling -------------------------------//
	@ExceptionHandler(JJRuntimeException.class)
	public ModelAndView handleJJRuntime(JJRuntimeException ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("errorType", ex.getClass().getName());
		model.addObject("message", ex.getMessage());
		model.addObject("returnpage", "list_and_search");
 
		return model;
	}
	
	@ExceptionHandler(ImageException.class)
	public ModelAndView handleImageException(ImageException ex) {

		ModelAndView model = new ModelAndView("error");
		model.addObject("errorType", ex.getType());
		model.addObject("message", ex.getMessage());
		model.addObject("returnpage", "list_and_search");
 
		return model;
	}
}
