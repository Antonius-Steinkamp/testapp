package com.example.application.app.meldepunkt;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource
public interface MeldepunktRepository extends JpaRepository<Meldepunkt, UUID> {

}