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
import org.springframework.samples.petclinic.model.Video;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.samples.petclinic.service.VideoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=VideoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class VideoControllerTests {
	
	private static final int TEST_VIDEO_ID = 1;
	
	@Autowired
	private VideoController videoController;
	
	@MockBean
	private VideoService videoService;
	@MockBean
	UsuarioService usuarioService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Video video;
	
	@BeforeEach
	void setup() {
		video = new Video();
		video.setId(TEST_VIDEO_ID);
		video.setNombre("paquito");
		video.setLink("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		video.setDescripcion("Never gonna give you up, never gonna let you down");
		video.setDuracion("21");
		given(this.videoService.findById(TEST_VIDEO_ID)).willReturn(video);
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/videos/new")).andExpect(status().isOk()).andExpect(model().attributeExists("video"))
			.andExpect(view().name("videos/CreateOrUpdateVideoForm"));
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
    @Test
    void testProcessCreationFormSuccess() throws Exception {
	mockMvc.perform(post("/videos/new").param("nombre", "paquito").param("link", "https://www.youtube.com/watch?v=dQw4w9WgXcQ").param("descripcion", "Never gonna give you up, never gonna let you down")
						.with(csrf())
						.param("duracion", "21"))
			.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
	mockMvc.perform(post("/videos/new")
						.with(csrf())
						.param("nombre", "")
						.param("link", "")
						.param("descripcion", "GuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebf"
								+ "iwbfuhwvbfuwebfuwevbfuwebvfjwevfjwevfhguwevfhuwvfhugwvfuwvfndjqnidniwuqfiweufuiwehfuiwhfbuiwehfuiwfhuiweh"
								+ " fui3whtfuiwehfuiewfndsjkahfuyrwbafcvhsaugfurewfooncccccccccccccjdjoqhowojdiofwjwjGuilleIsaFranMasiuPablito"
								+ "dnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfiwbfuhwvbfuwebfuwevbfuwebvfjwevfjwevfhguwevfhuwvfhugwvfuwvfndjqnidni"
								+ "wuqfiweufuiwehfuiwhfbuiwehfuiwfhuiweh fui3whtfuiwehfuiewfndsjkahfuyrwbafcvhsaugfurewfooncccccccccccccjdjoqhow"
								+ "ojdiofwjwj")
						.param("duracion", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("video"))
			.andExpect(model().attributeHasFieldErrors("video", "link"))
			.andExpect(model().attributeHasFieldErrors("video", "descripcion"))
			.andExpect(model().attributeHasFieldErrors("video", "duracion"))
			.andExpect(view().name("videos/CreateOrUpdateVideoForm"));
	}
	
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testInitUpdateVideoForm() throws Exception {
		mockMvc.perform(get("/videos/{id}/edit", TEST_VIDEO_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("video"))
				.andExpect(model().attribute("video", hasProperty("nombre", is("paquito"))))
				.andExpect(model().attribute("video", hasProperty("link", is("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))))
				.andExpect(model().attribute("video", hasProperty("descripcion", is("Never gonna give you up, never gonna let you down"))))
				.andExpect(model().attribute("video", hasProperty("duracion", is("21"))))
				.andExpect(view().name("videos/CreateOrUpdateVideoForm"));
	}
    
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdateVideoFormSuccess() throws Exception {
		mockMvc.perform(post("/videos/{id}/edit", TEST_VIDEO_ID)
							.with(csrf())
							.param("nombre", "paquito2")
							.param("link", "https://www.youtube.com/watch?v=cFqYtipAl7g")
							.param("descripcion", "El temaso")
							.param("duracion", "5:20")
							)
				.andExpect(status().isOk())
				.andExpect(view().name("videos/VideosListing"));
	}
    
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testProcessUpdateVideoFormHasErrors() throws Exception {
		mockMvc.perform(post("/videos/{id}/edit", TEST_VIDEO_ID)
							.with(csrf())
							.param("nombre", "")
							.param("link", "")
							.param("descripcion", "GuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablito"
									+ "dnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsa"
									+ "FranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwb"
									+ "fuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhweb"
									+ "fhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnw"
									+ "qibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMa"
									+ "siuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfG"
									+ "uilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwe"
									+ "bfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwebfhwebfhuwebfhwebfhuwbfuwebfGuilleIsaFranMasiuPablitodnwqibdfhwe"
									+ "bfhwebfhuwebfhwebfhuwbfuwebf")
							.param("duracion", ""))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("video"))
				.andExpect(view().name("videos/CreateOrUpdateVideoForm"));
	}
    
	@WithMockUser(value = "spring", authorities= {"admin", "registrado"})
	@Test
	void testVideoVisualizer() throws Exception {
		mockMvc.perform(get("/videos/{id}/visualize", TEST_VIDEO_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("video")).andExpect(view().name("videos/VideoVisualize"));
	}

}
