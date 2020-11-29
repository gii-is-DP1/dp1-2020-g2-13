package org.springframework.samples.petclinic.service;

import java.util.Collection;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.repository.HiloRepository;
import org.springframework.stereotype.Service;

@Service
public class HiloService {

	@Autowired
	HiloRepository hiloRepository;

	public Collection<Hilo> findAll() {
		return hiloRepository.findAll();
	}

	public Hilo findById(int id) {
		return hiloRepository.findById(id);
	}

	public void delete(Hilo hilo) {
		hiloRepository.deleteById(hilo.getId());

	}

	public void save(@Valid Hilo hilo) {
		hiloRepository.save(hilo);
	}

	public Collection<Hilo> findByUsuarioId(int usuarioid) {
		return hiloRepository.findByUsuarioId(usuarioid);
	}

}

