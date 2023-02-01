package com.jjacome.springcloudmsvc.usuarios.models.entity;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cedula")
public class Cedula extends AbstractAuditable {
}