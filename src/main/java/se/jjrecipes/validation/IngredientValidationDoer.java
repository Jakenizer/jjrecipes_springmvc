package se.jjrecipes.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import se.jjrecipes.util.IngredientFromJSON;
import se.jjrecipes.util.JSONConverter;

public class IngredientValidationDoer extends AbstractValidationDoer {

	@Override
	public boolean validate(Object input) { 
		if (!(input instanceof String[])) return false;
		
		String[] inputt = (String[]) input;
		
		for (String obj : inputt) {
			IngredientFromJSON pojo = new IngredientFromJSON();
			if (NumberUtils.isNumber(obj) && Long.valueOf(obj) <= 0) {
				return false;
			} else if (JSONConverter.isConvertable(obj)){
				pojo = JSONConverter.toIngredientPojo(obj);
				if (pojo == null || StringUtils.isBlank(pojo.getContent())) {
					return false;
				}
			}
			
		}
		return true;
	}
	
}
