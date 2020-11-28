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
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class LogroServiceTests {

	@Autowired
	protected LogroService logroService;
	
	@BeforeEach
	void setup() {

		//
		
		
	}
	
	@DisplayName("Prueba de localización de logro")
	@Test
	void shouldFindById() {
		Logro logro = new Logro();
		logro.setNombre("Logro del test");
		logro.setDescripcion("Descripción adecuada para el test");
		this.logroService.save(logro);
		assertEquals(logro, this.logroService.findById(logro.getId()).get());
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
		Logro logro = new Logro();
		logro.setNombre("golasooo");
		logro.setDescripcion("Madonna maradona");
		this.logroService.save(logro);
		assertThat(logro.getId().longValue()).isNotEqualTo(0);
		assertEquals("golasooo", this.logroService.findById(logro.getId()).get().getNombre());	
	}
	
	
	@DisplayName("Prueba de borrado de logro")
	@Test
	void shouldDelete() {
		Logro logro = new Logro();
		logro.setNombre("golasooo");
		logro.setDescripcion("Madonna maradona");
		this.logroService.save(logro);
		Integer id = logro.getId();
		this.logroService.delete(logro);
		assertThrows(NoSuchElementException.class, () -> this.logroService.findById(id).get().getNombre());
	}
	
}
