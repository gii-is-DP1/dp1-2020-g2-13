package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Hilo;

public interface HiloRepository extends CrudRepository<Hilo, Integer> {

	Collection<Hilo> findAll();

	Hilo findById(int id);
	
	@Query("SELECT h FROM Hilo h WHERE h.usuario.id = :usuarioId")
	Collection<Hilo> findByUsuarioId(@Param("usuarioId")int usuarioId);

	void delete(Hilo hilo);

}