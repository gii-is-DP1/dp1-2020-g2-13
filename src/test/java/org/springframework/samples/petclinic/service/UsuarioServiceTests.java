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
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UsuarioServiceTests {
	
	
	
	@Autowired
	protected UsuarioService usuarioService;
	
	private static int TEST_USUARIO_ID;
	
	
	
	@BeforeEach
	void setup() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
		this.usuarioService.save(usuario);
		TEST_USUARIO_ID = usuario.getId();
		
	}
	
	
	@DisplayName("Prueba de localización de usuario")
	@Test
	void shouldFindById() {
		
		
		assertEquals(TEST_USUARIO_ID, this.usuarioService.findById(TEST_USUARIO_ID).get().getId());
		

	}
	@DisplayName("Prueba de guardado de usuario")
	@Test
	void shouldSave(){
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran2");
		usuario.setApellidos("Bel2");
		usuario.setLocalidad("El piso2");
		usuario.setColegio("La etsii2");
		usuario.setEmail("999999999992");
		this.usuarioService.save(usuario);
		assertThat(usuario.getId().longValue()).isNotEqualTo(0);
		assertEquals("Fran2", this.usuarioService.findById(usuario.getId()).get().getNombre());
		

	}
	
	@DisplayName("Prueba de borrado de usuario")
	@Test
	void shouldDelete() {
		
		
		this.usuarioService.delete(this.usuarioService.findById(TEST_USUARIO_ID).get());
		assertThrows(NoSuchElementException.class, () -> this.usuarioService.findById(TEST_USUARIO_ID).get().getNombre());
		

	}

}
