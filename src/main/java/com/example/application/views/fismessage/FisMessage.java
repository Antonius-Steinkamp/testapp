package com.example.application.views.fismessage;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FisMessage {
	@Length(min = 1)
	private String fisMessagePoint;
	@Length(min = 1)
    private String product;
    private LocalDateTime dateOfBirth = LocalDateTime.now();

}
