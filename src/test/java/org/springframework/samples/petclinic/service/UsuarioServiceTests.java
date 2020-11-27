package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Usuario;

public class UsuarioServiceTests {
	
	private static final int TEST_USUARIO_ID = 27;
	
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

}
