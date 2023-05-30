package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.Portfolio;
import ru.isu.portfolio.model.db.User;
import ru.isu.portfolio.model.request.PortfolioRequest;
import ru.isu.portfolio.repository.UserRepository;
import ru.isu.portfolio.repository.PortfolioRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PortfolioController {
    @Autowired
    private PortfolioRepository mainRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/portfolio/getAllByUserID/{userId}")
    public ResponseEntity<List<Portfolio>> getAllByUser(@PathVariable("userId") Long user_id) {
        List<Portfolio> portfolios = mainRepository.findPortfoliosByUserId(user_id);
        return ResponseEntity.ok(portfolios);
    }

    @GetMapping("/portfolio/get/{id}")
    public ResponseEntity<Portfolio> getById(@PathVariable Long id) {
        Portfolio portfolio = mainRepository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping(value = "/portfolio/add")
    public ResponseEntity<?> create(@RequestBody PortfolioRequest request
    ) {
        User user = userRepository.getOne(request.getUser_id());
        Portfolio portfolio = new Portfolio(request.getName(), user, request.getAbout());

        mainRepository.save(portfolio);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/portfolio/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PortfolioRequest request) {
        Portfolio portfolio = mainRepository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }
        portfolio.setName(request.getName());
        portfolio.setAbout(request.getAbout());
        mainRepository.save(portfolio);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/portfolio/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/user/getTotal/{id}")
    public ResponseEntity<Double> getTotal(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getTotal());
    }
}