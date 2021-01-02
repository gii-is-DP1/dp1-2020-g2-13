package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Usuario;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
	@Query("SELECT a FROM Authorities a WHERE a.user.username = :username")
	Collection<Authorities> findByUsername(@Param("username")String username);
	
	
}
