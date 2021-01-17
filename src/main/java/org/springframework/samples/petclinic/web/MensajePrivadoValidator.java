package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MensajePrivadoValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		MensajePrivado mensajePrivado = (MensajePrivado) obj;
		String contenido = mensajePrivado.getContenido();
		Usuario emisor = mensajePrivado.getEmisor();
		Usuario receptor = mensajePrivado.getReceptor();

		// contenido validation
		if (!StringUtils.hasLength(contenido) || contenido.length() < 1 || contenido.length() > 250) {
			errors.rejectValue("contenido", "Contenido debe tener entre 1 y 250 caracteres",
					"Contenido debe tener entre 1 y 250 caracteres");
		}
		if (contenido.trim().length() == 0) {
			errors.rejectValue("contenido", "Contenido no puede estar vacío", "Contenido no puede estar vacío");
		}
		// emisor validation
		if (emisor == null) {
			errors.rejectValue("emisor", "Emisor no puede estar vacío", "Emisor no puede estar vacío");
		}
		// receptor validation
		if (receptor == null) {
			errors.rejectValue("receptor", "Receptor no puede estar vacío", "Receptor no puede estar vacío");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return MensajePrivado.class.isAssignableFrom(clazz);
	}

}
