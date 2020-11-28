package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Examen;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExamenValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Examen examen = (Examen) obj;
		String titulos = examen.getTitulos();
		Double puntuacionMinima = examen.getPuntuacionMinima();
		Double puntuacionMaxima = examen.getPuntuacionMaxima();
		// titulos validation
		if (!StringUtils.hasLength(titulos) || titulos.length() < 1 || titulos.length() > 50) {
			errors.rejectValue("titulos", REQUIRED + " y entre 1 y 50 caracteres", REQUIRED
					+ " y entre 1 y 50 caracteres");
		}
		if (titulos.trim().length() == 0) {
			errors.rejectValue("titulos", REQUIRED + " y no puede estar vacío",
					REQUIRED + " y no puede estar vacío");	
		}
		// puntuacionMinima validation
		try {
			if (puntuacionMinima>=puntuacionMaxima) {
				errors.rejectValue("puntuacionMinima", REQUIRED +
						" y no puede ser mayor o igual que la puntuación Máxima", REQUIRED
						+ " y no puede ser mayor o igual que la puntuación Máxima");
			}			
		}catch(NullPointerException e) {
			errors.rejectValue("puntuacionMinima", REQUIRED + " y no puede estar vacía", REQUIRED
					+ " y no puede estar vacía");
		}
		
		
		// puntuacionMaxima validation
		try {
			if (puntuacionMinima>=puntuacionMaxima) {
				errors.rejectValue("puntuacionMaxima", REQUIRED +
						" y no puede ser mayor o igual que la puntuación Mínima", REQUIRED
						+ " y no puede ser mayor o igual que la puntuación Mínima");
			}
		}catch(NullPointerException e) {
			errors.rejectValue("puntuacionMaxima", REQUIRED + " y no puede estar vacía", REQUIRED
					+ " y no puede estar vacía");
		}
		
	}

	/**
	 * This Validator validates *just* Pet instances
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Examen.class.isAssignableFrom(clazz);
	}

}
