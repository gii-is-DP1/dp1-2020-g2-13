package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {

	Collection<Comentario> findAll();

	Comentario findById(int id);

	@Query("SELECT c FROM Comentario c WHERE c.hilo.id = :hiloId AND c.cita = NULL")
	Collection<Comentario> findByHiloId(@Param("hiloId")int hiloId);

	@Query("SELECT c FROM Comentario c WHERE c.cita.id = :cita")
	Collection<Comentario> findByCita(@Param("cita")int cita);
	
//	@Query("SELECT c FROM Comentario c WHERE c.comentario.id = :comentarioId")
//	Collection<Comentario> findByComentarioId(@Param("comentarioId")int comentraioId);
	
	void delete(Comentario comentario);

}
