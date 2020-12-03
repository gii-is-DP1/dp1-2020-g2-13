package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cuestionario;

public interface CuestionarioRepository extends  CrudRepository<Cuestionario, Integer > {
	
	Collection<Cuestionario> findAll();
	
	Cuestionario findById(int id);
	
	void delete(Cuestionario cuestionario);

}
