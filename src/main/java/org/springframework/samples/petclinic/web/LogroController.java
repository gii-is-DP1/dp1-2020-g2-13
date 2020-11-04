package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.LogroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logros")
public class LogroController {

	@Autowired
	LogroService logroService;
	
	@GetMapping
    public String listLogros(ModelMap model)
    {
        model.addAttribute("logros",logroService.findAll());
        return "logros/LogrosListing";
    }
	
	
}
