package org.springframework.samples.petclinic.web;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Usuario;

import org.springframework.samples.petclinic.service.ExamenService;

import org.springframework.samples.petclinic.service.HiloService;

import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

	public static final String USUARIOS_FORM = "usuarios/createOrUpdateUsuariosForm";


	@Autowired
	UsuarioService usuarioService;
	@Autowired
	HiloService hiloService;

	@Autowired
	ExamenService examenService;


	@GetMapping
	public String listUsuarios(ModelMap model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return USUARIOS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, ModelMap model) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		if (usuario.isPresent()) {
			model.addAttribute("usuario", usuario.get());
			return USUARIOS_FORM;
		} else {
			model.addAttribute("message", "We cannot find the user you tried to edit!");
			return listUsuarios(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editPdf(@PathVariable("id") int id, @Valid Usuario modifiedUsuario, BindingResult binding,
			ModelMap model) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		if (binding.hasErrors()) {
			return USUARIOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedUsuario, usuario.get(), "id");
			usuarioService.save(usuario.get());
			model.addAttribute("message", "User updated succesfully!");
			return listUsuarios(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deletePdf(@PathVariable("id") int id, ModelMap model) {
		Optional<Usuario> usuario = usuarioService.findById(id);
		if (usuario.isPresent()) {
			usuarioService.delete(usuario.get());
			model.addAttribute("message", "The user was deleted successfully!");
			return listUsuarios(model);
		} else {
			model.addAttribute("message", "We cannot find the user you tried to delete!");
			return listUsuarios(model);
		}
	}

	@GetMapping("/new")
	public String editNewDisease(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return USUARIOS_FORM;
	}

	@PostMapping("/new")
	public String saveNewDisease(@Valid Usuario usuario, BindingResult binding, ModelMap model) {
		if (binding.hasErrors()) {
			return USUARIOS_FORM;
		} else {
			usuarioService.save(usuario);
			model.addAttribute("message", "The user was created successfully!");
			return listUsuarios(model);
		}
	}

	@GetMapping(value = "{usuarioId}/hilos")
	public String getPetClinicHistory(@PathVariable("usuarioId") int usuarioId, ModelMap model) {
		model.addAttribute("usuario", usuarioService.findById(usuarioId).get());
		model.addAttribute("hilos", hiloService.findByUsuarioId(usuarioId));
		return "usuarios/UsuarioHilos";
	}
	
	@GetMapping(value = "{usuarioId}/examenes")
	public String getExamenAuthor(@PathVariable("usuarioId") int usuarioId, ModelMap model) {
		model.addAttribute("usuario", usuarioService.findById(usuarioId).get());
		model.addAttribute("examenes", examenService.findByUsuarioId(usuarioId));
		return "usuarios/UsuarioHilos";
	}



}
