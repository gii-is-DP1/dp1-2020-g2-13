package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Examen;
import org.springframework.samples.petclinic.repository.ExamenRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamenService {

	@Autowired
	ExamenRepository examenRepository;

	public Collection<Examen> findAll() {
		return examenRepository.findAll();
	}

	public Examen findById(int id) {
		return examenRepository.findById(id);
	}

	public void delete(Examen examen) {
		examenRepository.deleteById(examen.getId());

	}

	public void save(Examen examen) {
		examenRepository.save(examen);
	}

	public Collection<Examen> findByUsuarioId(int usuarioid) {
		return examenRepository.findByUsuarioId(usuarioid);
	}
}
