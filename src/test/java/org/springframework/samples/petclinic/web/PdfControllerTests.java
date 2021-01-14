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
import org.springframework.samples.petclinic.service.PdfService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */

@WebMvcTest(value = PdfController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PdfControllerTests {

	private static final int TEST_PDF_ID = 1;

	@Autowired
	private PdfController pdfController;

	@MockBean
	private PdfService pdfService;

	@MockBean
	private UsuarioService usuarioService;

	@Autowired
	private MockMvc mockMvc;

	private Pdf pdf;

	@BeforeEach
	void setup() {

		pdf = new Pdf();
		pdf.setId(TEST_PDF_ID);
		pdf.setLink("Documento");
		pdf.setNombre("paquito");
		given(this.pdfService.findById(TEST_PDF_ID)).willReturn(pdf);

	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/pdfs/new")).andExpect(status().isOk()).andExpect(model().attributeExists("pdf"))
				.andExpect(view().name("pdfs/createOrUpdatePdfsForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/pdfs/new").with(csrf()).param("link", "Ejemplo").param("nombre", "paquito2"))

				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/pdfs/new").with(csrf()).param("link", "").param("nombre", "")).andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("pdf"))
				.andExpect(view().name("pdfs/createOrUpdatePdfsForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitUpdatePdfForm() throws Exception {
		mockMvc.perform(get("/pdfs/{id}/edit", TEST_PDF_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("pdf"))
				.andExpect(model().attribute("pdf", hasProperty("link", is("Documento"))))
				.andExpect(model().attribute("pdf", hasProperty("nombre", is("paquito"))))
				.andExpect(view().name("pdfs/createOrUpdatePdfsForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdatePdfFormSuccess() throws Exception {
		mockMvc.perform(post("/pdfs/{id}/edit", TEST_PDF_ID).with(csrf()).param("link", "Ejemplo")
				.param("nombre", "paquito12"))
		
				.andExpect(status().isOk()).andExpect(view().name("pdfs/PdfsListing"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdatePdfFormHasErrors() throws Exception {
		mockMvc.perform(post("/pdfs/{id}/edit", TEST_PDF_ID).with(csrf()).param("link", "")
				.param("nombre", ""))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("pdf"))
				.andExpect(view().name("pdfs/createOrUpdatePdfsForm"));
	}

	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testPdfVisualizer() throws Exception {
		mockMvc.perform(get("/pdfs/{id}/visualize", TEST_PDF_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("pdf")).andExpect(view().name("pdfs/PdfVisualize"));
	}
}
