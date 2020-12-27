package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Intento;
import org.springframework.samples.petclinic.repository.IntentoRepository;
import org.springframework.stereotype.Service;

@Service
public class IntentoService {

	@Autowired
	IntentoRepository intentoRepository;

	public Collection<Intento> findAll() {
		return intentoRepository.findAll();
	}

	public Intento findById(int id) {
		return intentoRepository.findById(id);
	}

	public void delete(Intento intento) {
		intentoRepository.deleteById(intento.getId());

	}

	public void save(@Valid Intento intento) {
		intentoRepository.save(intento);
	}
	
}
