package com.doctorappointment.controller;

import com.doctorappointment.input.AppointmentInput;
import com.doctorappointment.input.AppointmentTakeInput;
import com.doctorappointment.exception.ErrorMessageException;
import com.doctorappointment.service.AppointmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
@RestController
@Controller
@RequestMapping("Appointment/api")
public class AppointmentController {

    @Autowired
    AppointmentServices appointmentServices;

    //region use by Doctor

    /**
     * Story 1: Doctor adds open times.
     * As a doctor I would like to add a start and end time for each day, so that this time is broken down into
     * 30 minutes periods. If one of the periods is becomes less than 30 minutes during breakdown, then it
     * should be ignored.
     * Test cases:
     * 1. If doctor enters an end date that is sooner than start date, appropriate error should be shown
     * 2. If doctor enters start and end date so that the period is less than 30 minutes then no time
     * should be added.
     */
    @PostMapping(value = "createAppointment")
    public ResponseEntity createAppointment(@RequestBody AppointmentInput appointmentInput) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.appointmentServices.createAppointment(appointmentInput));
    }


    /**
     * Story 2: Doctor can view 30 minutes appointments
     * As a doctor I would like to see my open (not taken by patients) and taken 30 minutes appointment.
     * Test cases:
     * 1. If there is no appointment set, empty list should be shown.
     * 2. If there are some taken appointment, then phone number and name of the patient should also
     * be shown.
     *
     * @param doctorUuid The unique key for any doctor
     * @param date       Start of limitation date
     * @param finishDate End of limitation date
     */
    @GetMapping(value = {"findAppointmentsAsDoctor/{doctorUuid}", "findAppointmentsAsDoctor/{doctorUuid}/{date}/{finishDate}"})
    public ResponseEntity findAppointmentsAsDoctor(@PathVariable String doctorUuid, @PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finishDate) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(appointmentServices.findAppointmentsAsDoctor(doctorUuid, date, finishDate));
    }

    /**
     * Story 3: Doctor can delete open appointment
     * As a doctor I would like to be able to delete some of my open appointments.
     * Test cases:
     * 1. If there is no open appointment then 404 error is shown.
     * 2. If the appointment is taken by a patient, then a 406 error is shown
     * 3. Concurrency check; if doctor is deleting the same appointment that a patient is taking at the
     * same time.
     *
     * @param appointmentUuid The unique key for any appointment
     */
    @DeleteMapping(value = "deleteAppointmentByUuid/{appointmentUuid}")
    public ResponseEntity deleteAppointmentByUuid(@PathVariable String appointmentUuid) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(this.appointmentServices.deleteAppointmentByUuid(appointmentUuid));
    }

    //endregion

    //region use by Patient

    /**
     * Story 4: Patients can view a doctor open appointment
     * As a patient I like to be able to see all of the open appointments for the given day. So, I can take one of
     * these appointments.
     * Test cases:
     * 1. If the doctor doesn’t have any open appointment that day, then, an empty list should be shown.
     *
     * @param date       special date for find
     * @param doctorUuid special doctor unique key for find
     */

    @GetMapping(value = {"findAppointmentsAsPatient/{date}", "findAppointmentsAsPatient/{date}/{doctorUuid}"})
    public ResponseEntity findAppointmentsAsPatient(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @PathVariable(required = false) String doctorUuid) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(appointmentServices.findAppointmentsAsPatient(date, doctorUuid));
    }

    /**
     * Story 5: Patients can take an open appointment
     * As a patient I like to be able to take an open appointment by giving my name and phone number
     * <p>
     * Test cases:
     * 1. If either phone number or name is not given, then an appropriate error message should be given.
     * 2. If the appointment is already taken or deleted, then an appropriate error message should be given.
     * 3. Concurrency check; patient is taking an appointment that is in the process of deletion or being
     * taken by another patient.
     */
    @PutMapping(value = "takeAppointment")
    public ResponseEntity takeAppointment(@RequestBody AppointmentTakeInput appointmentTakeInput) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(this.appointmentServices.takeAppointmentsAsPatient(appointmentTakeInput));
    }

    /**
     * Story: Patients can view their own appointments
     * As a patient I like to be able to view my own appointments, providing only my phone number.
     * Test cases:
     * 1. If there is no appointment with this phone number, then an empty list should be shown.
     * 2. If there are more than one appointment taken by this user, then all should be shown
     *
     * @param patientMobile the unique key for Patient
     */
    @GetMapping(value = "findTakenAppointments/{patientMobile}")
    public ResponseEntity findTakenAppointments(@PathVariable String patientMobile) throws ErrorMessageException {

        return ResponseEntity.status(HttpStatus.OK).body(appointmentServices.findTakenAppointments(patientMobile));
    }

    //endregion

}
