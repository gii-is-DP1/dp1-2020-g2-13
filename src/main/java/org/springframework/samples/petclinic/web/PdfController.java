package org.springframework.samples.petclinic.web;

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
	public static final String PDFs_FORM = "pdfs/createOrUpdatePdfsForm";
	public static final String PDFs_LISTING = "pdfs/PdfsListing";
	
	private final PdfService pdfService;

	@Autowired
	public PdfController(PdfService pdfService) {
		this.pdfService = pdfService;
	}

	@GetMapping
	public String listPdfs(ModelMap model) {
		model.addAttribute("pdfs", pdfService.findAll());
		return PDFs_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		Pdf pdf = pdfService.findById(id);
		model.addAttribute("pdf", pdf);
		return PDFs_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, @Valid Pdf modifiedPdf, BindingResult binding,
			ModelMap model) {
		Pdf pdf = pdfService.findById(id);
		if (binding.hasErrors()) {
			model.addAttribute("message", "Documento inválido.");
			return PDFs_FORM;
		} else {
			BeanUtils.copyProperties(modifiedPdf, pdf, "id");
			pdfService.save(pdf);
			model.addAttribute("message", "Pdf updated succesfully!");
			return listPdfs(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePdf(@PathVariable("id") int id,ModelMap model) {
		Pdf pdf=pdfService.findById(id);
		pdfService.delete(pdf);
		model.addAttribute("message","The thread was deleted successfully!");
		return listPdfs(model);
	}
	
	@GetMapping("/new")
	public String editNewDisease(ModelMap model) {
		model.addAttribute("pdf",new Pdf());
		return PDFs_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewPdf(@Valid Pdf pdf, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {
			model.addAttribute("message", "Documento inválido.");
			return PDFs_FORM;
		}else {
			pdfService.save(pdf);
			model.addAttribute("message", "The thread was created successfully!");			
			return listPdfs(model);
		}
	}
	
}
