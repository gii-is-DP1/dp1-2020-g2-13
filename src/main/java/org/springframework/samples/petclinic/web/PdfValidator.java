package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PdfValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Pdf pdf = (Pdf) obj;
		String link = pdf.getLink();
		String nombre = pdf.getNombre();

		// link validation
		if (link.trim().length() == 0) {
			errors.rejectValue("link", "El enlace no puede estar vacío", "El enlace no puede estar vacío");
		}
		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length() < 1 || nombre.length() > 50) {
			errors.rejectValue("nombre", "El nombre debe tener entre 1 y 50 caracteres",
					"El nombre debe tener entre 1 y 50 caracteres");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("link", "El nombre no puede estar vacío", "El nombre no puede estar vacío");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Pdf.class.isAssignableFrom(clazz);
	}
}
