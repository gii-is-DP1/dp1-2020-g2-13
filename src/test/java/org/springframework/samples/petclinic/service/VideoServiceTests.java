package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.model.Video;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))

public class VideoServiceTests {
	
	
	
	@Autowired
	protected VideoService videoService;
	
	private static int TEST_VIDEO_ID;
	
	
	
	@BeforeEach
	void setup() {
		Video video = new Video();
		video.setLink("www.youtube.com");
		video.setDescripcion("po un video wapo");
		video.setDuracion("8:00");
		this.videoService.save(video);
		TEST_VIDEO_ID = video.getId();
	}
	
	
	@DisplayName("Prueba de localización de video")
	@Test
	void shouldFindById() {
		assertEquals(TEST_VIDEO_ID, this.videoService.findById(TEST_VIDEO_ID).getId());

	}
	
	
	@DisplayName("Prueba de localización de video errónea")
	@Test
	void shouldNotFindById() {
		assertThrows(NoSuchElementException.class, () -> this.videoService.findById(56789).getLink());

	}
	
	
	@DisplayName("Prueba de guardado de video")
	@Test
	void shouldSave(){
		Video video = new Video();
		video.setLink("www.youtube2.com");
		video.setDescripcion("po un video wapo2");
		video.setDuracion("8:02");
		this.videoService.save(video);
		assertThat(video.getId().longValue()).isNotEqualTo(0);
		assertEquals("www.youtube2.com", this.videoService.findById(video.getId()).getLink());

	}
	
	
	@DisplayName("Prueba de borrado de video")
	@Test
	void shouldDelete() {
		this.videoService.delete(this.videoService.findById(TEST_VIDEO_ID));
		assertThrows(NoSuchElementException.class, () -> this.videoService.findById(TEST_VIDEO_ID).getLink());
	}
	
	
	@Test
	@Transactional
	public void shouldUpdateDescripcion() throws Exception {
		Video video = this.videoService.findById(TEST_VIDEO_ID);

		String newDescripcion = "nueva descripcion";
		video.setDescripcion(newDescripcion);
		this.videoService.save(video);

		video = this.videoService.findById(TEST_VIDEO_ID);
		assertThat(video.getDescripcion()).isEqualTo(newDescripcion);
	}

}
