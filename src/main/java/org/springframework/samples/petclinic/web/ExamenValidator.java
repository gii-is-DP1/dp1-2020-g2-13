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
			errors.rejectValue("titulos", REQUIRED + " and between 1 character and 50 characters", REQUIRED
					+ " and between 1 character and 50 characters");
		}
		if (titulos.trim().length() == 0) {
			errors.rejectValue("titulos", REQUIRED + " and can't have only spaces",
					REQUIRED + " and can't have only spaces");	
		}
		// puntuacionMinima validation
		try {
			if (puntuacionMinima>=puntuacionMaxima) {
				errors.rejectValue("puntuacionMinima", REQUIRED +
						" and can't be larger or equal than puntuacionMaxima", REQUIRED
						+ " and can't be larger or equal than puntuacionMaxima");
			}			
		}catch(NullPointerException e) {
			errors.rejectValue("puntuacionMinima", REQUIRED + " and can't be empty", REQUIRED
					+ " and can't be empty");
		}
		
		
		// puntuacionMaxima validation
		try {
			if (puntuacionMinima>=puntuacionMaxima) {
				errors.rejectValue("puntuacionMaxima", REQUIRED +
						" and can't be lower or equal than puntuacionMinima", REQUIRED
						+ " and can't be lower or equal than puntuacionMinima");
			}
		}catch(NullPointerException e) {
			errors.rejectValue("puntuacionMaxima", REQUIRED + " and can't be empty", REQUIRED
					+ " and can't be empty");
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
