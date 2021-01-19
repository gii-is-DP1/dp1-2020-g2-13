package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

	@Autowired
	NotificacionRepository notificacionRepository;

	public Collection<Notificacion> findAll() {
		return notificacionRepository.findAll();
	}

	public Notificacion findById(int id) {
		return notificacionRepository.findById(id);
	}

	public void delete(Notificacion notificacion) {
		notificacionRepository.deleteById(notificacion.getId());
	}
	
	

//	public void save(@Valid Comentario comentario) throws ImpossibleComentarioException {
//		validateComentarioIsPossible(comentario);
//		comentarioRepository.save(comentario);
//	}

	public void save(@Valid Notificacion notificacion) {
		notificacionRepository.save(notificacion);
	}

	public Collection<Notificacion> findByUserId(int userid) {
		return notificacionRepository.findByUserId(userid);
	}

}
