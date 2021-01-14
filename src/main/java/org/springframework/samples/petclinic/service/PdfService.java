package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.repository.PdfRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PdfService {

	private PdfRepository pdfRepository;

	@Autowired
	public PdfService(PdfRepository pdfRepository) {
		this.pdfRepository = pdfRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Pdf> findAll() throws DataAccessException {
		return pdfRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Pdf findById(int id) throws DataAccessException {
		return pdfRepository.findById(id);
	}

	@Transactional
	public void delete(Pdf pdf) {
		pdfRepository.delete(pdf);

	}

	@Transactional
	public void save(Pdf pdf) {
		pdfRepository.save(pdf);
	}

}
