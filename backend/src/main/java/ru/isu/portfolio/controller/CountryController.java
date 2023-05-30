package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.Country;
import ru.isu.portfolio.model.request.EntityNameRequest;
import ru.isu.portfolio.repository.CountryRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CountryController {

    @Autowired
    private CountryRepository mainRepository;

    @GetMapping("/country/getAll")
    public ResponseEntity<List<Country>> getAll() {
        List<Country> countries = mainRepository.findAll();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/country/get/{id}")
    public ResponseEntity<Country> getById(@PathVariable Long id) {
        Country country = mainRepository.findById(id).orElse(null);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @PostMapping(value = "/country/add")
    public ResponseEntity<?> create(@RequestBody EntityNameRequest request) {
        Country country = new Country(request.getName());

        mainRepository.save(country);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/country/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EntityNameRequest request) {
        Country country = mainRepository.findById(id).orElse(null);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }

        country.setName(request.getName());

        mainRepository.save(country);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/country/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}