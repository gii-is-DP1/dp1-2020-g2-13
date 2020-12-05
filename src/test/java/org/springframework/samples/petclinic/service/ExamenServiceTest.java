package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ExamenServiceTest {

	@Autowired
	protected ExamenService examenService;

	@Autowired
	protected UsuarioService usuarioService;

	@DisplayName("Prueba de localización de examen")
	@Test
	
	void shouldFindExamen() {
		Examen examen = this.examenService.findById(1);
		assertThat(examen.getTitulos().equals("prueba"));
		assertThat(examen.getPuntuacionMaxima().equals(10.0));
		assertThat(examen.getPuntuacionMinima().equals(0.0));

	}

	@DisplayName("Prueba de guardado de examen")
	@Test
	void shouldSaveExamen() {
		Examen examen = new Examen();
		examen.setTitulos("Examen de Convocatoria");
		examen.setPuntuacionMaxima(10.0);
		examen.setPuntuacionMinima(0.0);
		examen.setUsuario(this.usuarioService.findById(1));
		this.examenService.save(examen);
		assertThat(examen.getId().longValue()).isNotEqualTo(0);
		assertEquals(examen, this.examenService.findById(examen.getId()));
	}

	@DisplayName("Prueba de borrado de examen")
	@Test
	void shouldDeleteExamen() {
		this.examenService.delete(this.examenService.findById(1));
		assertThrows(NoSuchElementException.class, () -> this.examenService.findById(1).getTitulos());
	}

	@DisplayName("Prueba de edición de examen")
	@Test
	@Transactional
	void shouldUpdateExamen() {
		Examen examen = this.examenService.findById(1);
		String tituloAntiguo = examen.getTitulos();
		String tituloNuevo = tituloAntiguo + "Final";

		examen.setTitulos(tituloNuevo);
		this.examenService.save(examen);

		examen = this.examenService.findById(1);
		assertThat(examen.getTitulos()).isEqualTo(tituloNuevo);
	}
}