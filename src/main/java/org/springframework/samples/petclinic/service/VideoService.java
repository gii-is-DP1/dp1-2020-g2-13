package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.model.Video;
import org.springframework.samples.petclinic.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
	
	@Autowired
	VideoRepository videoRepository;
	
	public Collection<Video> findAll() {
		return videoRepository.findAll();
	}
	
	public Video findById(int id) {
		return videoRepository.findById(id);
	}
	
	public void delete(Video video) {
		videoRepository.deleteById(video.getId());

	}
	
	public void save(Video video) {
		videoRepository.save(video);
	}

}
