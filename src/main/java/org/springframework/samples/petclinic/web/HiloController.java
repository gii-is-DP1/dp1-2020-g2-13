package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hilo;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.HiloService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hilos")
public class HiloController {
	public static final String HILOS_FORM = "hilos/createOrUpdateHilosForm";
	public static final String HILOS_LISTING = "hilos/HilosListing";

	@Autowired
	HiloService hiloService;
	@Autowired
	UsuarioService usuarioService;

	@GetMapping
	public String listHilos(ModelMap model) {
		model.addAttribute("hilos", hiloService.findAll());
		return HILOS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editHilo(@PathVariable("id") int id, ModelMap model) {
		Optional<Hilo> hilo = hiloService.findById(id);
		Collection<Usuario> usuarios = usuarioService.findAll();
		if (hilo.isPresent()) {
			model.addAttribute("hilo", hilo.get());
			model.addAttribute("usuarios", usuarios);
			return HILOS_FORM;
		} else {
			model.addAttribute("message", "We cannot find the thread you tried to edit!");
			return listHilos(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editHilo(@PathVariable("id") int id, @Valid Hilo modifiedHilo, BindingResult binding,
			ModelMap model) {
		Optional<Hilo> hilo = hiloService.findById(id);
		if (binding.hasErrors()) {
			return HILOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedHilo, hilo.get(), "id");
			hiloService.save(hilo.get());
			model.addAttribute("message", "Thread updated succesfully!");
			return listHilos(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteHilo(@PathVariable("id") int id,ModelMap model) {
		Optional<Hilo> hilo=hiloService.findById(id);
		if(hilo.isPresent()) {
			hiloService.delete(hilo.get());
			model.addAttribute("message","The thread was deleted successfully!");
			return listHilos(model);
		}else {
			model.addAttribute("message","We cannot find the thread you tried to delete!");
			return listHilos(model);
		}
	}
	
	@GetMapping("/new")
	public String editNewHilo(ModelMap model) {
		Collection<Usuario> usuarios = usuarioService.findAll();
		model.addAttribute("hilo",new Hilo());
		model.addAttribute("usuarios", usuarios);
		return HILOS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewHilo(@Valid Hilo hilo, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return HILOS_FORM;
		}else {
			hiloService.save(hilo);
			model.addAttribute("message", "The thread was created successfully!");			
			return listHilos(model);
		}
	}
	
}
