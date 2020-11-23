package org.springframework.samples.petclinic.service;

import java.util.Collection;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

	@Autowired
	ComentarioRepository comentarioRepository;

	public Collection<Comentario> findAll() {
		return comentarioRepository.findAll();
	}

	public Optional<Comentario> findById(int id) {
		return comentarioRepository.findById(id);
	}

	public void delete(Comentario comentario) {
		comentarioRepository.deleteById(comentario.getId());

	}

	public void save(Comentario comentario) {
		comentarioRepository.save(comentario);
	}

	public Collection<Comentario> findByHiloId(int hiloid) {
		return comentarioRepository.findByHiloId(hiloid);
	}

}
