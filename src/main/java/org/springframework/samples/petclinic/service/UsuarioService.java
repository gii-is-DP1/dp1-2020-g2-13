package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.model.businessrulesexceptions.ImpossibleComentarioException;
import org.springframework.samples.petclinic.repository.PdfRepository;
import org.springframework.samples.petclinic.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public Collection<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Usuario findById(int id) {
		return usuarioRepository.findById(id);
	}

	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	public void delete(Usuario usuario) {
		usuarioRepository.deleteById(usuario.getId());
	}

	public void save(@Valid Usuario usuario) {
		usuarioRepository.save(usuario);
		userService.saveUser(usuario.getUser());
		authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "registrado");
	}
	
	public void upgradeAccount(@Valid Usuario usuario) {
//		Authorities authorities = new Authorities();
//		authorities.setAuthority("pagado");
//		Set<Authorities> auth = new HashSet<>();
//		auth.add(authorities);
//		usuario.getUser().setAuthorities(auth);
		authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "pagado");
		usuario.setFechaPago(LocalDate.now());
		usuarioRepository.save(usuario);
	}
	
	public void downgradeAccount(@Valid Usuario usuario) {
//		Authorities authorities = new Authorities();
//		authorities.setAuthority("pagado");
//		Set<Authorities> auth = new HashSet<>();
//		auth.add(authorities);
//		usuario.getUser().setAuthorities(auth);
		authoritiesService.deleteAuthorities(usuario.getUser().getUsername(), "pagado");
//		usuarioRepository.save(usuario);
	}

	public void hacerAdministrador(@Valid Usuario usuario) {
//		Authorities authorities = new Authorities();
//		authorities.setAuthority("pagado");
//		Set<Authorities> auth = new HashSet<>();
//		auth.add(authorities);
//		usuario.getUser().setAuthorities(auth);
		authoritiesService.saveAuthorities(usuario.getUser().getUsername(), "admin");
//		usuarioRepository.save(usuario);
	}
	
	public void quitarAdministrador(@Valid Usuario usuario) {
//		Authorities authorities = new Authorities();
//		authorities.setAuthority("pagado");
//		Set<Authorities> auth = new HashSet<>();
//		auth.add(authorities);
//		usuario.getUser().setAuthorities(auth);
		authoritiesService.deleteAuthorities(usuario.getUser().getUsername(), "admin");
//		usuarioRepository.save(usuario);
	}
}
