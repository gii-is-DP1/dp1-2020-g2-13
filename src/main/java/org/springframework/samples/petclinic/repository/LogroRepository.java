package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Logro;

public interface LogroRepository extends  CrudRepository<Logro, Integer > {
	
	Collection<Logro> findAll();

	Optional<Logro> findById(int id);
	void delete(Logro logro);

}
