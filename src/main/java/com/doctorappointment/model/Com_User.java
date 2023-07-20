package com.doctorappointment.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "COM_USER", indexes = {@Index(columnList = "UUID",unique = true), @Index(columnList = "USER_TYPE,MOBILE",unique = true)})
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE",
        discriminatorType = DiscriminatorType.INTEGER)

public class Com_User {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "UUID", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FAMILY")
    private String family;

    @Column(name = "MOBILE")
    private String mobile;


    public String getFullName(){
        return this.name.concat(" ").concat(this.family);
    }
}
