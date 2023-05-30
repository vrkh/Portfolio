package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.*;
import ru.isu.portfolio.model.request.BoardRequest;
import ru.isu.portfolio.repository.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    @Autowired
    private BoardRepository mainRepository;

    @GetMapping("/board/getAll")
    public ResponseEntity<List<Board>> getAll() {
        List<Board> boards = mainRepository.findAll();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/board/get/{id}")
    public ResponseEntity<Board> getById(@PathVariable Long id) {
        Board board = mainRepository.findById(id).orElse(null);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(board);
    }

    @PostMapping(value = "/board/add")
    public ResponseEntity<?> create(@RequestBody BoardRequest request) {
        Board board = new Board(request.getCode(), request.getName());

        mainRepository.save(board);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/board/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BoardRequest request) {
        Board board = mainRepository.findById(id).orElse(null);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }

        board.setCode(request.getCode());
        board.setName(request.getName());

        mainRepository.save(board);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/board/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
