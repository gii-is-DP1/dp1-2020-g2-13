package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cuestionario;
import org.springframework.samples.petclinic.repository.CuestionarioRepository;
import org.springframework.stereotype.Service;

@Service
public class CuestionarioService {

	@Autowired
	CuestionarioRepository cuestionarioRepository;
	
	public Collection<Cuestionario> findAll() {
		return cuestionarioRepository.findAll();	
	}
	
	public Cuestionario findById(int id) {
		return cuestionarioRepository.findById(id);
	}
	
	public void delete(Cuestionario cuestionario) {
		cuestionarioRepository.deleteById(cuestionario.getId());

	}

	public void save(Cuestionario cuestionario) {
		cuestionarioRepository.save(cuestionario);
	}
	
}
