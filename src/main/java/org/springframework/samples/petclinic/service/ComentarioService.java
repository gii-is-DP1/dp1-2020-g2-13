package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
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
		Collection<Comentario> comentarios = findAll();
		for (Comentario c : comentario.getCitas()) {
			delete(c);
		}
		for (Comentario c : comentarios) {
			try {
				Set<Comentario> s = c.getCitas();
				if (s.contains(comentario)) {
					s.remove(comentario);
					c.setCitas(s);
					comentarioRepository.save(c);
				}
			}
			catch (Exception e) {
				
			}
		}
		
		comentarioRepository.deleteById(comentario.getId());

	}

//	public void save(@Valid Comentario comentario) throws ImpossibleComentarioException {
//		validateComentarioIsPossible(comentario);
//		comentarioRepository.save(comentario);
//	}
	
	public void save(@Valid Comentario comentario) {
		try {
			comentario.setNivel(comentario.getCita().getNivel() + 1);
		}
		catch (Exception e) {
			comentario.setNivel(0);
		}
		comentarioRepository.save(comentario);
		Collection<Comentario> comentarios = findAll();
		for (Comentario c : comentarios) {
			try {
				Set<Comentario> s = new HashSet<>();
				s.addAll(findByCita(c.getId()));
				c.setCitas(s);
				comentarioRepository.save(c);
			}
			catch (Exception e) {
				
			}
		}
		try {
			List<Usuario> suscriptores = new ArrayList<>(comentario.getHilo().getSuscriptores());
			for (Usuario u : suscriptores) {
				if (!u.equals(comentario.getUsuario())) {
					Notificacion notificacion = new Notificacion();
					notificacion.setUsuario(u);
					notificacion.setComentario(comentario);
					notificacion.setMensajePrivado(null);
					notificacionRepository.save(notificacion);
				}
			}
		}
		catch (Exception e) {
			
		}
	}
	
	private void validateComentarioIsPossible(@Valid Comentario comentario) throws ImpossibleComentarioException {
		if(comentario.getContenido().trim().length() == 0)
			throw new ImpossibleComentarioException(comentario.getContenido());
	}

	public Collection<Comentario> findByHiloId(int hiloid) {
		return comentarioRepository.findByHiloId(hiloid);
	}

	public Collection<Comentario> findByCita(int cita) {
		return comentarioRepository.findByCita(cita);
	}
	
//	public Collection<Comentario> findByComentarioId(int comentarioid) {
//		return comentarioRepository.findByComentarioId(comentarioid);
//	}

}
