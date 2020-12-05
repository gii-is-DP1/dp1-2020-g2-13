package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {

	Collection<Comentario> findAll();

	Comentario findById(int id);

	@Query("SELECT c FROM Comentario c WHERE c.hilo.id = :hiloId")
	Collection<Comentario> findByHiloId(@Param("hiloId")int hiloId);
	
	@Query("SELECT c FROM Comentario c WHERE c.comentario.id = :comentarioId")
	Collection<Comentario> findByComentarioId(@Param("comentarioId")int comentraioId);
	
	void delete(Comentario comentario);

}
