package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PreguntaServiceTest {

	@Autowired
	protected PreguntaService preguntaService;
	@Autowired
	protected OpcionService opcionService;
	@Autowired
	protected TipoTestService tipoTestService;

	private static int TEST_PREGUNTA_ID = 1;

	@DisplayName("Prueba de localización de pregunta")
	@Test
	void shouldFindPdfWithCorrectId() {
		Pregunta pregunta = this.preguntaService.findById(TEST_PREGUNTA_ID);
		assertEquals(TEST_PREGUNTA_ID, pregunta.getId());
	}

	@DisplayName("Prueba de guardado de pregunta")
	@Test
	@Transactional
	public void shouldsavePregunta() {

		Pregunta pregunta = new Pregunta();
		pregunta.setContenido("¿Qué hora es?");
		this.preguntaService.save(pregunta);
		assertEquals(pregunta, this.preguntaService.findById(pregunta.getId()));
	}

	@DisplayName("Prueba de actualización de pregunta")
	@Test
	@Transactional
	public void shouldUpdateContenidoPregunta() throws Exception {
		Pregunta pregunta = this.preguntaService.findById(TEST_PREGUNTA_ID);
		String oldContent = pregunta.getContenido();

		String newContent = oldContent + "X";
		pregunta.setContenido(newContent);
		this.preguntaService.save(pregunta);

		pregunta = this.preguntaService.findById(TEST_PREGUNTA_ID);
		assertThat(pregunta.getContenido()).isEqualTo(newContent);
	}

	@DisplayName("Prueba de borrado de pregunta")
	@Test
	void shouldDelete() {
		this.preguntaService.delete(this.preguntaService.findById(TEST_PREGUNTA_ID));
		assertThrows(NullPointerException.class, () -> this.preguntaService.findById(TEST_PREGUNTA_ID).getContenido());
	}

	@DisplayName("Prueba no Tipo Test")
	@Test
	void shouldntBeTest() {
		Pregunta pregunta = new Pregunta();
		pregunta.setContenido("Enunciado");
		this.preguntaService.save(pregunta);
		assertThat(pregunta.getTipoTest() == null);
	}

	@DisplayName("Prueba sí Tipo Test")
	@Test
	void shouldBeTest() {
		Pregunta pregunta = new Pregunta();
		pregunta.setContenido("Enunciado");
		this.preguntaService.save(pregunta);
		Opcion opcion = new Opcion();
		opcion.setTexto("opcion 1");
		this.opcionService.save(opcion);
		List<Opcion> opciones = new ArrayList<>();
		opciones.add(opcion);
		TipoTest tipoTest = new TipoTest();
		tipoTest.setOpciones(opciones);
		this.tipoTestService.save(tipoTest);
		pregunta.setTipoTest(tipoTest);
		this.preguntaService.save(pregunta);
		assertThat(pregunta.getTipoTest() != null);
	}

	@DisplayName("Prueba añadir y Tipo Test")
	@Test
	void addAndShouldBeTest() {
		Pregunta pregunta = new Pregunta();
		pregunta.setContenido("Enunciado");
		this.preguntaService.save(pregunta);
		if (pregunta.getTipoTest() == null) {
			this.preguntaService.save(pregunta);
			Opcion opcion = new Opcion();
			opcion.setTexto("opcion 1");
			this.opcionService.save(opcion);
			List<Opcion> opciones = new ArrayList<>();
			opciones.add(opcion);
			TipoTest tipoTest = new TipoTest();
			tipoTest.setOpciones(opciones);
			this.tipoTestService.save(tipoTest);
			pregunta.setTipoTest(tipoTest);
			this.preguntaService.save(pregunta);
			assertThat(pregunta.getTipoTest() != null);
		} else {
			throw new UnknownError();
		}
	}

	@DisplayName("Prueba borrar y no Tipo Test")
	@Test
	void deleteAndoShouldntBeTest() {
		Pregunta pregunta = new Pregunta();
		pregunta.setContenido("Enunciado");
		this.preguntaService.save(pregunta);
		Opcion opcion = new Opcion();
		opcion.setTexto("opcion 1");
		this.opcionService.save(opcion);
		List<Opcion> opciones = new ArrayList<>();
		opciones.add(opcion);
		TipoTest tipoTest = new TipoTest();
		tipoTest.setOpciones(opciones);
		this.tipoTestService.save(tipoTest);
		pregunta.setTipoTest(tipoTest);
		this.preguntaService.save(pregunta);
		if (pregunta.getTipoTest() != null) {
			this.tipoTestService.delete(tipoTest);
			this.preguntaService.save(pregunta);
			assertThat(pregunta.getTipoTest() == null);
		} else {
			throw new UnknownError();
		}
	}
}
