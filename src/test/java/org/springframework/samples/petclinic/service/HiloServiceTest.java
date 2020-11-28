package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class HiloServiceTest {
	
	@Autowired
	protected HiloService hiloService;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	private static int TEST_USUARIO_ID;
	private static int TEST_HILO_ID;

	
	@BeforeEach
	void setup(){
		
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
		this.usuarioService.save(usuario);
		TEST_USUARIO_ID = usuario.getId();
		
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleducado");
		hilo.setCategoria("maltrato psicológico");
		hilo.setContenido("abro hilo:");
		hilo.setUsuario(usuario);
		this.hiloService.save(hilo);
		TEST_HILO_ID = hilo.getId();
		
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
		
		assertEquals(TEST_HILO_ID, this.hiloService.findById(TEST_HILO_ID).get().getId());
		

	}
	
	
	@DisplayName("Prueba de guardado de hilo")
	@Test
	void shouldSave(){
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleducado2");
		hilo.setCategoria("maltrato psicológico2");
		hilo.setContenido("abro hilo:2");
		hilo.setUsuario(this.usuarioService.findById(TEST_USUARIO_ID).get());
		this.hiloService.save(hilo);
		assertThat(hilo.getId().longValue()).isNotEqualTo(0);
		assertEquals(hilo, this.hiloService.findById(hilo.getId()).get());
		

	}
}
