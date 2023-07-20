package com.doctorappointment.output;

import lombok.Data;
import java.util.UUID;

@Data
public class DoctorOutput {

    public DoctorOutput() {
    }

    public DoctorOutput(UUID uuid, String name , String family , String mobile) {
        this.uuid = uuid;
        this.name = name;
        this.family = family;
        this.mobile = mobile;
    }



    private UUID uuid;
    private String name;
    private String family;
    private String mobile;

}

