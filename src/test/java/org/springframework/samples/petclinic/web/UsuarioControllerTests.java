package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

public class UsuarioControllerTests {

	@Autowired
	private UsuarioController usuarioController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(value = "spring")
    @Test
void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/usuarios/new")).andExpect(status().isOk()).andExpect(model().attributeExists("usuario"))
			.andExpect(view().name("owners/createOrUpdateUsuariosForm"));
}
}
