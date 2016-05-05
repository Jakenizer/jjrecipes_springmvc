package se.jjrecipes.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public class IngredientUtils {
	
	public static boolean isSingleObject(String[] arr) {
		String str = Arrays.toString(arr);
		int count = StringUtils.countMatches(str, "name");

		if (count == 1) return true;
		
		return false;	
	}
	
	public static IngredientFromJSON getSingleObject(String[] arr) {
		String str = Arrays.toString(arr);
		return JSONConverter.toIngredientPojo(str);
	}
}
