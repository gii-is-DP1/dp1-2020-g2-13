package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Pdf;

public interface PdfRepository extends CrudRepository<Pdf, Integer> {

	Collection<Pdf> findAll();

	Optional<Pdf> findById(int id);

	void delete(Pdf pdf);

}
