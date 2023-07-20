package com.doctorappointment.serviceImplementation;

import com.doctorappointment.input.DoctorInput;
import com.doctorappointment.exception.ErrorMessageException;
import com.doctorappointment.model.Doctor;
import com.doctorappointment.output.DoctorOutput;
import com.doctorappointment.repository.DoctorRepository;
import com.doctorappointment.service.DoctorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
@Service
public class DoctorServiceImplimentation implements DoctorServices {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorOutput createDoctor(DoctorInput doctorInput) throws Exception {

        try {
            if (doctorInput.getName() != null && doctorInput.getFamily() != null) {
                Doctor doctor = new Doctor().fromDto(doctorInput);
                doctor.setUuid(UUID.randomUUID());

                doctor = doctorRepository.save(doctor);

                return doctor.toDto();
            } else
                throw new ErrorMessageException(0, 100, "NameOrFamilyEmpty");

        } catch (Exception exception) {
            throw exception;
        }
    }
}
