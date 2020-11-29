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
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ExamenServiceTest {

	@Autowired
	protected ExamenService examenService;

	@Autowired
	protected UsuarioService usuarioService;

	private static int TEST_USUARIO_ID;
	private static int TEST_EXAMEN_ID;

	@BeforeEach
	void setup() {

		Usuario usuario = new Usuario();
		usuario.setNombre("Masiu");
		usuario.setApellidos("Qye Lera");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999099999");
		this.usuarioService.save(usuario);
		TEST_USUARIO_ID = usuario.getId();

		Examen examen = new Examen();
		examen.setTitulos("Examen de DP1");
		examen.setPuntuacionMaxima(10.0);
		examen.setPuntuacionMinima(0.0);
		examen.setUsuario(usuario);
		this.examenService.save(examen);
		TEST_EXAMEN_ID = examen.getId();

	}

	@DisplayName("Prueba de localización de examen")
	@Test
	void shouldFindById() {
		Examen examen = new Examen();
		examen.setTitulos("Examen de DP1");
		examen.setPuntuacionMaxima(10.0);
		examen.setPuntuacionMinima(0.0);
		examen.setUsuario(this.usuarioService.findById(TEST_USUARIO_ID));
		this.examenService.save(examen);
		assertEquals(TEST_EXAMEN_ID, this.examenService.findById(TEST_EXAMEN_ID).getId());
	}

	@DisplayName("Prueba de guardado de examen")
	@Test
	void shouldSave() {
		Examen examen = new Examen();
		examen.setTitulos("Examen de Convocatoria");
		examen.setPuntuacionMaxima(10.0);
		examen.setPuntuacionMinima(0.0);
		examen.setUsuario(this.usuarioService.findById(TEST_USUARIO_ID));
		this.examenService.save(examen);
		assertThat(examen.getId().longValue()).isNotEqualTo(0);
		assertEquals(examen, this.examenService.findById(examen.getId()));
	}

	@DisplayName("Prueba de borrado de examen")
	@Test
	void shouldDelete() {
		this.examenService.delete(this.examenService.findById(TEST_EXAMEN_ID));
		assertThrows(NoSuchElementException.class, () -> this.examenService.findById(TEST_EXAMEN_ID));
	}

	@DisplayName("Prueba de edición de examen")
	@Test
	@Transactional
	void shouldUpdateExamen() {
		Examen examen = this.examenService.findById(TEST_EXAMEN_ID);
		String tituloAntiguo = examen.getTitulos();
		String tituloNuevo = tituloAntiguo + "X";

		examen.setTitulos(tituloNuevo);
		this.examenService.save(examen);

		examen = this.examenService.findById(TEST_EXAMEN_ID);
		assertThat(examen.getTitulos()).isEqualTo(tituloNuevo);
	}
}
