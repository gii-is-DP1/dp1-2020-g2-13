package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pregunta;
import org.springframework.samples.petclinic.repository.PreguntaRepository;
import org.springframework.stereotype.Service;

@Service
public class PreguntaService {
	
	@Autowired
	PreguntaRepository preguntaRepository;
	
	public Collection<Pregunta> findAll(){	
		return  preguntaRepository.findAll();	
	}
	

	public Pregunta findById(int id) {
		return preguntaRepository.findById(id);
	}
	
	public void delete(Pregunta pregunta) {
		preguntaRepository.delete(pregunta);

	}
	

	public void save(Pregunta pregunta) {
		preguntaRepository.save(pregunta);
	}
}
