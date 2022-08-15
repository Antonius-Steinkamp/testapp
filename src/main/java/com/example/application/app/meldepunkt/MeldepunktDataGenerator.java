package com.example.application.app.meldepunkt;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;

import lombok.extern.java.Log;

@Log
@SpringComponent
public class MeldepunktDataGenerator {

	@Bean
    public CommandLineRunner loadMeldepunktData(final MeldepunktRepository meldepunktRepository) {
    	final int DEMO_DATA_CNT = 10;
    	
        return args -> {
            if (meldepunktRepository.count() != 0L) {
                log.info("Using existing database");
                return;
            }
            int seed = 123;

            log.info( String.format("Generating %d demo data", DEMO_DATA_CNT));

            log.info("... generating 100 Sample Person entities...");
            ExampleDataGenerator<Meldepunkt> samplePersonRepositoryGenerator 
            	= new ExampleDataGenerator<>(Meldepunkt.class, LocalDateTime.of(2022, 8, 6, 0, 0, 0));
            samplePersonRepositoryGenerator.setData(Meldepunkt::setName, DataType.FIRST_NAME);
            meldepunktRepository.saveAll(samplePersonRepositoryGenerator.create(DEMO_DATA_CNT, seed));

            log.info(String.format("Generated %d demo data", DEMO_DATA_CNT));
        };
    }

}