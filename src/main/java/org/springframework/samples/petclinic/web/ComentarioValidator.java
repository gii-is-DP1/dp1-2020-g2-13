package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ComentarioValidator implements Validator {
	
	@Override
	public void validate(Object obj, Errors errors) {
		Comentario comentario = (Comentario) obj;
		String contenido = comentario.getContenido();
		// contenido validation
		if (!StringUtils.hasLength(contenido) || contenido.length() < 1) {
			errors.rejectValue("contenido", "Contenido debe tener más de un caracter",
					"contenido debe tener más de un caracter");
		}
		if (contenido.trim().length() == 0) {
			errors.rejectValue("contenido", "Contenido no puede estar vacío",
					 "contenido no puede estar vacío");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Comentario.class.isAssignableFrom(clazz);
	}

}
