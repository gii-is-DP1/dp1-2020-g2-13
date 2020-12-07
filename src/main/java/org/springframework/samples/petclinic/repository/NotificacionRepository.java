package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Notificacion;

public interface NotificacionRepository extends CrudRepository<Notificacion, Integer> {

	Collection<Notificacion> findAll();

	Notificacion findById(int id);

	@Query("SELECT n FROM Notificacion n WHERE n.usuario.id = :usuarioId")
	Collection<Notificacion> findByUserId(@Param("usuarioId") int usuarioId);

	@Query("SELECT n FROM Notificacion n WHERE n.mensajePrivado.id = :mensajePrivadoId")
	Collection<Notificacion> findByMensajeId(@Param("mensajePrivadoId") int mensajePrivadoId);

	void delete(Notificacion notificacion);

}
