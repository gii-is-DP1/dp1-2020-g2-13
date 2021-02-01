package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(value = LogroController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class LogroControllerTests {

	private static final int TEST_LOGRO_ID = 1;

	@Autowired
	private LogroController logroController;

	@MockBean
	private LogroService logroService;

	@Autowired
	private MockMvc mockMvc;

	private Logro logro;

	@BeforeEach
	void setup() {

		Logro logro = new Logro();
		logro.setId(TEST_LOGRO_ID);
		logro.setNombre("Logro1");
		logro.setDescripcion("Poseso");
		given(this.logroService.findById(TEST_LOGRO_ID)).willReturn(logro);

	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/logros/new")).andExpect(status().isOk()).andExpect(model().attributeExists("logro"))
				.andExpect(view().name("logros/createOrUpdateLogrosForm"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/logros/new").with(csrf()).param("nombre", "Otro nombre").param("descripcion",
				"Otra descripcion")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/logros/new").with(csrf()).param("nombre", "").param("descripcion", "Ejemplo2"))
				.andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("logro"))
				.andExpect(view().name("logros/createOrUpdateLogrosForm"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testInitUpdateLogroForm() throws Exception {
		mockMvc.perform(get("/logros/{id}/edit", TEST_LOGRO_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("logro"))
				.andExpect(model().attribute("logro", hasProperty("nombre", is("Logro1"))))
				.andExpect(view().name("logros/createOrUpdateLogrosForm"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessUpdateLogroFormSuccess() throws Exception {
		mockMvc.perform(post("/logros/{id}/edit", TEST_LOGRO_ID).with(csrf()).param("nombre", "Ejemplo2")
				.param("descripcion", "Ejemplo2")).andExpect(status().isOk())
				.andExpect(view().name("logros/LogrosListing"));
	}

	@WithMockUser(value = "spring", authorities = { "admin", "registrado" })
	@Test
	void testProcessUpdateLogroFormHasErrors() throws Exception {
		mockMvc.perform(post("/logros/{id}/edit", TEST_LOGRO_ID).with(csrf()).param("nombre", "")
				.param("descripcion","Ejemplo2"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("logro"))
				.andExpect(view().name("logros/createOrUpdateLogrosForm"));
	}
}
