package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.model.businessrulesexceptions.ImpossibleUsuarioException;
import org.springframework.transaction.annotation.Transactional;

public class UsuarioServiceTests {
	
	private static final int TEST_USUARIO_ID = 1;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	private Usuario guille;
	
	
	
	@BeforeEach
	void setup() {

		guille = new Usuario();
		guille.setId(TEST_USUARIO_ID);
		guille.setNombre("Guille");
		guille.setApellidos("López");
		guille.setLocalidad("El piso");
		guille.setColegio("La etsii");
		guille.setEmail("99999999999");
		//given(this.usuarioService.findById(TEST_USUARIO_ID).get()).willReturn(guille);
	}
	
	@Test
	void shouldFindUserWithCorrectId() {
		Usuario usuario = this.usuarioService.findById(TEST_USUARIO_ID).get();
		assertThat(usuario.getNombre()).startsWith("Guille");
		assertThat(usuario.getApellidos()).isEqualTo("López");

	}
	@DisplayName("Prueba de localización de usuario")
	@Test
	void shouldFindById() {
		Usuario usuario = this.usuarioService.findById(TEST_USUARIO_ID).get();
		System.out.println(usuario.getNombre());
		assertEquals("María José", usuario.getNombre());
		assertEquals("Lera", usuario.getApellidos());

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
		assertEquals("Fran", this.usuarioService.findById(usuario.getId()));
		

	}

}
