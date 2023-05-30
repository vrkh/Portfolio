package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.Quote;
import ru.isu.portfolio.model.request.QuoteRequest;
import ru.isu.portfolio.repository.AssetRepository;
import ru.isu.portfolio.repository.QuoteRepository;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class QuoteController {
    @Autowired
    private QuoteRepository mainRepository;
    @Autowired
    private AssetRepository assetRepository;

    @GetMapping("/quote/getAll")
    public ResponseEntity<List<Quote>> getAll() {
        List<Quote> quotes = mainRepository.findAll();
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/quote/get/{id}")
    public ResponseEntity<Quote> getById(@PathVariable Long id) {
        Quote quote = mainRepository.findById(id).orElse(null);
        if (quote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quote);
    }

    @PostMapping(value = "/quote/add")
    public ResponseEntity<?> create(@RequestBody QuoteRequest request) {
        Quote quote = new Quote(assetRepository.getOne(request.getAsset_id()), request.getDate(), request.getPrice());

        mainRepository.save(quote);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/quote/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody QuoteRequest request) {
        Quote quote = mainRepository.findById(id).orElse(null);
        if (quote == null) {
            return ResponseEntity.notFound().build();
        }

        quote.setDate(request.getDate());
        quote.setPrice(request.getPrice());

        mainRepository.save(quote);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/quote/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}