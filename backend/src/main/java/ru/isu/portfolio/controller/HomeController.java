package ru.isu.portfolio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.response.MessageResponse;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class HomeController {
    @GetMapping("/")
    public String startPage() {
        return "Start page";
    }

    @GetMapping("/admin")
    public ResponseEntity<?> helloAdmin() {
        return ResponseEntity.ok(new MessageResponse("Hello Admin"));
    }

}
