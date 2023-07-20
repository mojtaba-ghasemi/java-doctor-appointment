package com.doctorappointment.service;
import com.doctorappointment.input.DoctorInput;
import com.doctorappointment.output.DoctorOutput;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
public interface DoctorServices {

    DoctorOutput createDoctor(DoctorInput doctorInput) throws Exception;
}
