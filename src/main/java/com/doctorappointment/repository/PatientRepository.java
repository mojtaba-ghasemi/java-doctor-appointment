package com.doctorappointment.repository;


import com.doctorappointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    Patient findOnePatientByMobile(String mobile);
}
