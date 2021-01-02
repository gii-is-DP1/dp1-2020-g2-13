package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Respuesta;

public interface RespuestaRepository extends CrudRepository<Respuesta, Integer>  {

	Collection<Respuesta> findAll();
	
	Respuesta findById(int id);
	
	void delete(Respuesta respuesta);
}
