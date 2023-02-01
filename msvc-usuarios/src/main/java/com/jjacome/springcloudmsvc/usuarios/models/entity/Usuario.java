package com.jjacome.springcloudmsvc.usuarios.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank
    @Column(name = "nombre")
    private String nombre ;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email ;

    @NotBlank
    private String password ;


}
