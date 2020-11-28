package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.model.businessrulesexceptions.ImpossibleUsuarioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UsuarioServiceTests {
	
	private static final int TEST_USUARIO_ID = 1;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	private Usuario guille;
	
	
	
	@BeforeEach
	void setup() {
		//Usuario 1
		
		
	}
	
	
	@DisplayName("Prueba de localización de usuario")
	@Test
	void shouldFindById() throws ImpossibleUsuarioException {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
		this.usuarioService.save(usuario);
		
		assertEquals(usuario, this.usuarioService.findById(usuario.getId()).get());
		

	}
	@DisplayName("Prueba de guardado de usuario")
	@Test
	void shouldSave() throws ImpossibleUsuarioException {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
		this.usuarioService.save(usuario);
		assertThat(usuario.getId().longValue()).isNotEqualTo(0);
		assertEquals("Fran", this.usuarioService.findById(usuario.getId()).get().getNombre());
		

	}
	
	@DisplayName("Prueba de borrado de usuario")
	@Test
	void shouldDelete() throws ImpossibleUsuarioException {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
		this.usuarioService.save(usuario);
		Integer id = usuario.getId();
		this.usuarioService.delete(usuario);
		assertThrows(NoSuchElementException.class, () -> this.usuarioService.findById(id).get().getNombre());
		

	}

}
