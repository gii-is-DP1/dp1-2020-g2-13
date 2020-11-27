package org.springframework.samples.petclinic.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.petclinic.model.Comentario;

public class PossibleComentarioValidator implements ConstraintValidator<ValidatePossibleComentario, Comentario> {

	@Override
	public boolean isValid(Comentario value, ConstraintValidatorContext context) {
		return value.getContenido().trim().length() > 0 && value.getContenido().length() <= 1024;
	}

}
