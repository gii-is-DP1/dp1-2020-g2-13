package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	Collection<Usuario> findAll();
	
	Usuario findById(int id);

	@Query("SELECT u FROM Usuario u WHERE u.user.username = :username")
	Usuario findByUsername(@Param("username")String username);

	void delete(Usuario usuario);
	
}
