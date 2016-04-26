package se.jjrecipes.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target (ElementType.FIELD)
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = StringArrayValidator.class)
public @interface StringArrayInputFormat {
	
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    Class<? extends AbstractValidationDoer> classForValidation();

}
