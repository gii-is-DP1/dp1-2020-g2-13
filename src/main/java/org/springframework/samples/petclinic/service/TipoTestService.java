package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TipoTest;
import org.springframework.samples.petclinic.repository.TipoTestRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoTestService {

	@Autowired
	TipoTestRepository tipoTestRepository;

	public Collection<TipoTest> findAll() {
		return tipoTestRepository.findAll();
	}

	public TipoTest findById(int id) {
		return tipoTestRepository.findById(id);
	}

	public void delete(TipoTest tipoTest) {
		tipoTestRepository.deleteById(tipoTest.getId());

	}

	public void save(@Valid TipoTest tipoTest) {
		tipoTestRepository.save(tipoTest);
	}

}

