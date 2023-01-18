package com.svi.san.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.svi.san.entity.Doctor;
import com.svi.san.exception.DoctorNotFoundException;
import com.svi.san.service.IDoctorService;
import com.svi.san.service.ISpecializationService;
import com.svi.san.util.MyMailUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private IDoctorService docService;
	@Autowired
	private ISpecializationService specService;
	@Autowired
	private MyMailUtil mailUtil;
	
	private void getDynamicDropDownUi(Model model) {
		model.addAttribute("specializations", specService.getSpecIdAndName());
	}
	
	@GetMapping("/register")
	public String showRegister(Model model,@RequestParam(value = "message",required = false)String message) {
		model.addAttribute("message", message);
		getDynamicDropDownUi(model);
		return "DoctorRegister";
	}
	
	@PostMapping("/save")
	public String saveDoctor(@ModelAttribute Doctor doctor,RedirectAttributes attribute) {
		Long id=docService.saveDoctor(doctor);
		String regMsg="successfully register with "+id;
		attribute.addAttribute("message",id+" record saved");
		//send mail to doctor
		if(id!=null) {
			new Thread(new Runnable(){
				public void run() {
				                                    mailUtil.send(doctor.getEmail(),"success", regMsg,
						                                              new ClassPathResource("/static/myres/doctor.jpg"));}
			}).start();
		}
		return "redirect:register";
		
	}
	
	@GetMapping("/all")
	public String viewAllDoctors(Model model,@RequestParam(value = "message",required = false)String message) {
		List<Doctor> docList=docService.getAllDoctor();
		model.addAttribute("docList", docList);
		model.addAttribute("message", message);
		return "DoctorData";
	}
	
	@GetMapping("/delete")
	public String removeDoctor(@RequestParam Long id,RedirectAttributes attribute) {
		try {
			docService.removeDoctor(id);
			attribute.addFlashAttribute("message",id+" record deleted");
		}catch (DoctorNotFoundException e) {
			e.printStackTrace();
			attribute.addFlashAttribute("message",id+" record not found");
		}
		return"redirect:all";
	}
	
	@GetMapping("/edit")
	public String editDoctor(@RequestParam Long id,Model model,RedirectAttributes attribute) {
		String page=null;
		try {
			Doctor doc=docService.getOneDoctor(id);
			model.addAttribute("doctor", doc);
			getDynamicDropDownUi(model);
			page="DoctorEdit";
		}catch (DoctorNotFoundException e) {
			attribute.addAttribute("message",id+" record not found");
			page="redirect:all";
		}
		return page;
	}
	
	@PostMapping("/update")
	public String updateDoctor(@ModelAttribute Doctor doctor,RedirectAttributes attribute) {
		docService.updateDoctor(doctor);
		attribute.addAttribute("message",doctor.getId()+" record updated");
		return "redirect:all";
	}
	

}
