package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.Exchange;
import ru.isu.portfolio.model.request.EntityNameRequest;
import ru.isu.portfolio.repository.ExchangeRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ExchangeController {

    @Autowired
    private ExchangeRepository mainRepository;

    @GetMapping("/exchange/getAll")
    public ResponseEntity<List<Exchange>> getAll() {
        List<Exchange> exchanges = mainRepository.findAll();
        return ResponseEntity.ok(exchanges);
    }

    @GetMapping("/exchange/get/{id}")
    public ResponseEntity<Exchange> getById(@PathVariable Long id) {
        Exchange exchange = mainRepository.findById(id).orElse(null);
        if (exchange == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exchange);
    }

    @PostMapping(value = "/exchange/add")
    public ResponseEntity<?> create(@RequestBody EntityNameRequest request) {
        Exchange exchange = new Exchange(request.getName());

        mainRepository.save(exchange);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/exchange/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EntityNameRequest request) {
        Exchange exchange = mainRepository.findById(id).orElse(null);
        if (exchange == null) {
            return ResponseEntity.notFound().build();
        }

        exchange.setName(request.getName());

        mainRepository.save(exchange);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/exchange/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}