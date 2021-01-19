package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class NotificacionServiceTest {

	@Autowired
	protected NotificacionService notificacionService;

	@Autowired
	protected MensajePrivadoService mensajePrivadoService;

	@Autowired
	protected ComentarioService comentarioService;

	@Autowired
	protected HiloService hiloService;
	
	@Autowired
	protected UsuarioService usuarioService;

	private static int TEST_NOTIFICACION_ID = 1;
	
	
	@DisplayName("Prueba de localización de notificacion")
	@Test
	void shouldFindPdfWithCorrectId() {
		Notificacion notificacion = this.notificacionService.findById(TEST_NOTIFICACION_ID);
		assertEquals(TEST_NOTIFICACION_ID, notificacion.getId());

	}


	@DisplayName("Prueba de guardado de notificacion")
	@Test
	@Transactional
	public void shouldsavePdf() {

		Notificacion notificacion = new Notificacion();
		Usuario usuario = this.usuarioService.findById(1);
		notificacion.setUsuario(usuario);
		this.notificacionService.save(notificacion);
		assertEquals(notificacion, this.notificacionService.findById(notificacion.getId()));
	}
	

	@DisplayName("Prueba de borrado de pdf")
	@Test
	void shouldDelete() {
		
		
		this.notificacionService.delete(this.notificacionService.findById(TEST_NOTIFICACION_ID));
		assertThrows(NullPointerException.class, () -> this.notificacionService.findById(TEST_NOTIFICACION_ID).getId());
		

	}
	
	@DisplayName("Prueba de localizado de notificaciones de un usuario")
	@Test
	void shouldfindByUserId() {
		Notificacion notificacion = new Notificacion();
		Usuario usuario = this.usuarioService.findById(1);
		notificacion.setUsuario(usuario);
		this.notificacionService.save(notificacion);
		List<Notificacion> notificaciones = new ArrayList<>(this.notificacionService.findByUserId(usuario.getId()));
		assertThat(notificaciones.get(0).getUsuario().getId()).isEqualTo(1);

	}

	@DisplayName("Prueba de generación de notificacion al enviar mensaje privado")
	@Test
	@Transactional
	public void shouldGenerateNotificationOnMessageRecieve() {
		int n = notificacionService.findAll().size();
		Usuario usuario1 = this.usuarioService.findById(1);
		Usuario usuario2 = this.usuarioService.findById(2);
		MensajePrivado m = new MensajePrivado();
		m.setContenido("Hola");
		m.setEmisor(usuario2);
		m.setReceptor(usuario1);
		mensajePrivadoService.save(m);
		assertThat(notificacionService.findAll().size()).isEqualTo(n+1);
	}

	@DisplayName("Prueba de generación de notificacion al comentarse en un hilo al que se está suscrito")
	@Test
	@Transactional
	public void shouldGenerateNotificationOnCommentInSubscThread() {
		int n = notificacionService.findAll().size();
		Usuario usuario1 = this.usuarioService.findById(1);
		Usuario usuario2 = this.usuarioService.findById(2);
		Hilo hilo = hiloService.findById(1);
		hiloService.suscribir(hilo, usuario1);
		Comentario comentario = new Comentario();
		comentario.setContenido("Hola");
		comentario.setUsuario(usuario2);
		comentario.setHilo(hilo);
		comentarioService.save(comentario);
		assertThat(notificacionService.findAll().size()).isEqualTo(n+1);
	}
}
