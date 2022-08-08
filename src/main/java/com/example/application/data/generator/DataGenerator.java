package com.example.application.data.generator;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.application.data.entity.SamplePerson;
import com.example.application.data.service.SamplePersonRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;

import lombok.extern.java.Log;

@Log
@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(final SamplePersonRepository samplePersonRepository) {
    	final int DEMO_DATA_CNT = 100;
    	
        return args -> {
            if (samplePersonRepository.count() != 0L) {
                log.info("Using existing database");
                return;
            }
            int seed = 123;

            log.info( String.format("Generating %d demo data", DEMO_DATA_CNT));

            log.info("... generating 100 Sample Person entities...");
            ExampleDataGenerator<SamplePerson> samplePersonRepositoryGenerator = new ExampleDataGenerator<>(
                    SamplePerson.class, LocalDateTime.of(2022, 8, 6, 0, 0, 0));
            samplePersonRepositoryGenerator.setData(SamplePerson::setFirstName, DataType.FIRST_NAME);
            samplePersonRepositoryGenerator.setData(SamplePerson::setLastName, DataType.LAST_NAME);
            samplePersonRepositoryGenerator.setData(SamplePerson::setEmail, DataType.EMAIL);
            samplePersonRepositoryGenerator.setData(SamplePerson::setPhone, DataType.PHONE_NUMBER);
            samplePersonRepositoryGenerator.setData(SamplePerson::setDateOfBirth, DataType.DATE_OF_BIRTH);
            samplePersonRepositoryGenerator.setData(SamplePerson::setOccupation, DataType.OCCUPATION);
            samplePersonRepositoryGenerator.setData(SamplePerson::setImportant, DataType.BOOLEAN_10_90);
            samplePersonRepository.saveAll(samplePersonRepositoryGenerator.create(DEMO_DATA_CNT, seed));

            log.info(String.format("Generated %d demo data", DEMO_DATA_CNT));
        };
    }

}