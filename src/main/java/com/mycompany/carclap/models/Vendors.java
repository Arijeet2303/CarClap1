package com.mycompany.carclap.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "vendors")
public class Vendors {

    @Id
    @Column(name = "vendor_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vendor_id;

    @Column(name = "vendor_name",nullable = false)
    private String vendor_name;

    @Column(name = "vendor_city",nullable = false)
    private String vendor_city;

    @Column(name = "vendor_state",nullable = false)
    private String vendor_state;

    @Column(name = "vendor_country",nullable = false)
    private String vendor_country;

    @Column(name = "vendor_address",nullable = false)
    private String vendor_address;

    @Column(name = "vendor_pin",nullable = false)
    private String vendor_pin;

    @Column(name = "vendor_govt",nullable = false)
    private String vendor_govt;

    @Column(name = "vendor_email",nullable = false , unique = true)
    private String vendor_email;

    @Column(name = "vendor_phone",nullable = false , unique = true)
    private String vendor_phone;

    @Column(name = "vendor_pic",nullable = false)
    private String vendor_pic;

    @Column(name = "vendor_password",nullable = false)
    private String vendor_password;
}
