package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.samples.petclinic.model.Usuario;

public interface RespuestaRepository extends CrudRepository<Respuesta, Integer>  {

	Collection<Respuesta> findAll();
	
	Respuesta findById(int id);

	@Query("SELECT r FROM Respuesta r WHERE r.intento.id = :intento")
	Collection<Respuesta> findByIntentoId(@Param("intento")int intento);
	
	void delete(Respuesta respuesta);
}
