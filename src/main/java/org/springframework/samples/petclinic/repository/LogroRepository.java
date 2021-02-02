package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Logro;

public interface LogroRepository extends  CrudRepository<Logro, Integer > {
	
	Collection<Logro> findAll();

	Logro findById(int id);
	void delete(Logro logro);
	
	@Query("SELECT u FROM Logro u WHERE u.nombre = :nombre")
	Logro findByName(@Param("nombre")String nombre);

}
