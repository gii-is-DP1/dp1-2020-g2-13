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
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ExamenService;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.TipoTestService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PreguntaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PreguntaControllerTest {
	
	private static final int TEST_EXAMEN_ID = 1;
	
	private static final int TEST_PREGUNTA_ID = 1;
	
	private static final int TEST_PREGUNTA_ID_2 = 2;
	
	private static final int TEST_OPCION_ID = 1;
	
	private static final int TEST_USUARIO_ID = 1;
	
	private static final int TEST_TIPOTEST_ID = 1;
	
	@Autowired
	private PreguntaController PreguntaController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ExamenService examenService;
	
	@MockBean
	private PreguntaService preguntaService;
	
	@MockBean
	private OpcionService opcionService;
	
	@MockBean
	private ExamenController examenController;
	
	@MockBean
	TipoTestService tipoTestService;
	
	@MockBean
	private UsuarioService usuarioService;
	
	private Opcion opcion;
	private TipoTest tipoTest;
	private Pregunta pregunta;
	
	@BeforeEach
	void setup() {
		Examen e = new Examen();
		e.setId(3);
		e.setTitulos("Examen de DP");
		given(this.usuarioService.findById(1)).willReturn(new Usuario());
		given(this.examenService.findById(TEST_EXAMEN_ID)).willReturn(new Examen());
		
		pregunta= new Pregunta();
		pregunta.setId(TEST_PREGUNTA_ID);
		pregunta.setContenido("Pregunta sin opciones");
		given(this.preguntaService.findById(TEST_PREGUNTA_ID)).willReturn(pregunta);
		
		pregunta= new Pregunta();
		pregunta.setId(TEST_PREGUNTA_ID_2);
		pregunta.setContenido("Pregunta conn opciones");
		opcion= new Opcion();
		opcion.setId(TEST_OPCION_ID);
		opcion.setTexto("una opción");
		opcion.setEsCorrecta(true);
		
		tipoTest= new TipoTest();
		tipoTest.setId(TEST_TIPOTEST_ID);
		pregunta.setTipoTest(tipoTest);	
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/preguntas")).andExpect(status().isOk()).andExpect(model().attributeExists("preguntas"))
				.andExpect(view().name("preguntas/PreguntasListing"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/preguntas/{value}/new",TEST_EXAMEN_ID ).with(csrf()).param("contenido", "Ejemplo"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/preguntas/{value}/new",TEST_EXAMEN_ID).with(csrf()).param("contenido", " "))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("pregunta"))
				.andExpect(view().name("preguntas/createOrUpdatePreguntasForm"));
	}


//	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
//	@Test
//	void testProcessUpdatePreguntaFormSuccess() throws Exception {
//		mockMvc.perform(post("preguntas/{id}/edit", TEST_PREGUNTA_ID).with(csrf()).param("contenido", "Ejemplo"))
//				.andExpect(status().isOk()).andExpect(view().name("examenes/ExamenDetails"));
//	}
//
//	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
//	@Test
//	void testProcessUpdatePreguntaFormHasErrors() throws Exception {
//		mockMvc.perform(post("preguntas/{id}/edit", TEST_PREGUNTA_ID).with(csrf()).param("contenido", ""))
//				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("contenido"))
//				.andExpect(view().name("preguntas/createOrUpdatePreguntasForm"));
//	}

}
