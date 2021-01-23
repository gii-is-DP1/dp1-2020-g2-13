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
			model.addAttribute("message", "Documento actualizado");
			return listPdfs(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePdf(@PathVariable("id") int id,ModelMap model) {
		Pdf pdf=pdfService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioLoggeado = usuarioService.findByUsername(username);
		if (!pdf.getUsuario().equals(usuarioLoggeado) && !AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		pdfService.delete(pdf);
		model.addAttribute("message","Documento eliminado");
		return listPdfs(model);
	}
	
	@GetMapping("/new")
	public String editNewDisease(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		model.addAttribute("pdf",new Pdf());
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
			Usuario usuarioLoggeado = usuarioService.findByUsername(username);
			pdf.setUsuario(usuarioLoggeado);
			pdfService.save(pdf);
			model.addAttribute("message", "Nuevo documento creado");			
			return listPdfs(model);
		}
	}
	
}
