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
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LogroServiceTests {

	@Autowired
	protected LogroService logroService;
	
	public static int TEST_LOGRO_ID = 1;
	
	@BeforeEach
	void setup() {
		
	}
	
	@DisplayName("Prueba de localización de logro")
	@Test
	void shouldFindById() {
		assertEquals(TEST_LOGRO_ID, this.logroService.findById(TEST_LOGRO_ID).getId());
	}
	
	@DisplayName("Prueba de localización de logro errónea")
	@Test
	void shouldNotFindById() {
		assertEquals(TEST_LOGRO_ID, this.logroService.findById(TEST_LOGRO_ID).getId());
	}
	
	
	@DisplayName("Prueba de guardado de logro")
	@Test
	void shouldSave() {
		Logro logro = new Logro();
		logro.setNombre("golasooo");
		logro.setDescripcion("Madonna maradona");
		this.logroService.save(logro);
		assertThat(logro.getId().longValue()).isNotEqualTo(0);
		assertEquals("golasooo", this.logroService.findById(logro.getId()).getNombre());	
	}
	
	
	@DisplayName("Prueba de borrado de logro")
	@Test
	void shouldDelete() {
		this.logroService.delete(this.logroService.findById(TEST_LOGRO_ID));
		assertThrows(NullPointerException.class, () -> this.logroService.findById(TEST_LOGRO_ID).getNombre());
	}
	
}
