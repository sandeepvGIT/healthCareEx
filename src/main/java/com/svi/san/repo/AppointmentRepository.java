package com.svi.san.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.svi.san.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query("SELECT aptm.date,aptm.noOfSlots,aptm.amt,aptm.id FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.id=:docId AND aptm.noOfSlots>0 AND aptm.date>=current_date")
	public List<Object[]> getAppointmentByDoctor(Long docId);

	
	@Query("SELECT aptm.date, aptm.noOfSlots, aptm.amt, aptm.detail FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.email=:userName AND aptm.noOfSlots>0")
	public List<Object[]> getAppoinmentsByDoctorEmail(String userName);
	
	@Modifying
	@Query("UPDATE Appointment SET noOfSlots = noOfSlots + :count WHERE id=:id")
	public void updateSlotCountForAppointment(Long id,int count);
	
}//AppointmentRepository
