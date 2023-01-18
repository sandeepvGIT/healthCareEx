package com.svi.san.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.svi.san.entity.Appointment;
import com.svi.san.entity.Doctor;
import com.svi.san.service.IAppointmentService;
import com.svi.san.service.IDoctorService;
import com.svi.san.service.ISpecializationService;

@Controller
@RequestMapping("/appointment")
public class AppointmentContoller {
	@Autowired
	private IAppointmentService appService;
	@Autowired
	private IDoctorService docService;
	@Autowired
	private ISpecializationService specService;
	
	private void getDynamicDropDownUi(Model model) {
		Map<Long,String> map=docService.getDoctorIdAndNames();
		System.out.println(map);
		model.addAttribute("doctors", map);
	}
	
	@GetMapping("/register")
	public String showRegister(@RequestParam(value="message",required = false)String message,Model model) {
		model.addAttribute("message", message);
		getDynamicDropDownUi(model);
		return "AppointmentRegister";
	}
	
	@PostMapping("/save")
	public String saveAppointment(@ModelAttribute Appointment appointment,RedirectAttributes attribute) {
		Long id=appService.saveAppointment(appointment);
		attribute.addAttribute("message", id+"record saved");
		return"redirect:register";
	}
	@GetMapping("/all")
	public String getAllApointments(Model model) {
		List<Appointment> appList=appService.getAllAppointments();
		model.addAttribute("appList", appList);
		return "AppointmentData";
	}
	
	@GetMapping("/delete")
	public String removeAppointment(@RequestParam Long id,RedirectAttributes attribute) {
		try {
			appService.remove(id);
			attribute.addAttribute("message", id+" record deleted");
		}catch (Exception e) {
			attribute.addAttribute("message", e.getMessage());
		}
		return"redirect:all";
	}
	@GetMapping("/edit")
	public String editAppointment(@RequestParam Long id,RedirectAttributes attribute,Model model) {
		String page=null;
		try {
			Appointment appointment=appService.getOneAppointment(id);
			model.addAttribute("appointment", appointment);
			//get dynamic drop down
			getDynamicDropDownUi(model);
			page="AppointmentEdit";
		}catch (Exception e) {
			attribute.addAttribute("message", e.getMessage());
			page="redirect:all";
		}
		return page;
		
	}
	@PostMapping("/update")
	public String updateAppointment(@ModelAttribute Appointment appointment,RedirectAttributes attribute) {
		try {
			appService.updateAppointment(appointment);
			attribute.addAttribute("message", appointment.getId()+" record updated");
		}catch (Exception e) {
			attribute.addAttribute("message", e.getMessage());
		}
		
		return"redirect:all";
	}
	
	//view appointment page.
	@GetMapping("/view")
	public String viewSlots(
			                                    @RequestParam(required = false,defaultValue = "0") Long specid,
			                                     Model model) 
	{
		//fetch data for spec DropDown
		Map<Long,String> specMap=specService.getSpecIdAndName();
		model.addAttribute("specializations",specMap);
		List<Doctor> docList=null;
		String message=null;
		if(specid<=0) {
			//if they did not select any spec 
			docList=docService.getAllDoctor();
			message="Result : "+"All Doctors";
		}
		else {
			docList= docService.findDoctorBySpecId(specid);
			message="Result : "+specService.getOneSpecailization(specid).getSpecName();
	}
		model.addAttribute("docList", docList);
		model.addAttribute("message", message);
		return "AppointmentSearch";
}
//view slots
@GetMapping("/viewSlot")	
public String showSlots(@RequestParam Long id, Model model) {
	List<Object[]> list = appService.getAppointmentByDoctor(id);	
	model.addAttribute("list", list);
	Doctor doc=docService.getOneDoctor(id);
	model.addAttribute("message", " Results showing for  "+doc.getFirstName()+"  "+doc.getLastName());
	return "AppointmentSlots";
	}

@GetMapping("/docAppointment")
public String showDocAppointment(Principal prn,Model model) {
	String email=prn.getName();
	List<Object[]>appList=appService.getAppoinmentsByDoctorEmail(email);
	model.addAttribute("docAppList", appList);
	return "DoctorAppointment";
}
	
}
