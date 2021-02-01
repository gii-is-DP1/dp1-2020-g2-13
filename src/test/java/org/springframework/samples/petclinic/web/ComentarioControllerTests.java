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
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(value = ComentarioController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class ComentarioControllerTests {

	private static final int TEST_COMENTARIO_ID = 1;

	private static final int TEST_USUARIO_ID = 1;

	private static final int TEST_HILO_ID = 1;

	@Autowired
	private ComentarioController comentarioController;


	@MockBean
	private ComentarioService comentarioService;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private HiloService hiloService;
	
	@MockBean
	private NotificacionService notificacionService;
        

	@Autowired
	private MockMvc mockMvc;

	private Comentario comentario;

	private Usuario usuario;

	private Hilo hilo;

	@BeforeEach
	void setup() {
		
		usuario = new Usuario();
		usuario.setId(TEST_USUARIO_ID);
		usuario.setNombre("Pablito");
		usuario.setApellidos("Hola Adiós");
//		usuario.setContrasena("Hola");
		usuario.setEmail("hola@us.exe");
		usuario.setLocalidad("Valencina");;
		given(this.usuarioService.findById(TEST_USUARIO_ID)).willReturn(usuario);
		
		hilo = new Hilo();
		hilo.setId(TEST_HILO_ID);
		hilo.setNombre("Prueba");
		hilo.setCategoria("General");
		hilo.setUsuario(usuario);
		hilo.setContenido("Hola a todos");
		given(this.hiloService.findById(TEST_HILO_ID)).willReturn(hilo);

		comentario = new Comentario();
		comentario.setId(TEST_COMENTARIO_ID);
		comentario.setContenido("Hola Pablito");
		comentario.setHilo(hilo);
		comentario.setUsuario(usuario);
		given(this.comentarioService.findById(TEST_COMENTARIO_ID)).willReturn(comentario);

	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
        @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/hilos/{id}/{cita}/new", TEST_HILO_ID, TEST_COMENTARIO_ID))
		.andExpect(status().isOk()).andExpect(model().attributeExists("hilo"))
		.andExpect(view().name("comentarios/createOrUpdateComentariosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
        @Test
	void testProcessCitationSuccess() throws Exception {
		mockMvc.perform(post("/hilos/{id}/{cita}/new", TEST_HILO_ID, TEST_COMENTARIO_ID)
							.with(csrf())
							.param("contenido", "Hola"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/hilos/{id}/new", TEST_HILO_ID)
							.with(csrf())
							.param("contenido", "Hola"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
        @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/hilos/{id}/new", TEST_HILO_ID)
							.with(csrf())
							.param("contenido", " "))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("comentario"))
				.andExpect(view().name("comentarios/createOrUpdateComentariosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
        @Test
	void testInitCitationForm() throws Exception {
		mockMvc.perform(get("/hilos/{id}/new", TEST_HILO_ID))
		.andExpect(status().isOk()).andExpect(model().attributeExists("hilo"))
		.andExpect(view().name("comentarios/createOrUpdateComentariosForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
        @Test
	void testSubscribe() throws Exception {
		mockMvc.perform(get("/hilos/{id}/subscribe", TEST_HILO_ID))
		.andExpect(view().name("comentarios/ComentariosListing"));
	}
}
