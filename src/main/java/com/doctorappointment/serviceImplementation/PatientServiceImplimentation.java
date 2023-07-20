package com.doctorappointment.serviceImplementation;

import com.doctorappointment.input.PatientInput;
import com.doctorappointment.exception.ErrorMessageException;
import com.doctorappointment.model.Patient;
import com.doctorappointment.output.PatientOutput;
import com.doctorappointment.repository.PatientRepository;
import com.doctorappointment.service.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientServiceImplimentation implements PatientServices {
    @Autowired
    private PatientRepository patientRepository;


    @Override
    public PatientOutput createPatient(PatientInput patientInput) throws Exception {
        try {
            if (patientInput.getName() != null && patientInput.getFamily() != null) {
                Patient patient = new Patient().fromDto(patientInput);
                patient.setUuid(UUID.randomUUID());

                patient = patientRepository.save(patient);

                return patient.toDto();
            } else
                throw new ErrorMessageException(0, 100, "NameOrFamilyEmpty");

        } catch (Exception exception) {
            throw exception;
        }
    }


}
