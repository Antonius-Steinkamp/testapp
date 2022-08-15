package com.example.application.views.fismessage;

import java.time.LocalDateTime;

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
	private String fisMessagePoint;
    private String product;

    private String status = "REC";
    
    private LocalDateTime dateOfBirth = LocalDateTime.now();
}
