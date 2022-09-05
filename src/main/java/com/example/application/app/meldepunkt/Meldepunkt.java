package com.example.application.app.meldepunkt;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.example.application.data.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Meldepunkt extends AbstractEntity {

	@NotEmpty
	@Size(min=2, max=200)
    private String name;
	
	@PositiveOrZero
    private int eigenschaften = 0;
	
    private LocalDateTime dateTimeOfBirth = LocalDateTime.now();

}
