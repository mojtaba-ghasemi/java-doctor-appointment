package com.doctorappointment.repository;

import com.doctorappointment.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    Collection<Doctor> findDoctorByUuid(UUID uuid);


}
