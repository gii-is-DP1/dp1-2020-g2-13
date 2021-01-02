package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.samples.petclinic.repository.RespuestaRepository;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

	@Autowired
	RespuestaRepository respuestaRepository;

	public Collection<Respuesta> findAll() {
		return respuestaRepository.findAll();
	}

	public Respuesta findById(int id) {
		return respuestaRepository.findById(id);
	}

	public void delete(Respuesta Respuesta) {
		respuestaRepository.deleteById(Respuesta.getId());

	}

	public void save(@Valid Respuesta Respuesta) {
		respuestaRepository.save(Respuesta);
	}
	
}
