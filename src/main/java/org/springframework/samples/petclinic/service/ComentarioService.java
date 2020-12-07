package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Notificacion;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.model.businessrulesexceptions.ImpossibleComentarioException;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.samples.petclinic.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

	@Autowired
	ComentarioRepository comentarioRepository;

	@Autowired
	NotificacionRepository notificacionRepository;

	public Collection<Comentario> findAll() {
		return comentarioRepository.findAll();
	}

	public Comentario findById(int id) {
		return comentarioRepository.findById(id);
	}

	public void delete(Comentario comentario) {
		comentarioRepository.deleteById(comentario.getId());

	}

//	public void save(@Valid Comentario comentario) throws ImpossibleComentarioException {
//		validateComentarioIsPossible(comentario);
//		comentarioRepository.save(comentario);
//	}
	
	public void save(@Valid Comentario comentario) {
		comentarioRepository.save(comentario);
//		for (Usuario u : usuarios) {
//			if (!u.equals(comentario.getUsuario())) {
//				Notificacion notificacion = new Notificacion();
//				notificacion.setUsuario(u);
//				notificacion.setComentario(comentario);
//				notificacion.setMensajePrivado(null);
//				notificacionRepository.save(notificacion);
//			}
//		}
	}
	
	private void validateComentarioIsPossible(@Valid Comentario comentario) throws ImpossibleComentarioException {
		if(comentario.getContenido().trim().length() == 0)
			throw new ImpossibleComentarioException(comentario.getContenido());
	}

	public Collection<Comentario> findByHiloId(int hiloid) {
		return comentarioRepository.findByHiloId(hiloid);
	}
	
//	public Collection<Comentario> findByComentarioId(int comentarioid) {
//		return comentarioRepository.findByComentarioId(comentarioid);
//	}

}
