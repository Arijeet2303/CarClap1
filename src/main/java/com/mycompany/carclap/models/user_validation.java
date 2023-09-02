package com.mycompany.carclap.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_validation")
public class user_validation {

    @Id
    @Column(name = "user_email")
    private String email;

    @Column(name = "otp")
    private int otp;
}
