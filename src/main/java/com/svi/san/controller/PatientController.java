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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.svi.san.entity.Patient;
import com.svi.san.exception.PatientNotFoundException;
import com.svi.san.service.IPatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private IPatientService patService;
	
	
	@GetMapping("/register")
	public String showRegister(@RequestParam(value = "message",required = false) String message,Model model) {
		model.addAttribute("message", message);
		return "PatientRegister";
	}
	
	@PostMapping("/save")
	public String savePatient(@ModelAttribute Patient patient,Model model,RedirectAttributes attribute ) {
		Long id=patService.savePatient(patient);
		attribute.addAttribute("message", id+" record saved");
		return "redirect:register";
	}
	
	@GetMapping("/all")
	public String getAllPatients(Model model,@RequestParam(value = "message",required = false) String message) {
		List<Patient> patList=patService.getAllPatients();
		model.addAttribute("patList", patList);
		model.addAttribute("message", message);
		return "PatientData";
	}
	@GetMapping("/delete")
	public String removePatient(@RequestParam Long id,RedirectAttributes attribute) {
		try {
			patService.remove(id);
			attribute.addAttribute("message", id+" record deleted");
		}catch (PatientNotFoundException e) {
			attribute.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}
	@GetMapping("/edit")
	public String editPatient(@RequestParam Long id,Model model,RedirectAttributes attribute) {
		String page=null;
		try {
			Patient patient=patService.getOnePatient(id);
			model.addAttribute("patient", patient);
			page="PatientEdit";
		}catch (PatientNotFoundException e) {
			attribute.addFlashAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
	}
	
	@PostMapping("/update")
	public String updatePatient(@ModelAttribute Patient patient,RedirectAttributes attribute) {
		try {
			patService.updatePatient(patient);
			attribute.addAttribute("message", patient.getId()+" record updated");
		}catch (PatientNotFoundException e) {
			attribute.addAttribute("message",e.getMessage());
		}
		return"redirect:all";
	}

}
