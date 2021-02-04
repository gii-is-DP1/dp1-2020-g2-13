package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pdf;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.PdfService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/pdfs")
public class PdfController {
	public static final String PDFs_FORM = "pdfs/createOrUpdatePdfsForm";
	public static final String PDFs_LISTING = "pdfs/PdfsListing";
	public static final String PDFs_VISUALIZE = "pdfs/PdfVisualize";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";

	
	private final PdfService pdfService;
	private final UsuarioService usuarioService;

	@Autowired
	public PdfController(PdfService pdfService, UsuarioService usuarioService) {
		this.pdfService = pdfService;
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String listPdfs(ModelMap model) {
		model.addAttribute("pdfs", pdfService.findAll());
		String authority = "";
		if (AuthController.isAuthenticated()) {
			authority = AuthController.highestLevel();
		}
		model.addAttribute("authority", authority);
		return PDFs_LISTING;
	}
	
	@GetMapping("/{id}/visualize")
	public String visualizePdfs(@PathVariable("id") int id, ModelMap model) {
		Pdf pdf = pdfService.findById(id);
		model.addAttribute("pdf", pdf);
		return PDFs_VISUALIZE;
	}
	
	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		Pdf pdf = pdfService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!pdf.getUsuario().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		model.addAttribute("pdf", pdf);
		model.addAttribute("usuario", usuarioLoggeado);
		return PDFs_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, @Valid Pdf modifiedPdf, BindingResult binding,
			ModelMap model, @RequestParam(value="version", required= false) Integer version) {
		Pdf pdf = pdfService.findById(id);
		if(pdf.getVersion()!=version) {	
			model.put("message", "Alguien ha modificado simultáneamente el pdf, prueba otra vez");
			return editPdf(id, model);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();	
		if (binding.hasErrors()) {
			model.addAttribute("message", "Documento inválido.");
			return PDFs_FORM;
		} else {
			modifiedPdf.setVersion(version+1);
			log.info("Editando el pdf con id: "+id+ " y con versión actual " + modifiedPdf.getVersion()+" por el usuario: "+username);
			BeanUtils.copyProperties(modifiedPdf, pdf, "id");
			pdfService.save(pdf);
			model.addAttribute("message", "Documento actualizado");
			return "redirect:/" + "pdfs";
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePdf(@PathVariable("id") int id,ModelMap model) {
		Pdf pdf=pdfService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		log.info("Eliminando el pdf con id: "+id+" por el usuario: "+username);
	    Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!pdf.getUsuario().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		pdfService.delete(pdf);
		model.addAttribute("message","Documento eliminado");
		return "redirect:/"+"pdfs";
	}
	
	@GetMapping("/new")
	public String editNewDisease(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		model.addAttribute("pdf",new Pdf());
		model.addAttribute("usuario", usuarioLoggeado);
		return PDFs_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewPdf(@Valid Pdf pdf, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {
			model.addAttribute("message", "Documento inválido.");
			return PDFs_FORM;
		}else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			pdf.setVersion(0);
			log.info("Creando el pdf con id: "+pdf.getId()+" por el usuario: " + username + " y con versión "+ pdf.getVersion());
			pdfService.save(pdf);
			model.addAttribute("message", "Nuevo documento creado");			
			return "redirect:/" + "pdfs";
		}
	}
	
}
