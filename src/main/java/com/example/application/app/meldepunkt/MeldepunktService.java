package com.example.application.app.meldepunkt;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MeldepunktService {

    private final MeldepunktRepository repository;

    @Autowired
    public MeldepunktService(MeldepunktRepository repository) {
        this.repository = repository;
    }

    public Optional<Meldepunkt> get(UUID id) {
        return repository.findById(id);
    }

    public Meldepunkt update(Meldepunkt entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Meldepunkt> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
