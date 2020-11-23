package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.service.PdfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pdfs")
public class PdfController {
	public static final String HILOS_FORM = "pdfs/createOrUpdatePdfsForm";
	public static final String HILOS_LISTING = "pdfs/PdfsListing";

	@Autowired
	PdfService pdfService;

	@GetMapping
	public String listPdfs(ModelMap model) {
		model.addAttribute("pdfs", pdfService.findAll());
		return HILOS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		Optional<Pdf> pdf = pdfService.findById(id);
		if (pdf.isPresent()) {
			model.addAttribute("pdf", pdf.get());
			return HILOS_FORM;
		} else {
			model.addAttribute("message", "We cannot find the pdf you tried to edit!");
			return listPdfs(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, @Valid Pdf modifiedPdf, BindingResult binding,
			ModelMap model) {
		Optional<Pdf> pdf = pdfService.findById(id);
		if (binding.hasErrors()) {
			return HILOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedPdf, pdf.get(), "id");
			pdfService.save(pdf.get());
			model.addAttribute("message", "Pdf updated succesfully!");
			return listPdfs(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePdf(@PathVariable("id") int id,ModelMap model) {
		Optional<Pdf> pdf=pdfService.findById(id);
		if(pdf.isPresent()) {
			pdfService.delete(pdf.get());
			model.addAttribute("message","The thread was deleted successfully!");
			return listPdfs(model);
		}else {
			model.addAttribute("message","We cannot find the thread you tried to delete!");
			return listPdfs(model);
		}
	}
	
	@GetMapping("/new")
	public String editNewDisease(ModelMap model) {
		model.addAttribute("pdf",new Pdf());
		return HILOS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewDisease(@Valid Pdf pdf, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return HILOS_FORM;
		}else {
			pdfService.save(pdf);
			model.addAttribute("message", "The thread was created successfully!");			
			return listPdfs(model);
		}
	}
	
}
