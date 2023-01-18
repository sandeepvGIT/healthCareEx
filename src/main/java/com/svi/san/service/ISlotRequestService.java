package com.svi.san.service;

import java.util.List;

import com.svi.san.entity.SlotRequest;

public interface ISlotRequestService {
	//Patient can book slot
	Long saveRequest(SlotRequest request);
	//Admin can view all slots
	List<SlotRequest>  getAllSlotRequest();
	//Admin patient can update status
	void updateSlotRequestStatus(Long id,String status);
	SlotRequest getOneSlotReqById(Long id);
	//patient can view his slots
	List<SlotRequest>  viewSlotByPatientMail(String userName);
	//DOCTOR can see his slots
	List<SlotRequest> viewSlotsByDoctorMail(String doctorMail,String status);
	//Admin dashBoard
	List<Object[]> fetchSlotStatusAndCount();
	
	
	

}
