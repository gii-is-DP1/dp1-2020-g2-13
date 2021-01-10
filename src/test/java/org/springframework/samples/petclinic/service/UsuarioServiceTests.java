package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UsuarioServiceTests {
	
	
	
	@Autowired
	protected UsuarioService usuarioService;
	
	private final static int TEST_USUARIO_ID = 1;
	
	
	
//	@BeforeEach
//	void setup() {
//		Usuario usuario = new Usuario();
//		usuario.setNombre("Fran");
//		usuario.setId(TEST_USUARIO_ID);
//		usuario.setApellidos("Bel");
//		usuario.setLocalidad("El piso");
//		usuario.setColegio("La etsii");
//		usuario.setEmail("99999999999");
//		usuario.setContrasena("qwerty123");
//		given(this.usuarioService.findById(TEST_USUARIO_ID)).willReturn(usuario);
//		
//	}
	
	
	@DisplayName("Prueba de localización de usuario")
	@Test
	void shouldFindById() {
		
		
		assertEquals(TEST_USUARIO_ID, this.usuarioService.findById(TEST_USUARIO_ID).getId());
		

	}
	@DisplayName("Prueba de guardado de usuario")
	@Test
	void shouldSave(){
		User user = new User();
		user.setUsername("user");
		user.setPassword("Qwerty123");
		Usuario usuario = new Usuario();
		usuario.setUser(user);
		usuario.setNombre("Fran2");
		usuario.setApellidos("Bel2");
		usuario.setLocalidad("El piso2");
		usuario.setColegio("La etsii2");
		usuario.setEmail("999999999992");
		usuarioService.save(usuario);
		assertThat(usuario.getId().longValue()).isNotEqualTo(0);
		assertEquals("Fran2", this.usuarioService.findById(usuario.getId()).getNombre());
		

	}
	
	@DisplayName("Prueba de borrado de usuario")
	@Test
	void shouldDelete() {
		
		
		this.usuarioService.delete(this.usuarioService.findById(TEST_USUARIO_ID));
		assertThrows(NullPointerException.class, () -> this.usuarioService.findById(TEST_USUARIO_ID).getId());
		

	}

}
