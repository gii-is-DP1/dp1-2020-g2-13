package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Video;


public interface VideoRepository extends CrudRepository<Video, Integer> {
	
	Collection<Video> findAll();
	
	Video findById(int id);
	
	void delete(Video video);

}
