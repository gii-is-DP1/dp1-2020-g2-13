package org.springframework.samples.petclinic.web;

import java.util.Optional;

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

	@Autowired
	LogroService logroService;
	

	@InitBinder("logro")
	public void initLogroBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new LogroValidator());
	}

	@GetMapping
	public String listLogros(ModelMap model) {
		model.addAttribute("logros", logroService.findAll());
		return LOGROS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editLogro(@PathVariable("id") int id, ModelMap model) {
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
		Logro logro=logroService.findById(id);
		logroService.delete(logro);
		model.addAttribute("message","The goal was deleted successfully!");
		return listLogros(model);
	}
	
	@GetMapping("/new")
	public String editNewDisease(ModelMap model) {
		model.addAttribute("logro",new Logro());
		return LOGROS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewDisease(@Valid Logro logro, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return LOGROS_FORM;
		}else {
			logroService.save(logro);
			model.addAttribute("message", "The goal was created successfully!");			
			return listLogros(model);
		}
	}
	
}
