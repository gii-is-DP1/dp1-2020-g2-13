package org.springframework.samples.petclinic.service;

import java.util.Collection;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.MensajePrivado;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.businessrulesexceptions.ImpossibleComentarioException;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.samples.petclinic.repository.MensajePrivadoRepository;
import org.springframework.samples.petclinic.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

@Service
public class MensajePrivadoService {

	@Autowired
	MensajePrivadoRepository mensajePrivadoRepository;

	@Autowired
	NotificacionRepository notificacionRepository;

	public Collection<MensajePrivado> findAll() {
		return mensajePrivadoRepository.findAll();
	}

	public MensajePrivado findById(int id) {
		return mensajePrivadoRepository.findById(id);
	}

	public void delete(MensajePrivado mensajePrivado) {
		Notificacion notificacion = notificacionRepository.findByMensajeId(mensajePrivado.getId()).iterator().next();
		notificacionRepository.delete(notificacion);
		mensajePrivadoRepository.deleteById(mensajePrivado.getId());
	}

//	public void save(@Valid Comentario comentario) throws ImpossibleComentarioException {
//		validateComentarioIsPossible(comentario);
//		comentarioRepository.save(comentario);
//	}

	public void save(@Valid MensajePrivado mensajePrivado) {
		mensajePrivadoRepository.save(mensajePrivado);
		Notificacion notificacion = new Notificacion();
		notificacion.setUsuario(mensajePrivado.getReceptor());
		notificacion.setMensajePrivado(mensajePrivado);
		notificacion.setComentario(null);
		notificacionRepository.save(notificacion);
	}

	public Collection<MensajePrivado> findByUsersId(int emisorid, int receptorid) {
		return mensajePrivadoRepository.findByUsersId(emisorid, receptorid);
	}

}
