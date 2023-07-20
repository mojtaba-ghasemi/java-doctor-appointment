package com.doctorappointment.controller;

import com.doctorappointment.input.PatientInput;
import com.doctorappointment.service.PatientServices;
import com.doctorappointment.exception.ErrorMessageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mojtaba Ghasemi
 * @version 1.0
 * @since 2024/07/12
 */
@RestController
@Controller
@RequestMapping("Patient/api")
public class PatientController {

    @Autowired
    PatientServices patientServices;

    @PostMapping(value = "createPatient")
    public ResponseEntity createPatient(@RequestBody PatientInput patientInput) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.patientServices.createPatient(patientInput));
        } catch (Exception exception) {
            if (exception instanceof ErrorMessageException)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body(((ErrorMessageException) exception).getErrorMessage());
            else {
                exception.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("system exception occurred");
            }

        }
    }

}
