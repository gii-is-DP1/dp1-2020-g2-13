package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.util.EntityUtils;
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
	
	@Autowired
	protected UserService userService;
	
	private static int TEST_COMENTARIO_ID = 1;
	
	
	
//	@BeforeEach
//	void setup() {
//		Usuario usuario = new Usuario();
//		usuario.setNombre("Fran");
//		usuario.setApellidos("Bel");
//		usuario.setLocalidad("El piso");
//		usuario.setColegio("La etsii");
//		usuario.setEmail("99999999999");
//		usuario.setContrasena("qwerty123");
//		this.usuarioService.save(usuario);
//		
//		Hilo hilo = new Hilo();
//		hilo.setNombre("Profesorado maleducado");
//		hilo.setCategoria("maltrato psicológico");
//		hilo.setContenido("abro hilo:");
//		hilo.setUsuario(usuario);
//		this.hiloService.save(hilo);
//		
//		Comentario comentario = new Comentario();
//		comentario.setContenido("para k kieres saber eso jaja salu2");
//		comentario.setUsuario(usuario);
//		comentario.setHilo(hilo);
//		this.comentarioService.save(comentario);
//		TEST_COMENTARIO_ID = comentario.getId();
//	}
	
	
	@DisplayName("Prueba de localización de comentario")
	@Test
	@Transactional
	void shouldFindById() {

		Comentario comentario = this.comentarioService.findById(1);
		assertThat(comentario.getContenido()).startsWith("Hola");
		
	}

	@Test
	void shouldFindAllComentarios() {
		Collection<Comentario> comentarios = this.comentarioService.findAll();

		Comentario comentario1 = EntityUtils.getById(comentarios, Comentario.class, 1);
		assertThat(comentario1.getContenido()).isEqualTo("Hola_mundo");
		Comentario comentario2 = EntityUtils.getById(comentarios, Comentario.class, 2);
		assertThat(comentario2.getContenido()).isEqualTo("Adios_mundo");
	}
	
	
	@DisplayName("Prueba de localización de comentario errónea")
	@Test
	@Transactional
	void shouldNotFindById() {
		assertThrows(NullPointerException.class, () -> this.comentarioService.findById(56789).getContenido());
	}
	
	
	@DisplayName("Prueba de guardado de comentario")
	@Test
	@Transactional
	void shouldSave(){
		Collection<Comentario> comentarios = this.comentarioService.findAll();
		int size = comentarios.size();
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
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleducado2");
		hilo.setCategoria("maltrato psicológico2");
		hilo.setContenido("abro hilo:2");
		hilo.setUsuario(usuario);
		hiloService.save(hilo);
		Comentario comentario = new Comentario();
		comentario.setContenido("para k kieres saber eso jaja salu3");
		comentario.setUsuario(usuario);
		comentario.setHilo(hilo);
		this.comentarioService.save(comentario);
		assertThat(comentarioService.findAll().size()).isEqualTo(size + 1);
		assertEquals("para k kieres saber eso jaja salu3", this.comentarioService.findById(comentario.getId()).getContenido());
	}
	
	@DisplayName("Prueba de borrado de comentario")
	@Test
	@Transactional
	void shouldDelete() {
		this.comentarioService.delete(this.comentarioService.findById(TEST_COMENTARIO_ID));
		assertThrows(NullPointerException.class, () -> this.comentarioService.findById(TEST_COMENTARIO_ID).getContenido());
	}
	
	@DisplayName("Prueba de nivel de comentario")
	@Test
	@Transactional
	void shouldLevelUp(){
		User user = new User();
		user.setUsername("user");
		user.setPassword("Qwerty123");
		Usuario usuario = new Usuario();
		usuario.setUser(user);
		usuario.setNombre("Giorno");
		usuario.setApellidos("Giovana");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("109876543210");
		usuarioService.save(usuario);
		Hilo hilo = new Hilo();
		hilo.setNombre("Profesorado maleduca2");
		hilo.setCategoria("maltrato");
		hilo.setContenido("abro hilo:");
		hilo.setUsuario(usuario);
		hiloService.save(hilo);
		Comentario comentario = new Comentario();
		comentario.setUsuario(usuario);
		comentario.setHilo(hilo);
		comentario.setContenido("Pole");
		this.comentarioService.save(comentario);
		Comentario respuesta = new Comentario();
		respuesta.setContenido("Subpole");
		respuesta.setUsuario(usuario);
		respuesta.setHilo(hilo);
		respuesta.setCita(comentario);
		this.comentarioService.save(respuesta);
		assertEquals(comentario.getNivel() + 1, respuesta.getNivel());
	}
}
