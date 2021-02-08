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
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.IntentoService;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.RespuestaService;
import org.springframework.samples.petclinic.service.TipoTestService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ExamenController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ExamenControllerTest {

	private static final int TEST_USUARIO_ID = 1;

	private static final int TEST_EXAMEN_ID = 1;

	@Autowired
	private ExamenController examenController;
	
	

	@MockBean
	private ExamenService examenService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private IntentoService intentoService;

	@MockBean
	private RespuestaService respuestaService;
	
	@MockBean
	private PreguntaService preguntaService;
	
	@MockBean
	private OpcionService opcionService;

	@MockBean
	private TipoTestService tipoTestService;
	
	@MockBean
	private LogroService logroService;
	
	@MockBean
	private LogroController logroController;
	
//	@MockBean
//	private CustomErrorController customErrorController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Examen e = new Examen();
		e.setId(3);
		e.setTitulos("Examen de DP");
		e.setVersion(0);
		given(this.usuarioService.findById(TEST_USUARIO_ID)).willReturn(new Usuario());
		given(this.examenService.findById(TEST_EXAMEN_ID)).willReturn(new Examen());
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/examenes/new", TEST_EXAMEN_ID)).andExpect(status().isOk())
				.andExpect(view().name("examenes/createOrUpdateExamenesForm"))
				.andExpect(model().attributeExists("examen"));
	}
	
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/examenes/new", TEST_EXAMEN_ID).with(csrf())
							.param("titulos", "Examen de DP")
							.param("puntuacionMinima", "0.0")
							.param("puntuacionMaxima", "10.0"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/examenes/{id}"));
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/examenes/new", TEST_EXAMEN_ID).with(csrf())
							.param("titulos", "")
							.param("puntuacionMinima", "0.0")
							.param("puntuacionMaxima", "10.0"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("examen"))
				.andExpect(view().name("examenes/createOrUpdateExamenesForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test 
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/examenes/{id}/edit", TEST_EXAMEN_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("examen"))
				.andExpect(view().name("examenes/createOrUpdateExamenesForm"));
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
	mockMvc.perform(post("/examenes/{id}/edit", TEST_EXAMEN_ID).with(csrf())
						.param("titulos", "Examen de DP")
						.param("puntuacionMinima", "0.0")
						.param("puntuacionMaxima", "10.0"))
			.andExpect(status().isOk())
			.andExpect(view().name("examenes/ExamenesListing"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
	mockMvc.perform(post("/examenes/{id}/edit", TEST_EXAMEN_ID).with(csrf())
						.param("titulos", "Examen de DP")
						.param("puntuacionMinima", " ")
						.param("puntuacionMaxima", "10.0"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasFieldErrors("examen"))
			.andExpect(view().name("examenes/createOrUpdateExamenesForm"));
	}
	
	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test 
	void testGetExamenNewTry() throws Exception {
		mockMvc.perform(get("/{examen_id}/newTry", TEST_EXAMEN_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examen"))
				.andExpect(model().attributeExists("intento"))
				.andExpect(model().attributeExists("respuesta"))
				.andExpect(view().name("examenes/examenTry"));
	}
	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test 
	void testGetExamenContinue() throws Exception {
		mockMvc.perform(get("/{intento_id}/continue", TEST_EXAMEN_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examen"))
				.andExpect(model().attributeExists("intento"))
				.andExpect(model().attributeExists("respuesta"))
				.andExpect(view().name("examenes/examenTry"));
	}
	
	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test 
	void testPostExamenTryNextQuestion() throws Exception {
		mockMvc.perform(get("/{examen_id}/newTry", TEST_EXAMEN_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examen"))
				.andExpect(model().attributeExists("intento"))
				.andExpect(model().attributeExists("respuesta"))
				.andExpect(view().name("examenes/examenTry"));
	}
	
	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test 
	void testPostExamenTryFinish() throws Exception {
		mockMvc.perform(get("/{examen_id}/newTry", TEST_EXAMEN_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examen"))
				.andExpect(view().name("examenes/examenListing"));
	}
	
	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test 
	void testPostExamenContinueNextQuestion() throws Exception {
		mockMvc.perform(get("/{intento_id}/continue", TEST_EXAMEN_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examen"))
				.andExpect(model().attributeExists("intento"))
				.andExpect(model().attributeExists("respuesta"))
				.andExpect(view().name("examenes/examenTry"));
	}
	
	@WithMockUser(value = "spring", authorities= {"registrado"})
	@Test 
	void testPostExamenContinueFinish() throws Exception {
		mockMvc.perform(get("/{intento_id}/continue", TEST_EXAMEN_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examen"))
				.andExpect(view().name("examenes/examenListing"));
	}
}