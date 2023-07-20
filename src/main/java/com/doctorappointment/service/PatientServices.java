package com.doctorappointment.service;


import com.doctorappointment.input.PatientInput;
import com.doctorappointment.output.PatientOutput;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
public interface PatientServices {
    PatientOutput createPatient(PatientInput patientInput) throws Exception;
}
