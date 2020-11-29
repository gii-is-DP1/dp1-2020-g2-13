package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Examen;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExamenValidator implements Validator {

	@Override
	public void validate(Object obj, Errors errors) {
		Examen examen = (Examen) obj;
		String titulos = examen.getTitulos();
		Double puntuacionMinima = examen.getPuntuacionMinima();
		Double puntuacionMaxima = examen.getPuntuacionMaxima();
		// titulos validation
		if (!StringUtils.hasLength(titulos) || titulos.length() < 1 || titulos.length() > 50) {
			errors.rejectValue("titulos", "El título debe tener entre 1 y 50 caracteres",
					"El título debe tener entre 1 y 50 caracteres");
		}
		if (titulos.trim().length() == 0) {
			errors.rejectValue("titulos", "El título no puede estar vacío", "El título no puede estar vacío");
		}
		// puntuacionMinima validation
		try {
			if (puntuacionMinima >= puntuacionMaxima) {
				errors.rejectValue("puntuacionMinima",
						"La puntuación mínima no puede ser mayor o igual que la puntuación máxima",
						"La puntuación mínima no puede ser mayor o igual que la puntuación máxima");
			}
		} catch (NullPointerException e) {
			errors.rejectValue("puntuacionMinima", "La puntuación mínima no puede estar vacía",
					"La puntuación mínima no puede estar vacía");
		}

		// puntuacionMaxima validation
		try {
			if (puntuacionMinima >= puntuacionMaxima) {
				errors.rejectValue("puntuacionMaxima",
						"La puntuación máxima no puede ser mayor o igual que la puntuación mínima",
						"La puntuación máxima no puede ser mayor o igual que la puntuación mínima");
			}
		} catch (NullPointerException e) {
			errors.rejectValue("puntuacionMaxima", "La puntuación máxima no puede estar vacía",
					"La puntuación máxima no puede estar vacía");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Examen.class.isAssignableFrom(clazz);
	}
}
