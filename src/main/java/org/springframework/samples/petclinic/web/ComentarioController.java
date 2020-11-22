package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Comentario;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.ComentarioService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hilos/baisddho")
public class ComentarioController {
	public static final String COMENTARIOS_FORM = "comentarios/createOrUpdateComentariosForm";
	public static final String COMENTARIOS_LISTING = "comentarios/ComentariosListing";

	@Autowired
	ComentarioService comentarioService;
	
	@PostMapping("/new")
	public String saveNewComentario(@Valid Comentario comentario, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return "usuarios/UsuariosListing";
		}else {
			comentarioService.save(comentario);
			model.addAttribute("message", "El comentario se ha publicado.");			
			return "hilos/HilosListing";
		}
	}
	
}
