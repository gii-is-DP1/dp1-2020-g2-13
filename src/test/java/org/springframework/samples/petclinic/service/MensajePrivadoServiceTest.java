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
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MensajePrivadoServiceTest {

	@Autowired
	protected MensajePrivadoService mensajePrivadoService;
	
	@Autowired
	protected UsuarioService usuarioService;

	private static int TEST_SMSPRIV_ID = 1;
	
	@DisplayName("Prueba de localización de mensaje privado")
	@Test
	void shouldFindPdfWithCorrectId() {
		MensajePrivado smspriv = this.mensajePrivadoService.findById(TEST_SMSPRIV_ID);
		assertEquals(TEST_SMSPRIV_ID, smspriv.getId());

	}
	
	@DisplayName("Prueba de guardado de mensaje privado")
	@Test
	@Transactional
	public void shouldsavePdf() {

		MensajePrivado smspriv = new MensajePrivado();
		smspriv.setContenido("ejemplo");
		this.mensajePrivadoService.save(smspriv);
		assertEquals(smspriv, this.mensajePrivadoService.findById(smspriv.getId()));
	}
	@DisplayName("Prueba de actualización de mensaje privado")
	@Test
	@Transactional
	public void shouldUpdatePdfName() throws Exception {
		MensajePrivado smspriv = this.mensajePrivadoService.findById(1);
		String oldcontent = smspriv.getContenido();

		String newContent = oldcontent + "X";
		smspriv.setContenido(newContent);
		this.mensajePrivadoService.save(smspriv);

		MensajePrivado smspriv2 = this.mensajePrivadoService.findById(1);
		assertThat(smspriv2.getContenido()).isEqualTo(newContent);
	}

	@DisplayName("Prueba de borrado de mensaje privado")
	@Test
	void shouldDelete() {
		
		
		this.mensajePrivadoService.delete(this.mensajePrivadoService.findById(TEST_SMSPRIV_ID));
		assertThrows(NullPointerException.class, () -> this.mensajePrivadoService.findById(TEST_SMSPRIV_ID).getContenido());
		

	}
	
	@DisplayName("Prueba de localizado de notificaciones de un usuario")
	@Test
	void shouldfindByUserId() { //Posible fallo detectado, orden de emisor y receptor
		MensajePrivado smspriv = new MensajePrivado();
		smspriv.setContenido("hola");
		Usuario usuario1 = this.usuarioService.findById(1);
		Usuario usuario2 = this.usuarioService.findById(2);
		smspriv.setReceptor(usuario2);
		smspriv.setEmisor(usuario1);
		this.mensajePrivadoService.save(smspriv);
		List<MensajePrivado> smsprivs = new ArrayList<>(this.mensajePrivadoService.findByUsersId(usuario1.getId(), usuario2.getId()));
		assertThat(smsprivs.get(0).getEmisor().getId()).isEqualTo(2);
		assertThat(smsprivs.get(0).getReceptor().getId()).isEqualTo(1);

	}
	
	
	
}
