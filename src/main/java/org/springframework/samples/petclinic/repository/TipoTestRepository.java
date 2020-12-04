package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.TipoTest;

public interface TipoTestRepository extends CrudRepository<TipoTest, Integer> {
	
	Collection<TipoTest> findAll();
	
	TipoTest findById(int id);
	
	void delete(TipoTest tipotest);
}
