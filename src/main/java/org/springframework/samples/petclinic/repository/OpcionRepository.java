package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Opcion;

public interface OpcionRepository extends CrudRepository<Opcion, Integer> {

	Collection<Opcion> findAll();

	Opcion findById(int id);

	void delete(Opcion opcion);

}