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
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.service.OpcionService;
import org.springframework.samples.petclinic.service.PreguntaService;
import org.springframework.samples.petclinic.service.TipoTestService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = OpcionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class OpcionControllerTest {
	private static final int TEST_OPCION_ID = 1;

	@Autowired
	private OpcionController opcionController;

	@MockBean
	private OpcionService opcionService;
	@MockBean
	private PreguntaService preguntaService;
	@MockBean
	private TipoTestService tipoTestService;
	@MockBean
	private ExamenController examenController;

	@Autowired
	private MockMvc mockMvc;

	private Opcion opcion;

	@BeforeEach
	void setup() {

		opcion = new Opcion();
		opcion.setId(TEST_OPCION_ID);
		opcion.setTexto("dfhb");
		opcion.setEsCorrecta(true);
		
		given(this.opcionService.findById(TEST_OPCION_ID)).willReturn(opcion);

	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/{id_examen}/{id_pregunta}/new",1,1)).andExpect(status().isOk()).andExpect(model().attributeExists("opcion"))
				.andExpect(view().name("opciones/createOrUpdateOpcionesForm"));
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/{id_examen}/{id_pregunta}/new",1,1).with(csrf()).param("texto", "Ejemplo"))

				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/{id_examen}/{id_pregunta}/new",1,1).with(csrf()).param("texto", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("pdf"))
				.andExpect(view().name("opciones/createOrUpdateOpcionesForm"));
	}

}
