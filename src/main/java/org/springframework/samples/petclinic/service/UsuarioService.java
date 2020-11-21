package org.springframework.samples.petclinic.service;

import java.util.Collection;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Collection<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(int id) {
		return usuarioRepository.findById(id);
	}

	public void delete(Usuario usuario) {
		usuarioRepository.deleteById(usuario.getId());
	}

	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public Optional<Usuario> findById(int id) {
		return usuarioRepository.findById(id);
	}
	public void delete(Usuario usuario) {
		usuarioRepository.deleteById(usuario.getId());
	}
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	

}
