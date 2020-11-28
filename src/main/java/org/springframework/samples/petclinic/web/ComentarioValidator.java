
package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ComentarioValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Comentario comentario = (Comentario) obj;
		String contenido = comentario.getContenido();
		// contenido validation
		if (!StringUtils.hasLength(contenido) || contenido.length() < 1) {
			errors.rejectValue("contenido", REQUIRED + " y por encima de un caracter",
					REQUIRED + " y por encima de un caracter");
		}
		if (contenido.trim().length() == 0) {
			errors.rejectValue("contenido", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
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
