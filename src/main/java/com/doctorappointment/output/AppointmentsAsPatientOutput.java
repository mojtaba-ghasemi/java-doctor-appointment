package com.doctorappointment.output;

import com.doctorappointment.model.Appointment;
import com.doctorappointment.model.Doctor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentsAsPatientOutput {

    public AppointmentsAsPatientOutput(Appointment appointment) {
        this.startDateTime = appointment.getStartDateTime();
        this.finishDateTime = appointment.getFinishDateTime();
        this.doctorFullName = appointment.getDoctor().getFullName();
        this.uUid = appointment.getUuid().toString();
    }

    public AppointmentsAsPatientOutput(UUID uUid, LocalDateTime startDateTime, LocalDateTime finishDateTime
            , Doctor doctor) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.doctorFullName = doctor.getFullName();
        this.uUid = uUid.toString();
    }

    private String uUid;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;
    private String doctorFullName;
}
