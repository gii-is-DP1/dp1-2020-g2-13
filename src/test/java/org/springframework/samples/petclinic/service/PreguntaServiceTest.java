package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PreguntaServiceTest {

	@Autowired
	protected PreguntaService preguntaService;

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
}
