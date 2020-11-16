package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Hilo;

public interface HiloRepository extends CrudRepository<Hilo, Integer> {

	Collection<Hilo> findAll();

	Optional<Hilo> findById(int id);

	void delete(Hilo hilo);

}
