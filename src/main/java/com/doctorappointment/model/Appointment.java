package com.doctorappointment.model;

import com.doctorappointment.input.AppointmentInput;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "APPOINTMENT", indexes = {@Index(columnList = "UUID")})
@Setter
@Getter
public class Appointment {

    public Appointment(){

    }
    public Appointment(LocalDateTime startDateTime, LocalDateTime finishDateTime){
        this.setStartDateTime(startDateTime);
        this.setFinishDateTime(finishDateTime);
    }
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Version
    private Long version;

    @Column(name = "UUID", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(name = "START_DATE_TIME")
    private LocalDateTime startDateTime;

    @Column(name = "FINISH_DATE_TIME")
    private LocalDateTime finishDateTime;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;


    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", nullable = true)
    private Patient takenPatient;

    @Column(name = "REGISTER_DATE_TIME")
    private LocalDateTime registerDateTime;

    @Column(name = "DELETED")
    private boolean deleted = false;

    @Column(name = "DELETED_DATE_TIME")
    private LocalDateTime deletedDateTime;

    @Column(name = "TAKEN_DATE_TIME")
    private LocalDateTime takenDateTime;


    public Appointment fromDto(AppointmentInput appointmentInput) {

        this.setStartDateTime(appointmentInput.getStartDateTime());
        this.setFinishDateTime(appointmentInput.getFinishDateTime());

        return this;
    }
}
