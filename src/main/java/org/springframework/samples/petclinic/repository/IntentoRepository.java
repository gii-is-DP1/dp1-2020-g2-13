package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Intento;


public interface IntentoRepository extends CrudRepository<Intento, Integer>  {

	Collection<Intento> findAll();
	
	Intento findById(int id);
	
	void delete(Intento intento);
}
