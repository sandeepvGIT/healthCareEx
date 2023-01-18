package com.svi.san.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.svi.san.entity.Specialization;
import com.svi.san.exception.SpecializationNotFoundException;
import com.svi.san.service.ISpecializationService;
import com.svi.san.view.SpecializationExcel;

@Controller
/*Annotation for mapping web requests onto methods in request-handling classes with flexible method signatures. */
@RequestMapping("/spec")
public class SpecializationController {
	@Autowired
	private ISpecializationService specService;
	/**
	 * add the specialization
	 */
	@GetMapping("/register")
	public String addSpecialization() {
		return "SpecializationRegister";
	}
	
	@PostMapping("/save")
	public String saveSpecialization(Model model,@ModelAttribute Specialization specialization) {
		Long id=specService.saveSpecialization(specialization);
		String message="Record saved with id "+id;
		//add to model attribute
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}
	
	/**
	 * display the all specialization
	 */
	@GetMapping("/all")
	public String viewAllSpecialization(Model model,@RequestParam(value = "message",required = false) String message) {
		List<Specialization> specList=specService.getAllSpecialization();
		//add as model attribute
		model.addAttribute("specList", specList);
		model.addAttribute("message", message);
		//return lvn
		return "SpecializationData";
	}
	
	/**
	 * remove specialization
	 */
	@GetMapping("/delete")
	public String removeSpecialization(@RequestParam Long id,RedirectAttributes attributes) {
		try {
			specService.removeSpecialization(id);
			attributes.addAttribute("message", "Record "+id+" removed");
		}catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}
	/**
	 * Fetch data into Edit page
	 */
	@GetMapping("/edit")
	public String specializationEdit(@RequestParam Long id,Model model,RedirectAttributes attribute) {
		String path="";
		try {
			Specialization spec=specService.getOneSpecailization(id);
			model.addAttribute("specialization", spec);
			path="SpecializationEdit";
		}catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attribute.addAttribute("message", e.getMessage());
			path="redirect:all";
		}
		return path;
	}
	/**
	 * update specialization and redirect to all
	 */
	@PostMapping("/update")
	public String updateSpecialization(@ModelAttribute Specialization specialization,RedirectAttributes attribute) {
		Long id=specService.updateSpecialization(specialization);
		attribute.addAttribute("message", "Record "+id+" updated");
		return"redirect:all";
		
	}
	/**
	 * Read code and check .
	 * Is code exist.
	 */
	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(@RequestParam String code) {
		String message="";
		if(specService.isSpecCodeExist(code)) {
			message=code+", Already exist";
		}
		return message;
	}
	/**
	 * Read name and check .
	 * Is name exist.
	 */
	@GetMapping("/checkName")
	/*Annotation that indicates a method return value should be bound to the web response body. 
	 * Supported for annotated handler methods*/
	@ResponseBody
	public String validateSpecName(@RequestParam String name) {
		String message="";
		if(specService.isSpecNameExist(name)) {
			message=name+", Already exist";
		}
		return message;
	}
	
	/**
	 * Read note and check .
	 * Is note exist.
	 */
	@GetMapping("/checkNote")
	@ResponseBody
	public String validateSpecNote(@RequestParam String note) {
		String message="";
		if(specService.isSpecNoteExist(note)) {
			message=note+", Already exist";
		}
		return message;
		
	}
	/***
	  export data to excel 
	 * */
	@GetMapping("/export")
	public ModelAndView exportToExcel() {
		ModelAndView view=new ModelAndView();
		view.setView(new SpecializationExcel());
		//read the form data from DB
		List<Specialization> specList=specService.getAllSpecialization();
		//send to Excel  Impl class
		view.addObject("specList",specList);
		return view;
	}
	

}
