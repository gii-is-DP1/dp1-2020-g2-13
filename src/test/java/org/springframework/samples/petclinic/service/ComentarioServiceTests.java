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
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class ComentarioServiceTests {
	
	
	
	@Autowired
	protected ComentarioService comentarioService;

	@Autowired
	protected HiloService hiloService;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	private static int TEST_COMENTARIO_ID;
	
	
	
	@BeforeEach
	void setup() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
		usuario.setContrasena("qwerty123");
		this.usuarioService.save(usuario);
		
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleducado");
		hilo.setCategoria("maltrato psicológico");
		hilo.setContenido("abro hilo:");
		hilo.setUsuario(usuario);
		this.hiloService.save(hilo);
		
		Comentario comentario = new Comentario();
		comentario.setContenido("para k kieres saber eso jaja salu2");
		comentario.setUsuario(usuario);
		comentario.setHilo(hilo);
		this.comentarioService.save(comentario);
		TEST_COMENTARIO_ID = comentario.getId();
	}
	
	
	@DisplayName("Prueba de localización de comentario")
	@Test
	@Transactional
	void shouldFindById() {
		assertEquals(TEST_COMENTARIO_ID, this.comentarioService.findById(TEST_COMENTARIO_ID).get().getId());
	}
	
	
	@DisplayName("Prueba de localización de comentario errónea")
	@Test
	@Transactional
	void shouldNotFindById() {
		assertThrows(NoSuchElementException.class, () -> this.comentarioService.findById(56789).get().getContenido());
	}
	
	
	@DisplayName("Prueba de guardado de comentario")
	@Test
	@Transactional
	void shouldSave(){
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran2");
		usuario.setApellidos("Bel2");
		usuario.setLocalidad("El piso2");
		usuario.setColegio("La etsii2");
		usuario.setEmail("999999999992");
		usuario.setContrasena("qwerty1232");
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleducado2");
		hilo.setCategoria("maltrato psicológico2");
		hilo.setContenido("abro hilo:2");
		hilo.setUsuario(usuario);
		Comentario comentario = new Comentario();
		comentario.setContenido("para k kieres saber eso jaja salu3");
		comentario.setUsuario(usuario);
		comentario.setHilo(hilo);
		this.comentarioService.save(comentario);
		assertThat(comentario.getId().longValue()).isNotEqualTo(0);
		assertEquals("para k kieres saber eso jaja salu3", this.comentarioService.findById(comentario.getId()).get().getContenido());
	}
	
	@DisplayName("Prueba de borrado de comentario")
	@Test
	@Transactional
	void shouldDelete() {
		this.comentarioService.delete(this.comentarioService.findById(TEST_COMENTARIO_ID).get());
		assertThrows(NoSuchElementException.class, () -> this.comentarioService.findById(TEST_COMENTARIO_ID).get().getContenido());
	}

}
