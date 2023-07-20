package com.doctorappointment.input;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentInput {

    private UUID uuid;
    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;
    private UUID doctor_uuid;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(LocalDateTime finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public UUID getDoctor_uuid() {
        return doctor_uuid;
    }

    public void setDoctor_uuid(UUID doctor_uuid) {
        this.doctor_uuid = doctor_uuid;
    }
}
