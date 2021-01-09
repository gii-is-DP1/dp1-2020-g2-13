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
import org.springframework.samples.petclinic.service.MensajePrivadoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = MensajePrivadoController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class MensajePrivadoControllerTest {

	private static final int TEST_SMSPRIV_ID = 1;

	@Autowired
	private MensajePrivadoController mensajePrivadoController;

	@MockBean
	private MensajePrivadoService mensajePrivadoService;

	@Autowired
	private MockMvc mockMvc;

	private MensajePrivado mensajePrivado;

	@BeforeEach
	void setup() {

		mensajePrivado = new MensajePrivado();
		mensajePrivado.setId(TEST_SMSPRIV_ID);
		given(this.mensajePrivadoService.findById(TEST_SMSPRIV_ID)).willReturn(mensajePrivado);

	}

	@WithMockUser(value = "spring")
	@Test
	void testlistNotificaciones() throws Exception {
		mockMvc.perform(get("/mensajesPrivados/{value}")).andExpect(status().isOk()).andExpect(model().attributeExists("mensajesPrivados"))
				.andExpect(view().name("mensajesPrivados/mensajesPrivadosListing"));
	}
	
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/mensajesPrivados/{value}/new").with(csrf()).param("contenido", "Ejemplo"))

				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/mensajesPrivados/{value}/new").with(csrf()).param("contenido", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("mensajePrivado"))
				.andExpect(view().name("mensajesPrivados/createOrUpdateMensajePrivadoForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePdfFormSuccess() throws Exception {
		mockMvc.perform(post("/mensajesPrivados/{value}/edit", TEST_SMSPRIV_ID).with(csrf()).param("contenido", "Ejemplo2"))
		
				.andExpect(status().isOk()).andExpect(view().name("mensajesPrivados/mensajesPrivadosListing"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePdfFormHasErrors() throws Exception {
		mockMvc.perform(post("/mensajesPrivados/{value}/edit", TEST_SMSPRIV_ID).with(csrf()).param("contenido", ""))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("mensajePrivado"))
				.andExpect(view().name("mensajesPrivados/mensajesPrivadosListing"));
	}
	
}
