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
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RespuestaServiceTest {

	
	@Autowired
	protected RespuestaService respuestaService;

	private static int TEST_RESPUESTA_ID = 1;
	
	@DisplayName("Prueba de localización de respuesta")
	@Test
	void shouldFindPdfWithCorrectId() {
		Respuesta respuesta = this.respuestaService.findById(TEST_RESPUESTA_ID);
		assertEquals(TEST_RESPUESTA_ID, respuesta.getId());

	}
	
	@DisplayName("Prueba de guardado de respuesta")
	@Test
	@Transactional
	public void shouldsaveRespuesta() {

		Respuesta respuesta = new Respuesta();
		respuesta.setTextoRespuesta("Las 2:00 pm");
		this.respuestaService.save(respuesta);
		assertEquals(respuesta, this.respuestaService.findById(respuesta.getId()));
	}
	@DisplayName("Prueba de actualización de respuesta")
	@Test
	@Transactional
	public void shouldUpdateTesxtoRespuesta() throws Exception {
		Respuesta respuesta = this.respuestaService.findById(TEST_RESPUESTA_ID);
		String oldContent = respuesta.getTextoRespuesta();

		String newContent = oldContent + "X";
		respuesta.setTextoRespuesta(newContent);
		this.respuestaService.save(respuesta);

		respuesta = this.respuestaService.findById(TEST_RESPUESTA_ID);
		assertThat(respuesta.getTextoRespuesta()).isEqualTo(newContent);
	}

	@DisplayName("Prueba de borrado de respuesta")
	@Test
	void shouldDelete() {
		
		
		this.respuestaService.delete(this.respuestaService.findById(TEST_RESPUESTA_ID));
		assertThrows(NullPointerException.class, () -> this.respuestaService.findById(TEST_RESPUESTA_ID).getId());
		

	}
	
}
