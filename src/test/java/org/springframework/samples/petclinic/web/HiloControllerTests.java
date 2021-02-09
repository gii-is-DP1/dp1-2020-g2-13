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
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=HiloController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class HiloControllerTests {

	private static final int TEST_HILO_ID = 1;

	@Autowired
	private HiloController hiloController;

	@MockBean
	private HiloService hiloService;

	@MockBean
	private ComentarioService comentarioService;

	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private LogroService logroService;
	@MockBean
	private LogroController logroController;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Hilo hilo;

	@BeforeEach
	void setup() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
//		usuario.setContrasena("qwerty123");

		hilo = new Hilo();
		hilo.setId(TEST_HILO_ID);
		hilo.setNombre("George");
		hilo.setCategoria("Franklin");
		hilo.setContenido("110 W. Liberty St.");
		hilo.setUsuario(usuario);
		given(this.hiloService.findById(TEST_HILO_ID)).willReturn(hilo);

	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
    @Test
	void testListing() throws Exception {
		mockMvc.perform(get("/hilos"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("hilos"))
				.andExpect(view().name("hilos/HilosListing"));
	}

	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test
	void testListingNotPremium() throws Exception {
		mockMvc.perform(get("/hilos"))
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/usuarios/mejorarCuenta"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/hilos/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("hilo"))
				.andExpect(view().name("hilos/createOrUpdateHilosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/hilos/new")
				.with(csrf())
				.param("nombre", "Ejemplo")
				.param("categoria", "Ejemplo2")
				.param("contenido", "Ejemplo3"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/hilos/new")
				.with(csrf())
				.param("nombre", "")
				.param("categoria", "")
				.param("contenido", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("hilo"))
				.andExpect(view().name("hilos/createOrUpdateHilosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitUpdateHiloForm() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
//		usuario.setContrasena("qwerty123");
		mockMvc.perform(get("/hilos/{id}/edit", TEST_HILO_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("hilo"))
				.andExpect(model().attribute("hilo", hasProperty("nombre", is("George"))))
				.andExpect(model().attribute("hilo", hasProperty("categoria", is("Franklin"))))
				.andExpect(model().attribute("hilo", hasProperty("contenido", is("110 W. Liberty St."))))
				.andExpect(view().name("hilos/createOrUpdateHilosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
		@Test
		void testProcessUpdateHiloFormSuccess() throws Exception {
			mockMvc.perform(post("/hilos/{id}/edit", TEST_HILO_ID)
					.with(csrf())
					.param("nombre", "George")
					.param("categoria", "Franklin")
					.param("contenido", "110 W. Liberty St."))
					.andExpect(status().isOk())
					.andExpect(view().name("hilos/HilosListing"));
		}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdateHiloFormHasErrors() throws Exception {
		mockMvc.perform(post("/hilos/{id}/edit", TEST_HILO_ID)
				.with(csrf())
				.param("nombre", "")
				.param("categoria", "")
				.param("contenido", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("hilo"))
				.andExpect(view().name("hilos/createOrUpdateHilosForm"));

	}
	
	
}
