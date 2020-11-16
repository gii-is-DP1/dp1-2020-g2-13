package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	public static final String USUARIOS_LISTING = "usuarios/UsuariosListing";
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public String listUsuarios(ModelMap model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return 	USUARIOS_LISTING;
	}
	
	

}
