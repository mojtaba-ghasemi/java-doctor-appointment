package com.doctorappointment.output;

import com.doctorappointment.model.Doctor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentsTakenOutput {

    public AppointmentsTakenOutput(){

    }
    public AppointmentsTakenOutput(UUID uUid, LocalDateTime startDateTime, LocalDateTime finishDateTime
            , Doctor doctor, LocalDateTime takenDateTime) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.doctorFullName = doctor.getFullName();
        this.takenDateTime = takenDateTime;
        this.uUid = uUid.toString();
    }

    private String uUid;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;
    private LocalDateTime takenDateTime;
    private String doctorFullName;
}
