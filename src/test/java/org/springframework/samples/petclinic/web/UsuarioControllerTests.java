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

import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.HiloService;

import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = UsuarioController.class, 
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), 
		excludeAutoConfiguration = SecurityConfiguration.class)

public class UsuarioControllerTests {
	private static final int TEST_USUARIO_ID = 1;

	@Autowired
	private UsuarioController usuarioController;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private HiloService hiloService;
	@MockBean
	private ExamenService examenService;
	@Autowired
	private MockMvc mockMvc;

	private Usuario usuario;

	@BeforeEach
	void setup() {

		usuario = new Usuario();
		usuario.setId(TEST_USUARIO_ID);
		usuario.setNombre("Fran");
		usuario.setApellidos("Bel");
		usuario.setLocalidad("El piso");
		usuario.setColegio("La etsii");
		usuario.setEmail("99999999999");
//		usuario.setContrasena("qwerty123");
		given(this.usuarioService.findById(TEST_USUARIO_ID)).willReturn(usuario);
	}


	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/usuarios/new")).andExpect(status().isOk()).andExpect(model().attributeExists("usuario"))
				.andExpect(view().name("usuarios/createOrUpdateUsuariosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/usuarios/new").with(csrf()).param("nombre", "Ejemplo").param("apellidos", "Ejemplo2")
				.param("localidad", "Ejemplo3").param("colegio", "Ejemplo4").param("email", "Ejemplo5")
				.param("contrasena", "Ejemplo6")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/usuarios/new").with(csrf()).param("nombre", "").param("apellidos", "Ejemplo2")
				.param("localidad", "Ejemplo3").param("colegio", "Ejemplo4").param("email", "Ejemplo5")
				.param("contrasena", "Ejemplo6")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("usuario"))
				.andExpect(view().name("usuarios/createOrUpdateUsuariosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitUpdateUsuarioForm() throws Exception {
		mockMvc.perform(get("/usuarios/{id}/edit", TEST_USUARIO_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("usuario"))
				.andExpect(model().attribute("usuario", hasProperty("nombre", is("Fran"))))
				.andExpect(model().attribute("usuario", hasProperty("apellidos", is("Bel"))))
				.andExpect(model().attribute("usuario", hasProperty("localidad", is("El piso"))))
				.andExpect(model().attribute("usuario", hasProperty("colegio", is("La etsii"))))
				.andExpect(model().attribute("usuario", hasProperty("email", is("99999999999"))))
//				.andExpect(model().attribute("usuario", hasProperty("contrasena", is("qwerty123"))))
				.andExpect(view().name("usuarios/createOrUpdateUsuariosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
		@Test
		void testProcessUpdateUsuarioFormSuccess() throws Exception {
			mockMvc.perform(post("/usuarios/{id}/edit", TEST_USUARIO_ID)
					.with(csrf())
					.param("nombre", "Fran")
					.param("apellidos", "Bel")
					.param("localidad", "El piso")
					.param("colegio", "La etsii")
					.param("email", "99999999999")
					.param("contrasena", "qwerty123"))
					.andExpect(status().isOk())
					.andExpect(view().name("usuarios/UsuariosListing"));
		}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdateUsuarioFormHasErrors() throws Exception {
		mockMvc.perform(post("/usuarios/{id}/edit", TEST_USUARIO_ID)
							.with(csrf())
							.param("nombre", "").param("apellidos", "Ejemplo2")
							.param("localidad", "Ejemplo3").param("colegio", "Ejemplo4").param("email", "Ejemplo5")
							.param("contrasena", "Ejemplo6"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("usuario"))
				.andExpect(view().name("usuarios/createOrUpdateUsuariosForm"));

	}
}
