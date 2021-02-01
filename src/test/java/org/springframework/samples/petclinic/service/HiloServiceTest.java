package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class HiloServiceTest {
	
	@Autowired
	protected HiloService hiloService;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	
	private static int TEST_HILO_ID = 1;


	
	@BeforeEach
	void setup(){

	}

	@DisplayName("Prueba de localización de hilo")
	@Test
	void shouldFindById(){

//		Hilo hilo = new Hilo();
//		hilo.setNombre("Profesorado maleducado");
//		hilo.setCategoria("maltrato psicológico");
//		hilo.setContenido("abro hilo:");
//		hilo.setUsuario(this.usuarioService.findById(TEST_USUARIO_ID).get());
//		this.hiloService.save(hilo);
		
		assertEquals(TEST_HILO_ID, this.hiloService.findById(TEST_HILO_ID).getId());
		

	}
	
	
	@DisplayName("Prueba de guardado de hilo")
	@Test
	void shouldSave(){
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleducado2");
		hilo.setCategoria("maltrato psicológico2");
		hilo.setContenido("abro hilo:2");
		hilo.setUsuario(this.usuarioService.findById(TEST_HILO_ID));
		this.hiloService.save(hilo);
		assertThat(hilo.getId().longValue()).isNotEqualTo(0);
		assertEquals(hilo, this.hiloService.findById(hilo.getId()));

		

	}
	
	@DisplayName("Prueba de borrado de hilo")
	@Test
	void shouldDelete() {
		
		
		this.hiloService.delete(this.hiloService.findById(TEST_HILO_ID));
		assertThrows(NullPointerException.class, () -> this.hiloService.findById(TEST_HILO_ID).getNombre());

		

	}
	
	
	@DisplayName("Prueba de subscripción a un hilo")
	@Test
	void shouldSubscribe(){
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
		Hilo hilo = hiloService.findById(TEST_HILO_ID);
		int size = hilo.getSuscriptores().size();
		hiloService.suscribir(hilo, usuario);
		assertThat(hilo.getSuscriptores().size()).isEqualTo(size + 1);
	}
}