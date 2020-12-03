package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pregunta;

public interface PreguntaRepository extends  CrudRepository<Pregunta, Integer > {
	
	Collection<Pregunta> findAll();

	Pregunta findById(int id);
	void delete(Pregunta pregunta);

}
