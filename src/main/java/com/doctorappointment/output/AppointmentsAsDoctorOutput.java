package com.doctorappointment.output;

import com.doctorappointment.model.Doctor;
import com.doctorappointment.model.Patient;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentsAsDoctorOutput {


    public AppointmentsAsDoctorOutput(UUID uuid, LocalDateTime startDateTime, LocalDateTime finishDateTime
            , LocalDateTime registerDateTime, LocalDateTime takenDateTime
            , Doctor doctor, Patient patient, boolean deleted) {
        this.uuid = uuid.toString();
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.registerDateTime = registerDateTime;
        this.doctorFullName = doctor.getFullName();
        this.deleted = deleted;

        if(patient != null){
            this.patientFullName = patient.getFullName();
            this.patientMobile = patient.getMobile();
            this.takenDateTime = takenDateTime;
        }

    }

    private String uuid;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

    private LocalDateTime registerDateTime;
    private LocalDateTime takenDateTime;

    private String doctorFullName;
    private String patientFullName;
    private String patientMobile;
    private boolean deleted;
}
