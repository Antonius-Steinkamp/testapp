package com.example.application.app.meldepunkt;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.example.application.data.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Meldepunkt extends AbstractEntity {

    private String name;
    private int eigenschaften = 0;
    private LocalDateTime dateTimeOfBirth = LocalDateTime.now();

}
