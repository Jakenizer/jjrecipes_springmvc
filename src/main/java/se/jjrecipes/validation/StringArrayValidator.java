package se.jjrecipes.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.jjrecipes.exception.JJRuntimeException;

public class StringArrayValidator implements ConstraintValidator<StringArrayInputFormat, String[]> {
	private static Logger logger = LoggerFactory.getLogger(StringArrayValidator.class); //slf4j logger
	private Class<AbstractValidationDoer> validator;
	private String message; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(StringArrayInputFormat constraintAnnotation) {
		this.validator = (Class<AbstractValidationDoer>) constraintAnnotation.classForValidation();
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(String[] values, ConstraintValidatorContext constraintContext) {
		
		if (values == null) return true;
		
		boolean valid = false;
		try {
			AbstractValidationDoer doer = validator.newInstance();
			valid = doer.validate(values);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Error when validating String array", e);
			throw new JJRuntimeException(e);
		}
		
		if (!valid) {
			constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate( message ).addConstraintViolation();
		}
		
		return valid;
	}
}
