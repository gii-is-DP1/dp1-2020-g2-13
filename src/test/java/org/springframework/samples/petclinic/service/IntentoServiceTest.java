package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Intento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IntentoServiceTest {

	@Autowired
	protected IntentoService intentoService;

	private static int TEST_INTENTO_ID = 1;
	
	@DisplayName("Prueba de localización de intento")
	@Test
	void shouldFindPdfWithCorrectId() {
		Intento intento = this.intentoService.findById(TEST_INTENTO_ID);
		assertEquals(TEST_INTENTO_ID, intento.getId());

	}
	
	@DisplayName("Prueba de guardado de intento")
	@Test
	@Transactional
	public void shouldsaveIntento() {

		Intento intento = new Intento();
		intento.setPuntuacion(3.4);
		intento.setFecha(LocalDate.of(2009, 3, 21));
		this.intentoService.save(intento);
		assertEquals(intento, this.intentoService.findById(intento.getId()));
	}
	
	@DisplayName("Prueba de actualización de intento")
	@Test
	@Transactional
	public void shouldUpdatePuntuacionIntento() throws Exception {
		Intento intento = this.intentoService.findById(TEST_INTENTO_ID);
		Double oldScore = intento.getPuntuacion();

		Double newScore = oldScore + 2;
		intento.setPuntuacion(newScore);
		this.intentoService.save(intento);

		intento = this.intentoService.findById(TEST_INTENTO_ID);
		assertThat(intento.getPuntuacion()).isEqualTo(newScore);
	}
	
	@DisplayName("Prueba de borrado de intento")
	@Test
	void shouldDelete() {
		
		
		this.intentoService.delete(this.intentoService.findById(TEST_INTENTO_ID));
		assertThrows(NullPointerException.class, () -> this.intentoService.findById(TEST_INTENTO_ID).getId());
		

	}
}
