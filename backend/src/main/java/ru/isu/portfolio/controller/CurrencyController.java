package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.Currency;
import ru.isu.portfolio.model.request.EntityNameRequest;
import ru.isu.portfolio.repository.CurrencyRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CurrencyController {

    @Autowired
    private CurrencyRepository mainRepository;

    @GetMapping("/currency/getAll")
    public ResponseEntity<List<Currency>> getAll() {
        List<Currency> currencies = mainRepository.findAll();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/currency/get/{id}")
    public ResponseEntity<Currency> getById(@PathVariable Long id) {
        Currency currency = mainRepository.findById(id).orElse(null);
        if (currency == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(currency);
    }

    @PostMapping(value = "/currency/add")
    public ResponseEntity<?> create(@RequestBody EntityNameRequest request) {
        Currency currency = new Currency(request.getName());

        mainRepository.save(currency);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/currency/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EntityNameRequest request) {
        Currency currency = mainRepository.findById(id).orElse(null);
        if (currency == null) {
            return ResponseEntity.notFound().build();
        }

        currency.setName(request.getName());

        mainRepository.save(currency);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/currency/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}