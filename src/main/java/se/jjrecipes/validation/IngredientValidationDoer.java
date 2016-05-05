package se.jjrecipes.validation;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import se.jjrecipes.util.IngredientFromJSON;
import se.jjrecipes.util.IngredientUtils;
import se.jjrecipes.util.JSONConverter;

public class IngredientValidationDoer extends AbstractValidationDoer {

	@Override
	public boolean validate(Object input) { 
		if (!(input instanceof String[])) return false;
		
		String [] in = (String[]) input;
		String str;
		if (IngredientUtils.isSingleObject(in)) {
			str = Arrays.toString(in); 
			str = str.replace("[", "").replace("]", "");
			IngredientFromJSON pojo = JSONConverter.toIngredientPojo(str);
			if(pojo == null) return false;
			
			if (!nameValid(pojo.getName()) || !amountValid(pojo.getAmount()) || !measureTypeValid(pojo.getMeasureType()))
				return false;
			
		} else {
			for (String row : in) {
				IngredientFromJSON pojo = JSONConverter.toIngredientPojo(row);
				if(pojo == null) return false;

				if (!nameValid(pojo.getName()) || !amountValid(pojo.getAmount()) || !measureTypeValid(pojo.getMeasureType()))
					return false;
			}
		}
		return true;
	}

	private boolean measureTypeValid(Long measureType) {
		if (measureType < 1) return false;
		return true;
	}

	private boolean amountValid(Integer amount) {
		if (amount < 0) return false;
		return true;
	}

	private boolean nameValid(String name) {
		if (name.length() >= 1 && name.length() <= 45)
			return true;
		return false;
	}
	
}
