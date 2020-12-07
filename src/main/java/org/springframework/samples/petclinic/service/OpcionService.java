package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.repository.OpcionRepository;
import org.springframework.stereotype.Service;

@Service
public class OpcionService {

	@Autowired
	OpcionRepository opcionRepository;

	public Collection<Opcion> findAll() {
		return opcionRepository.findAll();
	}

	public Opcion findById(int id) {
		return opcionRepository.findById(id);
	}

	public void delete(Opcion opcion) {
		opcionRepository.deleteById(opcion.getId());

	}

	public void save(@Valid Opcion opcion) {
		opcionRepository.save(opcion);
	}

}

