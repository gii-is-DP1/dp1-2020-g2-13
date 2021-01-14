package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.model.Video;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class VideoServiceTests {

	@Autowired
	protected VideoService videoService;
	@Autowired
	protected UsuarioService usuarioService;
	
	@DisplayName("Prueba de localización de vídeo")
	@Test
	void shouldFindPdfWithCorrectId() {
		Video video = this.videoService.findById(1);
		assertEquals(1, video.getId());

	}
	
	@Test
	void shouldSave(){
		Usuario usuario = usuarioService.findById(1);
		Video video = new Video();
		video.setUsuario(usuario);
		video.setLink("www.youtube2.com");
		video.setDescripcion("po un video wapo2");
		video.setDuracion("8:02");
		video.setNombre("pepe");
		this.videoService.save(video);
		assertThat(video.getId().longValue()).isNotEqualTo(0);
		assertEquals(video.getLink(), this.videoService.findById(video.getId()).getLink());

	}
	
	@Test
	void shouldFindAllVideos() {
		Collection<Video> videos = this.videoService.findAll();
		Video video1 = EntityUtils.getById(videos, Video.class, 1);
		assertThat(video1.getLink()).isEqualTo("https://www.youtube.com/watch?v=HEydV4B6mRQ");
		
	}
	
	@Test
	@Transactional
	public void shouldUpdateVideoDescription() throws Exception {
		Video video1 = this.videoService.findById(1);
		String oldlink = video1.getLink();

		String newLink= oldlink + "X";
		video1.setLink(newLink);
		this.videoService.save(video1);

		video1 = this.videoService.findById(1);
		assertThat(video1.getLink()).isEqualTo(newLink);
	}

}
