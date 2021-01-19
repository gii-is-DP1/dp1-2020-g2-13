package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Logro;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
			return listLogros(model);
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
		return listLogros(model);
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
			model.addAttribute("message", "The goal was created successfully!");			
			return listLogros(model);
		}
	}
	
}
