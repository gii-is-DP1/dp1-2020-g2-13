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
	
	public static int TEST_LOGRO_ID;
	
	@BeforeEach
	void setup() {
		Logro logro = new Logro();
		logro.setNombre("Fran");
		logro.setDescripcion("Bel");
		this.logroService.save(logro);
		TEST_LOGRO_ID = logro.getId();
		
	}
	
	@DisplayName("Prueba de localización de logro")
	@Test
	void shouldFindById() {
		assertEquals(TEST_LOGRO_ID, this.logroService.findById(TEST_LOGRO_ID).get().getId());
	}
	
	@DisplayName("Prueba de localización de logro errónea")
	@Test
	void shouldNotFindById() {
		Logro logro = new Logro();
		logro.setNombre("Logro del test");
		logro.setDescripcion("Descripción adecuada para el test");
		this.logroService.save(logro);
		assertThrows(NoSuchElementException.class, () -> this.logroService.findById(56789).get().getNombre());
	}
	
	
	@DisplayName("Prueba de guardado de logro")
	@Test
	void shouldSave() {
		Logro logro2 = new Logro();
		logro2.setNombre("golasooo");
		logro2.setDescripcion("Madonna maradona");
		this.logroService.save(logro2);
		assertThat(logro2.getId().longValue()).isNotEqualTo(0);
		assertEquals("golasooo", this.logroService.findById(logro2.getId()).get().getNombre());	
	}
	
	
	@DisplayName("Prueba de borrado de logro")
	@Test
	void shouldDelete() {
		this.logroService.delete(this.logroService.findById(TEST_LOGRO_ID).get());
		assertThrows(NoSuchElementException.class, () -> this.logroService.findById(TEST_LOGRO_ID).get().getNombre());
	}
	
	@DisplayName("Prueba de borrado de logro errónea")
	@Test
	void shouldNotDelete() {
		this.logroService.delete(this.logroService.findById(TEST_LOGRO_ID).get());
		//Aquí da el fallo porque trataría de borrar 2 veces
		assertThrows(NoSuchElementException.class, () -> this.logroService.delete(this.logroService.findById(TEST_LOGRO_ID).get()));
	}
	
}
