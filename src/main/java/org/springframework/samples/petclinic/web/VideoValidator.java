package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Video;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VideoValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {

		Video video = (Video) obj;
		String link = video.getLink();
		String nombre = video.getNombre();
		String descripcion = video.getDescripcion();
		String duracion = video.getDuracion();

		// link validation
		if (link.trim().length() == 0) {
			errors.rejectValue("link", "El enlace no puede estar vacío", "El enlace no puede estar vacío");
		}
		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length() < 1 || nombre.length() > 50) {
			errors.rejectValue("nombre", "El nombre debe tener entre 1 y 50 caracteres",
					"El nombre debe tener entre 1 y 50 caracteres");
		}
		if (link.trim().length() == 0) {
			errors.rejectValue("link", "El nombre no puede estar vacío", "El nombre no puede estar vacío");
		}

		// descripcion validation
		if (!StringUtils.hasLength(descripcion) || descripcion.length() < 1 || descripcion.length() > 250) {
			errors.rejectValue("descripcion", "La descripcion debe tener entre 1 y 250 caracteres",
					"descripcion debe tener entre 1 y 250 caracteres");
		}
		if (descripcion.trim().length() == 0) {
			errors.rejectValue("descripcion", "La descripcion no puede estar vacía",
					"descripcion no puede estar vacía");
		}
		// duracion validation
		if (!StringUtils.hasLength(duracion) || duracion.length() < 1 || duracion.length() > 10) {
			errors.rejectValue("nombre", "Duración debe tener entre 1 y 10 caracteres",
					"Duración debe tener entre 1 y 10 caracteres");
		}
		if (link.trim().length() == 0) {
			errors.rejectValue("link", "Duració no puede estar vacío", "Duració no puede estar vacío");
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Video.class.isAssignableFrom(clazz);
	}
}
