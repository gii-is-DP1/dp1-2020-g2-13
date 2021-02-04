package org.springframework.samples.petclinic.web;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.model.Usuario;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/logros")
public class LogroController {
	public static final String LOGROS_FORM = "logros/createOrUpdateLogrosForm";
	public static final String LOGROS_LISTING = "logros/LogrosListing";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";

	@Autowired
	LogroService logroService;
	@Autowired
	UsuarioService usuarioService;
	

	@InitBinder("logro")
	public void initLogroBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LogroValidator());
	}

	@GetMapping
	public String listLogros(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}
		String authority = AuthController.highestLevel();
		model.addAttribute("authority", authority);
		model.addAttribute("logros", logroService.findAll());
		return LOGROS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editLogro(@PathVariable("id") int id, ModelMap model) {
		if (!AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Logro logro = logroService.findById(id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		log.info("Logro editado con id: "+ id+" con el administrador con id: "+username);
		model.addAttribute("logro", logro);
		return LOGROS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editLogro(@PathVariable("id") int id, @Valid Logro modifiedLogro, BindingResult binding,
			ModelMap model) {
		Logro logro = logroService.findById(id);
		if (binding.hasErrors()) {
			return LOGROS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedLogro, logro, "id");
			logroService.save(logro);
			model.addAttribute("message", "Goal updated succesfully!");
			return "redirect:/" +"logros";
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteLogro(@PathVariable("id") int id,ModelMap model) {
		if (!AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		Logro logro=logroService.findById(id);
		logroService.delete(logro);
		model.addAttribute("message","The goal was deleted successfully!");
		return "redirect:/" +"logros";
	}
	
	@GetMapping("/new")
	public String editNewLogro(ModelMap model) {
		if (!AuthController.isAdmin()) {
			return "redirect:/" + ERROR;
		}
		model.addAttribute("logro",new Logro());
		return LOGROS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewLogro(@Valid Logro logro, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return LOGROS_FORM;
		}else {
			logroService.save(logro);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			log.info("Logro creado con id: "+ logro.getId()+" con el administrador con id: "+username);
			model.addAttribute("message", "The goal was created successfully!");			
			return "redirect:/" +"logros";
		}
	}
	
	public Logro addLogro(Logro logro) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuario = usuarioService.findByUsername(username);
		Set<Logro> logros = usuario.getLogros();
		logros.add(logro);
		usuario.setLogros(logros);
		usuarioService.save(usuario);
		return logro;
	}
	
}
