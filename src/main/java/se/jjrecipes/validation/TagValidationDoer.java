package se.jjrecipes.validation;

import org.apache.commons.lang3.math.NumberUtils;

public class TagValidationDoer extends AbstractValidationDoer {

	@Override
	boolean validate(Object input) {
		if (!(input instanceof String[])) return false;
		
		String [] in = (String[]) input;
		for (String row : in) {
			if(!NumberUtils.isNumber(row) || (Long.valueOf(row) < 1)) return false;
		}
		
		return true;
	}
}
