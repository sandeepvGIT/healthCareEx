package com.svi.san.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.san.constants.SlotStatus;
import com.svi.san.entity.SlotRequest;
import com.svi.san.exception.SlotRequestNotFoundException;
import com.svi.san.repo.SlotRequestRepo;
import com.svi.san.service.ISlotRequestService;

@Service("slotReqService")
public class SlotRequestImpl implements ISlotRequestService {
	@Autowired
	private SlotRequestRepo slotReqRepo;

	@Override
	public Long saveRequest(SlotRequest slotReq) {
		return slotReqRepo.save(slotReq).getId();
	}

	@Override
	public List<SlotRequest> getAllSlotRequest() {
		return slotReqRepo.findAll();
	}

	@Override
	public void updateSlotRequestStatus(Long id,String status) {
		slotReqRepo.updateSlotRequestStatus(id, status);
	}

	@Override
	public SlotRequest getOneSlotReqById(Long id) {
		Optional<SlotRequest> opt=slotReqRepo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}//if
		throw new SlotRequestNotFoundException("There is no slot exist with this "+id);
	}

	@Override
	public List<SlotRequest> viewSlotByPatientMail(String userName) {
		return slotReqRepo.getAllPatientByMail(userName);
	}

	@Override
	public List<SlotRequest> viewSlotsByDoctorMail(String doctorMail,String status) {
		return slotReqRepo.getAllDoctorSlots(doctorMail, status);
	}

	@Override
	public List<Object[]> fetchSlotStatusAndCount() {
		return slotReqRepo.getSlotStatusAndCount();
	}

}//SlotRequestImpl
