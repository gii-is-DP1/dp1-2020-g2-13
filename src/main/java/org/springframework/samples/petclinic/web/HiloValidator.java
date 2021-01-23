
package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class HiloValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Hilo hilo = (Hilo) obj;
		String nombre = hilo.getNombre();
		String categoria = hilo.getCategoria();
		String contenido = hilo.getContenido();
		Usuario usuario = hilo.getUsuario();
		
		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length() < 1 || nombre.length() > 30) {
			errors.rejectValue("nombre", "El nombre debe tener entre 1 y 30 caracteres",
					"El nombre debe tener entre 1 y 30 caracteres");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("nombre", "El nombre no puede estar vacío", "El nombre no puede estar vacío");
		}
		// categoria validation
		if (!StringUtils.hasLength(categoria) || categoria.length() < 1 || categoria.length() > 50) {
			errors.rejectValue("categoria", "El nombre debe tener entre 1 y 50 caracteres",
					"El nombre debe tener entre 1 y 50 caracteres");
		}
		if (categoria.trim().length() == 0) {
			errors.rejectValue("categoria", "La categoría no puede estar vacía", "La categoría no puede estar vacía");
		}
		// contenido validation
		if (!StringUtils.hasLength(contenido) || contenido.length() < 1 || contenido.length() > 1024) {
			errors.rejectValue("contenido", "El contenido debe tener entre 1 y 1024 caracteres",
					"El contenido debe tener entre 1 y 1024 caracteres");
		}
		if (contenido.trim().length() == 0) {
			errors.rejectValue("contenido", "El contenido no puede estar vacío", "El contenido no puede estar vacío");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Hilo.class.isAssignableFrom(clazz);
	}
}
