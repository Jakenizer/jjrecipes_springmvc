package se.jjrecipes.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.jjrecipes.data.RecipeData;
import se.jjrecipes.data.TagData;
import se.jjrecipes.entity.Recipe;
import se.jjrecipes.entity.Tag;
import se.jjrecipes.function.Functions;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
		TagData.addTag(tagName);
		ModelAndView mv = new ModelAndView("tags");
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
		TagData.deleteById(Tag.class, id);
		
		return new ModelAndView("redirect:tags");
	}
}
