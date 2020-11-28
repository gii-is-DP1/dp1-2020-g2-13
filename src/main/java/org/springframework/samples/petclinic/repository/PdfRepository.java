package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Pdf;

public interface PdfRepository extends Repository<Pdf, Integer> {

	@Query("SELECT document FROM Pdf document ORDER BY document.archivo")
	Collection<Pdf> findAll() throws DataAccessException;

	Pdf findById(int id);

	void delete(Pdf pdf);

	void save(Pdf pdf) throws DataAccessException;

}
