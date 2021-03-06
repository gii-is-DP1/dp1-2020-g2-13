package org.springframework.samples.petclinic.service;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.repository.LogroRepository;
import org.springframework.stereotype.Service;

@Service
public class LogroService {
	
	@Autowired
	LogroRepository logroRepository;
	
	public Collection<Logro> findAll(){	
		return  logroRepository.findAll();	
	}
	

	public Logro findById(int id) {
		return logroRepository.findById(id);
	}
	
	public void delete(Logro logro) {
		logroRepository.deleteById(logro.getId());

	}
	

	public void save(Logro logro) {
		logroRepository.save(logro);
	}

	public Logro findByName(String name) {
		return logroRepository.findByName(name);
	}
	
	

}

