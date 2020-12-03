package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cuestionario;
import org.springframework.samples.petclinic.service.CuestionarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cuestionarios")
public class CuestionarioController {
	
	public static final String CUESTIONARIOS_FORM = "cuestionarios/createOrUpdateCuestionariosForm";
	public static final String CUESTIONARIOS_LISTING = "cuestionarios/CuestionariosListing";
	
	@Autowired
	CuestionarioService cuestionarioService;
	
	@GetMapping
	public String listCuestionarios(ModelMap model) {
		model.addAttribute("cuestionarios", cuestionarioService.findAll());
		return CUESTIONARIOS_LISTING;
	}

	@GetMapping("/{id}/edit")
	public String editCuestionario(@PathVariable("id") int id, ModelMap model) {
		Cuestionario cuestionario = cuestionarioService.findById(id);
		model.addAttribute("cuestionario", cuestionario);
		return CUESTIONARIOS_FORM;
	}

	@PostMapping("/{id}/edit")
	public String editCuestionario(@PathVariable("id") int id, @Valid Cuestionario modifiedCuestionario, BindingResult binding,
			ModelMap model) {
		Cuestionario cuestionario = cuestionarioService.findById(id);
		if (binding.hasErrors()) {
			return CUESTIONARIOS_FORM;
		} else {
			BeanUtils.copyProperties(modifiedCuestionario, cuestionario, "id");
			cuestionarioService.save(cuestionario);
			model.addAttribute("message", "Cuestionario updated succesfully!");
			return listCuestionarios(model);
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteCuestionario(@PathVariable("id") int id,ModelMap model) {
		Cuestionario cuestionario=cuestionarioService.findById(id);
		cuestionarioService.delete(cuestionario);
		model.addAttribute("message","The goal was deleted successfully!");
		return listCuestionarios(model);
	}
	
	@GetMapping("/new")
	public String editNewCuestionario(ModelMap model) {
		model.addAttribute("cuestionario",new Cuestionario());
		return CUESTIONARIOS_FORM;
	}
	
	@PostMapping("/new")
	public String saveNewCuestionario(@Valid Cuestionario cuestionario, BindingResult binding,ModelMap model) {
		if(binding.hasErrors()) {			
			return CUESTIONARIOS_FORM;
		}else {
			cuestionarioService.save(cuestionario);
			model.addAttribute("message", "The goal was created successfully!");			
			return listCuestionarios(model);
		}
	}
	
	

}
