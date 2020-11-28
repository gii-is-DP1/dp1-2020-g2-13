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
@Constraint(validatedBy = {PossibleComentarioValidator.class})

public @interface ValidatePossibleComentario {
	String message() default "Comentario no válido";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
