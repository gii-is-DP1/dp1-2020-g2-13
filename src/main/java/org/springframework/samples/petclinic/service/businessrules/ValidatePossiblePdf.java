package org.springframework.samples.petclinic.service.businessrules;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PossiblePdfValidator.class})

public @interface ValidatePossiblePdf {
	String message() default "Documento no válido";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
