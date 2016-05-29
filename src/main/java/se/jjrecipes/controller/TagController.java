package se.jjrecipes.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import javax.validation.Valid;

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

import se.jjrecipes.data.RecipeData;
import se.jjrecipes.data.TagData;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.form.TagForm;
import se.jjrecipes.function.Functions;
import se.jjrecipes.util.GeneralUtil;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TagController {
	private static Logger logger = LoggerFactory.getLogger(TagController.class); //slf4j logger

	@ModelAttribute
	public void alwaysAdd(Model model) {
		model.addAttribute("isAdmin", GeneralUtil.isAdmin());
	}
	
	@RequestMapping("/tags") 
	public ModelAndView tags() {
		TreeSet<Tag> tags = TagData.getSortedList();
		ModelAndView mv = new ModelAndView("tags");
		mv.addObject("tags", tags);
		logger.info("READING TAGS");
		return mv;
	}

	@RequestMapping("/nyTagg")
	public ModelAndView newTag(@Valid TagForm form, BindingResult result, RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView("redirect:tags");
		if(result.hasErrors()) {
			FieldError fieldError = result.getFieldError();
			redirectAttrs.addFlashAttribute("error", fieldError.getDefaultMessage());
		} else {
			TagData.addTag(form.getNewTag());
		}

		return mv;
	}
	
	@RequestMapping("/searchTag")
	public ModelAndView searchByTags(@RequestParam("selectedValues") String[] selectedValues) {
		List<Tag> tagsByNames = TagData.getTagsByNames(Arrays.asList(selectedValues));
		Iterable<Long> tagIds = Iterables.transform(tagsByNames, Functions.tagToIds);
		ArrayList<Long> arrayList = Lists.newArrayList(tagIds);
		List<Recipe> recipes = RecipeData.findRecipesByTags(arrayList);
		ModelAndView mv = new ModelAndView("list_and_search");
		mv.addObject("recipes", recipes);
		return mv;
	}
	
	@RequestMapping(value="/removeTag", method = RequestMethod.POST)
	public ModelAndView removeTag(@RequestParam("tagId") long id) {
		TagData.deleteById(id);
		return new ModelAndView("redirect:tags");
	}
}
