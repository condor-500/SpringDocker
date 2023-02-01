package com.jjacome.springcloudmsvc.usuarios.models.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "claves")
public class Clave extends AbstractPersistable {
}