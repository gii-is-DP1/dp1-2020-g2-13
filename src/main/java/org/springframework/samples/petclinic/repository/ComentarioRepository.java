package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {

	Collection<Comentario> findAll();

	Optional<Comentario> findById(int id);

	void delete(Comentario comentario);

}
