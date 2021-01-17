package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TipoTestServiceTest {

	@Autowired
	protected TipoTestService tipoTestService;

	private static int TEST_TIPO_TEST_ID = 1;
	
	@DisplayName("Prueba de localización de tipoTest")
	@Test
	void shouldFindPdfWithCorrectId() {
		TipoTest tipoTest = this.tipoTestService.findById(TEST_TIPO_TEST_ID);
		assertEquals(TEST_TIPO_TEST_ID, tipoTest.getId());

	}
	
	@DisplayName("Prueba de guardado de tipoTest")
	@Test
	@Transactional
	public void shouldsaveTipoTest() {

		TipoTest tipoTest = new TipoTest();
		
		this.tipoTestService.save(tipoTest);
		assertEquals(tipoTest, this.tipoTestService.findById(tipoTest.getId()));
	}


	@DisplayName("Prueba de borrado de tipoTest")
	@Test
	void shouldDelete() {
		
		
		this.tipoTestService.delete(this.tipoTestService.findById(TEST_TIPO_TEST_ID));
		assertThrows(NullPointerException.class, () -> this.tipoTestService.findById(TEST_TIPO_TEST_ID).getId());
		

	}
}
