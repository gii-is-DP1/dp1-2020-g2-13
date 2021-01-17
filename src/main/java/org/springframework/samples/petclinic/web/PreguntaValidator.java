package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PreguntaValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {

		Pregunta pregunta = (Pregunta) obj;
		String contenido = pregunta.getContenido();
//		TipoTest tipoTest = pregunta.getTipoTest();

		// contenido validation
		if (contenido.trim().length() == 0) {
			errors.rejectValue("contenido", "El contenido no puede estar vacío", "El contenido no puede estar vacío");
		}
		if (!StringUtils.hasLength(contenido) || contenido.length() < 1 || contenido.length() > 250) {
			errors.rejectValue("contenido", "El contenido debe tener entre 1 y 250 caracteres",
					"El contenido debe tener entre 1 y 250 caracteres");
		}
//		// tipoTest validation
//		if (tipoTest == null) {
//			errors.rejectValue("tipoTest", "Tipo Test no puede ser nulo", "Tipo Test no puede ser nulo");
//		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Pregunta.class.isAssignableFrom(clazz);
	}
}
