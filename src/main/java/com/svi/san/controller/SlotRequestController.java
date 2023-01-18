package com.svi.san.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.svi.san.constants.SlotStatus;
import com.svi.san.entity.Appointment;
import com.svi.san.entity.Patient;
import com.svi.san.entity.SlotRequest;
import com.svi.san.entity.User;
import com.svi.san.service.IAppointmentService;
import com.svi.san.service.IDoctorService;
import com.svi.san.service.IPatientService;
import com.svi.san.service.ISlotRequestService;
import com.svi.san.service.ISpecializationService;
import com.svi.san.util.AdminDashBoardUtil;
import com.svi.san.view.InvioceSlipPdfView;

@Controller
@RequestMapping("/slot")
public class SlotRequestController {
	@Autowired
	private ISlotRequestService slotReqService;
	@Autowired
	private IPatientService patService;
	@Autowired
	private IAppointmentService appService;
	@Autowired
	private IDoctorService docService;
	@Autowired
	private ISpecializationService specService;
	@Autowired
	private ServletContext context;
	@Autowired
	private AdminDashBoardUtil util;
	
	@GetMapping("/book")
	public String bookSlot(@RequestParam Long appid,HttpSession session,Model model) {
		User user=(User)session.getAttribute("userObj");
		//get current userName
		String email=user.getUserName();
		//get current appointment
		Appointment appointment=appService.getOneAppointment(appid);
		//get current patient by email
		Patient patient=patService.getOnePatient(email);
		SlotRequest slotReq=new SlotRequest();
		slotReq.setAppointment(appointment);
		slotReq.setPatient(patient);
		slotReq.setStatus("PENDING");
		try {
			//save the slotRequest
			slotReqService.saveRequest(slotReq);
			String message="Patient"+patient.getFirstName()+" "+patient.getLastName()
			                                +", Request for Dr. "+appointment.getDoctor().getFirstName()+" "+appointment.getDoctor().getLastName()
			                                +", on date : "+appointment.getDate()+" and  status is "+slotReq.getStatus();
			//send data to UI
			model.addAttribute("message", message);
		}catch (Exception e) {
		    e.printStackTrace();
		    model.addAttribute("message", "BOOKING REQUEST ALREADY MADE FOR THIS APPOINTMENT/DATE");
		}
		return "SlotRequestMessage";
	}
	
	@GetMapping("/all")
	public String showSlotReqData(Model model) {
		List<SlotRequest> slotReqData=slotReqService.getAllSlotRequest();
		model.addAttribute("slotReqData", slotReqData);
		return "SlotReqData";
	}
	
	@GetMapping("/accept")
	public String acceptSlotRequest(@RequestParam Long id) {
		slotReqService.updateSlotRequestStatus(id, SlotStatus.ACCEPTED.name());
		SlotRequest slotReq=slotReqService.getOneSlotReqById(id);
		if(slotReq.getStatus().equalsIgnoreCase("ACCEPTED")) {
			appService.updateSlotCountForAppointment(slotReq.getAppointment().getId(), -1);
		}
		return "redirect:all";
	}

	@GetMapping("/reject")
	public String rejectSlotRequest(@RequestParam Long id) {
		slotReqService.updateSlotRequestStatus(id, SlotStatus.REJECTED.name());
		return "redirect:all";
	}
	
	@GetMapping("/cancel")
	public String cancelSlotRequest(@RequestParam Long id) {
		slotReqService.updateSlotRequestStatus(id, SlotStatus.CANCELLED.name());
		SlotRequest slotReq=slotReqService.getOneSlotReqById(id);
		if(slotReq.getStatus().equalsIgnoreCase(SlotStatus.ACCEPTED.name())) {
			appService.updateSlotCountForAppointment(slotReq.getAppointment().getId(), -1);
		}
		return "redirect:all";
	}
	
	@GetMapping("/patient")
	public String viewMySlotReqPatient(Model model,Principal principal) {
		String email=principal.getName();
		List<SlotRequest> list=slotReqService.viewSlotByPatientMail(email);
		model.addAttribute("list", list);
		return "SoltReqDataPatient";
	}
	
	@GetMapping("/doctor")
	public String viewDoctorSlotReqData(Model model,Principal principal) {
		String email=principal.getName();
		List<SlotRequest> list=slotReqService.viewSlotsByDoctorMail(email, SlotStatus.ACCEPTED.name());
		model.addAttribute("list", list);
 		return "SlotReqDataDoctor";
	}
	
	@GetMapping("/dashboard")
	public String adminDashBord(Model model) {
		model.addAttribute("doctors", docService.getDoctorCount());
		model.addAttribute("patients", patService.getPatientCount());
		model.addAttribute("appointments", appService.getAppointmentCount());
		model.addAttribute("specialization", specService.getSpecializationCount());
		//root path
		String path=context.getRealPath("/");
		System.out.println("root path  :   -------  "+path);
		List<Object[]> list=slotReqService.fetchSlotStatusAndCount();
		util.generateBar(path, list);
		util.generatePie(path, list);
		return "AdminDashBoard";
	}
	//get pay slip of appointment booking
	@GetMapping("/invoice")
	public ModelAndView generateInvoice(@RequestParam Long id) {
		ModelAndView m=new ModelAndView( );
		m.setView(new InvioceSlipPdfView());
		SlotRequest slotReq=slotReqService.getOneSlotReqById(id);
		m.addObject("slotRequest", slotReq);
		return m;
	}
	
}
