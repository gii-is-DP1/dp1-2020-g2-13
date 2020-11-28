package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Comentario;
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
		if (!StringUtils.hasLength(nombre) || nombre.length()<1 || nombre.length()>30) {
			errors.rejectValue("nombre", REQUIRED+" between 1 and 30 characters", REQUIRED+" between 1 and 30 characters");
		}
		if (nombre.trim().length() == 0) {
			errors.rejectValue("nombre", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// apellidos validation
		if (!StringUtils.hasLength(apellidos) || apellidos.length()<1 || apellidos.length()>100) {
			errors.rejectValue("apellidos", REQUIRED+" between 1 and 100 characters", REQUIRED+" between 1 and 100 characters");
		}
		if (apellidos.trim().length() == 0) {
			errors.rejectValue("apellidos", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// localidad validation
		if (!StringUtils.hasLength(localidad) || localidad.length()<1 || localidad.length()>100) {
			errors.rejectValue("localidad", REQUIRED+" between 1 and 100 characters", REQUIRED+" between 1 and 100 characters");
		}
		if (localidad.trim().length() == 0) {
			errors.rejectValue("localidad", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// colegio validation
		if (!StringUtils.hasLength(colegio) || colegio.length()<1 || colegio.length()>100) {
			errors.rejectValue("colegio", REQUIRED+" between 1 and 100 characters", REQUIRED+" between 1 and 100 characters");
		}
		if (colegio.trim().length() == 0) {
			errors.rejectValue("colegio", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// email validation
		if (!StringUtils.hasLength(email) || email.length()<1 || email.length()>100) {
			errors.rejectValue("email", REQUIRED+" between 1 and 100 characters", REQUIRED+" between 1 and 100 characters");
		}
		if (email.trim().length() == 0) {
			errors.rejectValue("email", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
		}
		// contrasena validation
		if (!StringUtils.hasLength(contrasena) || contrasena.length()<1 || contrasena.length()>100) {
			errors.rejectValue("contrasena", REQUIRED+" between 1 and 100 characters", REQUIRED+" between 1 and 100 characters");
		}
		if (contrasena.trim().length() == 0) {
			errors.rejectValue("contrasena", REQUIRED+" and can't have only spaces", REQUIRED+" and can't have only spaces");
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
