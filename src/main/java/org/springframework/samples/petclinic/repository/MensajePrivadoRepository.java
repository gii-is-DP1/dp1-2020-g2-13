package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.MensajePrivado;

public interface MensajePrivadoRepository extends CrudRepository<MensajePrivado, Integer> {

	Collection<MensajePrivado> findAll();

	MensajePrivado findById(int id);

	@Query("SELECT m FROM MensajePrivado m WHERE (m.emisor.id = :emisorId AND m.receptor.id = :receptorId) "
			+ "OR (m.emisor.id = :receptorId AND m.receptor.id = :emisorId)")
	Collection<MensajePrivado> findByUsersId(@Param("emisorId")int emisorId,
			@Param("receptorId")int receptorId);
	
	void delete(MensajePrivado mensajePrivado);

}
