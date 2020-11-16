package org.springframework.samples.petclinic.service;

import java.util.Collection;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.repository.PdfRepository;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

	@Autowired
	PdfRepository pdfRepository;

	public Collection<Pdf> findAll() {
		return pdfRepository.findAll();
	}

	public Optional<Pdf> findById(int id) {
		return pdfRepository.findById(id);
	}

	public void delete(Pdf pdf) {
		pdfRepository.deleteById(pdf.getId());

	}

	public void save(Pdf pdf) {
		pdfRepository.save(pdf);
	}

}
