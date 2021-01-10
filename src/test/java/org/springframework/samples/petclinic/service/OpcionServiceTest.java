package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class OpcionServiceTest {

	@Autowired
	protected OpcionService opcionService;

	private static int TEST_OPC_ID = 1;
	
	
	
	
	@DisplayName("Prueba de localización de opciones")
	@Test
	void shouldFindPdfWithCorrectId() {
		Opcion opcion = this.opcionService.findById(TEST_OPC_ID);
		assertEquals(TEST_OPC_ID, opcion.getId());

	}
	
	@DisplayName("Prueba de guardado de opciones")
	@Test
	@Transactional
	public void shouldsaveOpcion() {

		Opcion opcion = new Opcion();
		opcion.setTexto("toma");
		this.opcionService.save(opcion);
		assertEquals(opcion, this.opcionService.findById(opcion.getId()));
	}
	@DisplayName("Prueba de actualización de opciones")
	@Test
	@Transactional
	public void shouldUpdateTextoOpcion() throws Exception {
		Opcion opcion = this.opcionService.findById(TEST_OPC_ID);
		String oldName = opcion.getTexto();

		String newName = oldName + "X";
		opcion.setTexto(newName);
		this.opcionService.save(opcion);

		opcion = this.opcionService.findById(TEST_OPC_ID);
		assertThat(opcion.getTexto()).isEqualTo(newName);
	}

	@DisplayName("Prueba de borrado de opciones")
	@Test
	void shouldDelete() {
		
		
		this.opcionService.delete(this.opcionService.findById(TEST_OPC_ID));
		assertThrows(NullPointerException.class, () -> this.opcionService.findById(TEST_OPC_ID).getId());
		

	}
}
