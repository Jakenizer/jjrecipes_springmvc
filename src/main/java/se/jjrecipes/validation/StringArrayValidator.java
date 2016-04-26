package se.jjrecipes.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.jjrecipes.controller.TagController;

public class StringArrayValidator implements ConstraintValidator<StringArrayInputFormat, String[]> {
	private static Logger logger = LoggerFactory.getLogger(TagController.class); //slf4j logger
	private Class<AbstractValidationDoer> validationFormat;
	private String message; 
	
	@Override
	public void initialize(StringArrayInputFormat constraintAnnotation) {
		this.validationFormat = (Class<AbstractValidationDoer>) constraintAnnotation.classForValidation();
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(String[] values, ConstraintValidatorContext constraintContext) {
		
		if (values == null) return true;
		
		boolean valid = false;
		try {
			AbstractValidationDoer doer = validationFormat.newInstance();
			valid = doer.validate(values);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Error when validating String array", e);
		}
		
		if (!valid) {
			constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate( message ).addConstraintViolation();
            return false;
		}
		
		
		return true;
	}
	
	 

}
