package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Intento;
import org.springframework.samples.petclinic.model.Opcion;
import org.springframework.samples.petclinic.model.Respuesta;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.IntentoService;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/intentos")
public class IntentoController {
	
	public static final String INTENTOS_LISTING = "intentos/intentosListing";
	
	@Autowired
	IntentoService intentoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/{id_usuario}")
	public String listIntentos(@PathVariable("id_usuario") int id_usuario, ModelMap model) {
		
		Usuario usuario= usuarioService.findById(id_usuario);
		List<Intento> intentosUsuario= usuario.getIntentos();
		List<String> titulos= new ArrayList<String>();
		List<List<Respuesta>> respuestas= new ArrayList<List<Respuesta>>();
		for(int i=0;i<intentosUsuario.size();i++){			
				titulos.add(intentosUsuario.get(i).getExamen().getTitulos());
				respuestas.add(intentosUsuario.get(i).getRespuestas());	
		}
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulos", titulos);
		model.addAttribute("intentos", intentosUsuario);
		model.addAttribute("respuestas", respuestas);
		return INTENTOS_LISTING;
	}
	
	

}
