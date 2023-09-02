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
@Table(name="users")
@Entity
public class Users {

    @Id
    @Column(name = "user_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    @Column(name="user_name" , nullable = false)
    private String user_name;

    @Column(name = "user_phone" , nullable = false , unique = true)
    private String user_phone;

    @Column(name = "user_email" , nullable = false , unique = true)
    private String user_email;

    @Column(name = "user_password" , nullable = false)
    private String user_password;

}
