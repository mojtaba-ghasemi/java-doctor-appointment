package com.doctorappointment.serviceImplementation;

import com.doctorappointment.exception.NoSuchElementFoundException;
import com.doctorappointment.helper.DateTimeHelper;
import com.doctorappointment.input.AppointmentInput;
import com.doctorappointment.input.AppointmentTakeInput;
import com.doctorappointment.exception.ErrorMessageException;
import com.doctorappointment.model.Appointment;
import com.doctorappointment.model.Doctor;
import com.doctorappointment.model.Patient;
import com.doctorappointment.output.AppointmentsAsDoctorOutput;
import com.doctorappointment.output.AppointmentsAsPatientOutput;
import com.doctorappointment.output.AppointmentsTakenOutput;
import com.doctorappointment.repository.AppointmentRepository;
import com.doctorappointment.repository.DoctorRepository;
import com.doctorappointment.repository.PatientRepository;
import com.doctorappointment.service.AppointmentServices;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
@Service
public class AppointmentServiceImplementation implements AppointmentServices {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;


    //region use by Doctor
    @Override
    public ArrayList<Pair<LocalDateTime, LocalDateTime>> createAppointment(AppointmentInput appointmentInput) throws Exception {

        ArrayList<Pair<LocalDateTime, LocalDateTime>> timeSlots;

        try {
            Doctor doctor = createAppointmentCheckCondition(appointmentInput);

            timeSlots = DateTimeHelper.splitDateTimeAsMinute(appointmentInput.getStartDateTime(), appointmentInput.getFinishDateTime(), 30);
            for (Pair<LocalDateTime, LocalDateTime> timeSlot : timeSlots) {
                Appointment appointment = new Appointment(timeSlot.getFirst(), timeSlot.getSecond());
                appointment.setRegisterDateTime(java.time.LocalDateTime.now());
                appointment.setUuid(UUID.randomUUID());
                appointment.setDoctor(doctor);
                appointmentRepository.save(appointment);
            }
            return timeSlots;

        } catch (Exception exception) {
            throw exception;
        }
    }

    private Doctor createAppointmentCheckCondition(AppointmentInput appointmentInput) throws ErrorMessageException {
        int date_diff = appointmentInput.getStartDateTime().toLocalDate().compareTo(appointmentInput.getFinishDateTime().toLocalDate());
        if (date_diff == 0) {
            long minute_diff = appointmentInput.getStartDateTime().until(appointmentInput.getFinishDateTime(), ChronoUnit.MINUTES);
            if (minute_diff > 0) {

                Collection<Doctor> doctors = doctorRepository.findDoctorByUuid(appointmentInput.getDoctor_uuid());
                if (doctors.size() != 1) throw new ErrorMessageException(0, 102, "DoctorNotFound");
                Doctor doctor = (Doctor) doctors.toArray()[0];
                int appointmentCount = appointmentRepository.getAppointmentCountByDate(doctor, appointmentInput.getStartDateTime().toLocalDate());
                if (appointmentCount > 0)
                    throw new ErrorMessageException(0, 103, "DoctorBeforeScheduledTimeForThisDay");

                return (Doctor) doctors.toArray()[0];

            } else throw new ErrorMessageException(0, 101, "StartTimeIsNotBeforeEndTime");
        } else throw new ErrorMessageException(0, 100, "StartAndEndDateNotEqual");

    }

    @Override
    public Collection<AppointmentsAsDoctorOutput> findAppointmentsAsDoctor(String doctorUuid, LocalDate date, LocalDate finishDate) throws Exception {
        try {
            Collection<AppointmentsAsDoctorOutput> appointmentsAsDoctorOutputs = null;

            if (doctorRepository.findDoctorByUuid(UUID.fromString(doctorUuid)).size() == 0) throw new NoSuchElementFoundException("DoctorNotFound");

            if (doctorUuid != null && date == null)
                appointmentsAsDoctorOutputs = appointmentRepository.findAppointmentAsDoctorByDoctor(doctorUuid);
            else if (doctorUuid != null && date != null && finishDate != null)
                appointmentsAsDoctorOutputs = appointmentRepository.findAppointmentAsDoctorByDoctorAndDate(doctorUuid, date, finishDate);

            return appointmentsAsDoctorOutputs;
        } catch (Exception exception) {
            throw exception;
        }
    }


    @Override
    @Transactional
    public boolean deleteAppointmentByUuid(String appointmentUuid) throws Exception {

        System.out.println("AppointmentServiceImplementation/deleteAppointmentByUuid Started");
        try {
            Appointment appointment = findAppointmentModelByUuid(UUID.fromString(appointmentUuid));
            if (appointment.getTakenDateTime() != null)
                throw new ErrorMessageException(0, 106, "AppointmentBeforeHasBeenTaken");
            if (appointment.isDeleted()) throw new ErrorMessageException(0, 107, "AppointmentBeforeHasBeenDeleted");

            appointment.setDeleted(true);
            appointmentRepository.save(appointment);

            System.out.println("AppointmentServiceImplementation/deleteAppointmentByUuid Successfully Finished");
            return true;
        } catch (Exception exception) {
            System.out.println("AppointmentServiceImplementation/deleteAppointmentByUuid Exception:");
            System.out.println(exception.getMessage());
            throw exception;
        }
    }

    private Appointment findAppointmentModelByUuid(UUID uuid) throws Exception {

        try {
            Collection<Appointment> appointmentCollation = appointmentRepository.findAppointmentByUuid(uuid);
            if (appointmentCollation.size() == 1) return appointmentCollation.iterator().next();
            else if (appointmentCollation.size() == 0)
                throw new NoSuchElementFoundException("AppointmentUUIDNotExist");
            else throw new Exception("system exception occurred");

        } catch (Exception exception) {
            throw exception;
        }
    }
    //endregion

    //region use by Patient
    @Override
    public Collection<AppointmentsAsPatientOutput> findAppointmentsAsPatient(LocalDate date, String doctorUuid) {
        try {

            if (doctorRepository.findDoctorByUuid(UUID.fromString(doctorUuid)).size() == 0) throw new NoSuchElementFoundException("DoctorDidNotFound");

            Collection<AppointmentsAsPatientOutput> appointmentsAsPatientCollection;

            if (doctorUuid == null) appointmentsAsPatientCollection = appointmentRepository.findAppointmentByDate(date);
            else
                appointmentsAsPatientCollection = appointmentRepository.findAppointmentByDateAndDoctor(date, doctorUuid);

            return appointmentsAsPatientCollection;
        } catch (Exception exception) {
            throw exception;
        }
    }

    @Override
    @Transactional
    public AppointmentsAsPatientOutput takeAppointmentsAsPatient(AppointmentTakeInput appointmentTakeInput) throws Exception {

        System.out.println("AppointmentServiceImplementation/takeAppointmentsAsPatient Started");
        try {
            Patient patient = takeAppointmentCheckCondition(appointmentTakeInput);

            Collection<Appointment> appointmentCollection = appointmentRepository.findAppointmentByUuid(UUID.fromString(appointmentTakeInput.getAppointmentUuid()));
            if (appointmentCollection.size() == 0) throw new ErrorMessageException(0, 110, "AppointmentUUIDNotExist");
            else {
                Appointment appointment = appointmentCollection.iterator().next();
                if (appointment.getTakenDateTime() != null)
                    throw new ErrorMessageException(0, 106, "AppointmentBeforeHasBeenTaken");
                if (appointment.isDeleted()) throw new ErrorMessageException(0, 107, "AppointmentBeforeHasBeenDeleted");

                // Appointment is free and ready to take
                appointment.setTakenDateTime(java.time.LocalDateTime.now());
                appointment.setTakenPatient(patient);
                appointmentRepository.save(appointment);

                System.out.println("AppointmentServiceImplementation/takeAppointmentsAsPatient Successfully Finished");
                return new AppointmentsAsPatientOutput(appointment);
            }

        } catch (Exception exception) {
            System.out.println("AppointmentServiceImplementation/takeAppointmentsAsPatient Exception:");
            System.out.println(exception.getMessage());
            throw exception;
        }
    }

    private Patient takeAppointmentCheckCondition(AppointmentTakeInput appointmentTakeInput) throws ErrorMessageException {

        try {
            if (StringUtils.isEmpty(appointmentTakeInput.getMobile()) || StringUtils.isEmpty(appointmentTakeInput.getName()) || StringUtils.isEmpty(appointmentTakeInput.getFamily()))
                throw new ErrorMessageException(0, 104, "NameFamilyOrMobileIsEmpty");
            else {
                Patient patient = patientRepository.findOnePatientByMobile(appointmentTakeInput.getMobile());
                if (patient != null) return patient;
                else { // create new Patient
                    patient = new Patient();
                    patient.setName(appointmentTakeInput.getName());
                    patient.setFamily(appointmentTakeInput.getFamily());
                    patient.setMobile(appointmentTakeInput.getMobile());
                    patient.setUuid(UUID.randomUUID());
                    patientRepository.save(patient);
                    return patient;
                }
            }
        } catch (Exception exception) {
            throw exception;
        }
    }


    @Override
    public Collection<AppointmentsTakenOutput> findTakenAppointments(String patientMobile) throws ErrorMessageException {
        try {

            Collection<AppointmentsTakenOutput> appointmentsTakenOutputsp;

            Patient patient = patientRepository.findOnePatientByMobile(patientMobile);
            if (patient == null) throw new ErrorMessageException(0, 108, "PatientMobileDoesNotExist");

            appointmentsTakenOutputsp = appointmentRepository.findTakenAppointments(patient);

            return appointmentsTakenOutputsp;
        } catch (Exception exception) {
            throw exception;
        }
    }
    //endregion

}
