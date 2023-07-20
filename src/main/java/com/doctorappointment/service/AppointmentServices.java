package com.doctorappointment.service;

import com.doctorappointment.input.AppointmentInput;
import com.doctorappointment.input.AppointmentTakeInput;
import com.doctorappointment.exception.ErrorMessageException;
import com.doctorappointment.output.AppointmentsAsDoctorOutput;
import com.doctorappointment.output.AppointmentsAsPatientOutput;
import com.doctorappointment.output.AppointmentsTakenOutput;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
public interface AppointmentServices {

    //region use by Patient
    ArrayList<Pair<LocalDateTime, LocalDateTime>> createAppointment(AppointmentInput appointmentInput) throws Exception;

    Collection<AppointmentsAsDoctorOutput> findAppointmentsAsDoctor (String doctorUuid, LocalDate date, LocalDate finishDate) throws Exception;

    public boolean deleteAppointmentByUuid(String appointmentUuid) throws Exception;

    //endregion



    //region use by Patient
    Collection<AppointmentsAsPatientOutput> findAppointmentsAsPatient (LocalDate date, String doctorUuid) throws Exception;
    AppointmentsAsPatientOutput takeAppointmentsAsPatient (AppointmentTakeInput appointmentTakeInput) throws Exception;

    Collection<AppointmentsTakenOutput> findTakenAppointments (String patientMobile) throws ErrorMessageException;

    //endregion
}
