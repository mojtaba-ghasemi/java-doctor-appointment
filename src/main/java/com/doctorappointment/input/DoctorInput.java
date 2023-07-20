package com.doctorappointment.input;

import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class DoctorInput {

    private UUID uuid;
    private String name;
    private String family;
    private String mobile;


}
