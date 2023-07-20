package com.doctorappointment.model;
import com.doctorappointment.input.DoctorInput;
import com.doctorappointment.output.DoctorOutput;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
@Table(name = "DOCTOR")
public class Doctor extends Com_User{


    public DoctorOutput toDto() {
        DoctorOutput doctorOutput = new DoctorOutput(getUuid(),getName(),getFamily(),getMobile());
        return doctorOutput;
    }

    public Doctor fromDto(DoctorInput doctorInput) {

        this.setName(doctorInput.getName());
        this.setFamily(doctorInput.getFamily());
        this.setMobile(doctorInput.getMobile());

        return this;
    }

}

