package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Intento;
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RespuestaServiceTest {

	
	@Autowired
	protected RespuestaService respuestaService;
	
	@Autowired
	protected IntentoService intentoService;
	

	private static int TEST_RESPUESTA_ID = 1;
	
	private static int TEST_INTENTO_ID = 1;
	
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
	
	@DisplayName("Prueba de localizar respuestas dada la id de un intento")
	@Test
	void shouldFindByIntentoId() {
		Intento intento1 = intentoService.findById(TEST_INTENTO_ID);
		Intento intento2 = new Intento();
		intento2.setExamen(intento1.getExamen());
		intento2.setFecha(LocalDate.now());
		intento2.setVersion(1);
		intentoService.save(intento2);
		Respuesta respuesta1 = respuestaService.findById(TEST_RESPUESTA_ID);
		respuesta1.setIntento(intento1);
		respuestaService.save(respuesta1);
		Respuesta respuesta2 = new Respuesta();
		respuesta2.setIntento(intento2);
		respuesta2.setNumeroPregunta(1);
		respuesta2.setTextoRespuesta("aaa");
		respuestaService.save(respuesta2);
		Respuesta respuesta3 = new Respuesta();
		respuesta3.setIntento(intento1);
		respuesta3.setNumeroPregunta(1);
		respuesta3.setTextoRespuesta("aaa");
		respuestaService.save(respuesta3);
		
		
		
		Collection<Respuesta> respuestas = new ArrayList<Respuesta>();
		respuestas.add(respuesta1);
		respuestas.add(respuesta3);
		Collection<Respuesta> collectionresp = this.respuestaService.findByIntentoId(TEST_INTENTO_ID);
		
		
		
		assertThat(respuestas).isEqualTo(collectionresp);
		

	}
	
}
