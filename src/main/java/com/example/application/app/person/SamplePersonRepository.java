package com.example.application.app.person;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}