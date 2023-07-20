package com.doctorappointment.model;

import com.doctorappointment.input.PatientInput;
import com.doctorappointment.output.PatientOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("2")
@Table(name = "PATIENT")
public class Patient extends Com_User{

    public PatientOutput toDto() {
        PatientOutput patientOutput = new PatientOutput(getUuid(),getName(),getFamily(),getMobile());
        return patientOutput;
    }

    public Patient fromDto(PatientInput patientInput) {

        this.setName(patientInput.getName());
        this.setFamily(patientInput.getFamily());
        this.setMobile(patientInput.getMobile());

        return this;
    }
}
