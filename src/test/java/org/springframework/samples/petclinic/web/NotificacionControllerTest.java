package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.service.NotificacionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = NotificacionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class NotificacionControllerTest {

	private static final int TEST_NOTIFICACION_ID = 1;

	@Autowired
	private NotificacionController notificacionController;

	@MockBean
	private NotificacionService notificacionService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	private Notificacion notificacion;

	@BeforeEach
	void setup() {

		notificacion = new Notificacion();
		notificacion.setId(TEST_NOTIFICACION_ID);
		given(this.notificacionService.findById(TEST_NOTIFICACION_ID)).willReturn(notificacion);

	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testlistNotificaciones() throws Exception {
		mockMvc.perform(get("/notificaciones"))
				.andExpect(status().isOk()).andExpect(model()
				.attributeExists("notificaciones"))
				.andExpect(view().name("notificaciones/NotificacionesListing"));
	}

	
	
}
