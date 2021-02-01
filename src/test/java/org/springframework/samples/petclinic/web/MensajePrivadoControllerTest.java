package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.MensajePrivadoService;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = MensajePrivadoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MensajePrivadoControllerTest {

	private static final int TEST_USUARIO1_ID = 1;
	private static final int TEST_USUARIO2_ID = 2;
	private static final int TEST_MENSAJEPRIVADO_ID = 1;

	@Autowired
	private MensajePrivadoController mensajePrivadoController;

	@MockBean
	private MensajePrivadoService mensajePrivadoService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private UserService userService;

	@MockBean
	private NotificacionService notificacionService;

	@Autowired
	private MockMvc mockMvc;

	private MensajePrivado mensajePrivado;

	private Usuario usuario1;
	private Usuario usuario2;

	@BeforeEach
	void setup() {
		usuario1 = new Usuario();
		usuario1.setId(TEST_USUARIO1_ID);
		usuario1.setNombre("Pablito");
		usuario1.setApellidos("Hola Adiós");
		usuario1.setEmail("hola@us.exe");
		usuario1.setLocalidad("Valencina");
		given(this.usuarioService.findById(TEST_USUARIO1_ID)).willReturn(usuario1);

		usuario2 = new Usuario();
		usuario2.setId(TEST_USUARIO2_ID);
		usuario2.setNombre("Pablito");
		usuario2.setApellidos("Hola Adiós");
		usuario2.setEmail("hola@us.exe");
		usuario2.setLocalidad("Valencina");
		given(this.usuarioService.findById(TEST_USUARIO2_ID)).willReturn(usuario2);

		mensajePrivado = new MensajePrivado();
		mensajePrivado.setId(TEST_MENSAJEPRIVADO_ID);
		mensajePrivado.setEmisor(usuario1);
		mensajePrivado.setReceptor(usuario2);
		mensajePrivado.setContenido("hola");
		mensajePrivado.setId(TEST_MENSAJEPRIVADO_ID);
		given(this.mensajePrivadoService.findById(TEST_MENSAJEPRIVADO_ID)).willReturn(mensajePrivado);

	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testListing() throws Exception {
		mockMvc.perform(get("/mensajesPrivados/{id}", TEST_USUARIO2_ID)).andExpect(status().isOk())
//				.andExpect(model().attributeExists("mensajePrivado"))
				.andExpect(view().name("mensajesPrivados/mensajesPrivadosListing"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/mensajesPrivados/{id}/new", TEST_USUARIO2_ID).with(csrf()).param("contenido", "Ejemplo"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/mensajesPrivados/{id}/new", TEST_USUARIO2_ID).with(csrf()).param("contenido", ""))
				.andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("mensajePrivado"))
				.andExpect(view().name("mensajesPrivados/createOrUpdateMensajePrivadoForm"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessUpdateMensajeFormSuccess() throws Exception {
		mockMvc.perform(
				post("/mensajesPrivados/{id}/edit", TEST_MENSAJEPRIVADO_ID).with(csrf()).param("contenido", "Ejemplo2"))
				.andExpect(status().isOk()).andExpect(view().name("mensajesPrivados/mensajesPrivadosListing"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessUpdateMensajeFormHasErrors() throws Exception {
		mockMvc.perform(
				post("/mensajesPrivados/{id}/edit", TEST_MENSAJEPRIVADO_ID).with(csrf()).param("contenido", ""))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("mensajePrivado"))
				.andExpect(view().name("mensajesPrivados/mensajesPrivadosListing"));
	}

}
