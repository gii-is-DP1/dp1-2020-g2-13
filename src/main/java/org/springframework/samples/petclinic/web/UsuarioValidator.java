package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UsuarioValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Usuario usuario = (Usuario) obj;
		String nombre = usuario.getNombre();
		String apellidos = usuario.getApellidos();
		String localidad = usuario.getLocalidad();
		String colegio = usuario.getColegio();
		String email = usuario.getEmail();
		String contrasena = usuario.getContrasena();
		// nombre validation
		if (!StringUtils.hasLength(nombre) || nombre.length() < 1 || nombre.length() > 30) {
			errors.rejectValue("nombre", REQUIRED + " y entre 1 y 30 caracteres",
					REQUIRED + " y entre 1 y 30 caracteres");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("nombre", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
		}
		// apellidos validation
		if (!StringUtils.hasLength(apellidos) || apellidos.length() < 1 || apellidos.length() > 100) {
			errors.rejectValue("apellidos", REQUIRED + " y entre 1 y 100 caracteres",
					REQUIRED + " y entre 1 y 100 caracteres");
		}
		if (apellidos.trim().length() == 0) {
			errors.rejectValue("apellidos", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
		}
		// localidad validation
		if (!StringUtils.hasLength(localidad) || localidad.length() < 1 || localidad.length() > 100) {
			errors.rejectValue("localidad", REQUIRED + " y entre 1 y 100 caracteres",
					REQUIRED + " y entre 1 y 100 caracteres");
		}
		if (localidad.trim().length() == 0) {
			errors.rejectValue("localidad", REQUIRED + " y no puede estar vacía",
					REQUIRED + " y no puede estar vacía");
		}
		// colegio validation
		if (!StringUtils.hasLength(colegio) || colegio.length() < 1 || colegio.length() > 100) {
			errors.rejectValue("colegio", REQUIRED + " y entre 1 y 100 caracteres",
					REQUIRED + " y entre 1 y 100 caracteres");
		}
		if (colegio.trim().length() == 0) {
			errors.rejectValue("colegio", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
		}
		// email validation
		if (!StringUtils.hasLength(email) || email.length() < 1 || email.length() > 100) {
			errors.rejectValue("email", REQUIRED + " y entre 1 y 100 caracteres",
					REQUIRED + " y entre 1 y 100 caracteres");
		}
		if (email.trim().length() == 0) {
			errors.rejectValue("email", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");
		}
		// contrasena validation
		if (!StringUtils.hasLength(contrasena) || contrasena.length() < 1 || contrasena.length() > 100) {
			errors.rejectValue("contrasena", REQUIRED + " y entre 1 y 100 caracteres",
					REQUIRED + " y entre 1 y 100 caracteres");
		}
		if (contrasena.trim().length() == 0) {
			errors.rejectValue("contrasena", REQUIRED + " y no puede estar vacía",
					REQUIRED + " y no puede estar vacía");
		}
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

}
