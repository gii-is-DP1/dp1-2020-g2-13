package org.springframework.samples.petclinic.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.CoreSearch;
import org.springframework.samples.petclinic.model.Result;
import org.springframework.samples.petclinic.service.ClientAPIService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/herramientas")
public class CoreSearchController {
	
	public static final String HERRAMIENTAS_LISTING = "herramientas/herramientasListing";
	public static final String MEJORAR_CUENTA = "usuarios/mejorarCuenta";
	public static final String LOGIN = "login";
	public static final String ERROR = "error";

	@GetMapping
	public String listadoHerramientas(ModelMap model) {
		if (!AuthController.isAuthenticated()) {
			return "redirect:/" + LOGIN;
		}
		if (!AuthController.hasPaid()) {
			return "redirect:/" + MEJORAR_CUENTA;
		}

		CoreSearch llamadaAPI;
		try {
			llamadaAPI = ClientAPIService.LlamadaLimeSurvey();
			List<Result> surveys = llamadaAPI.getResult();
			List<String> enlaces = new ArrayList<>();
			surveys = surveys.stream()
					.filter(x -> x.getActive().equals("Y"))
					.collect(Collectors.toList());
			
			log.info("Se ha llamado a la API de Limesurvey y se ha obtenido el coresearch: " + llamadaAPI);
			model.addAttribute("surveys", surveys);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		return HERRAMIENTAS_LISTING;	
	}
	
	
}
