package org.springframework.samples.petclinic.service.businessrules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Pdf;

public class PossiblePdfValidator implements ConstraintValidator<ValidatePossiblePdf, Pdf> {

	@Override
	public boolean isValid(Pdf value, ConstraintValidatorContext context) {
		return value.getArchivo().trim().length() > 0 && value.getArchivo().length() <= 1024;
	}

}
