package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Examen;

public interface ExamenRepository extends CrudRepository<Examen, Integer> {

	Collection<Examen> findAll();

	Examen findById(int id);

	@Query("SELECT e FROM Examen e WHERE e.usuario.id = :usuarioId")
	Collection<Examen> findByUsuarioId(@Param("usuarioId") int usuarioId);

	void delete(Examen examen);

}
