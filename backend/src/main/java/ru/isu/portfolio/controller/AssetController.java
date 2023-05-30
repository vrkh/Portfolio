package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.*;
import ru.isu.portfolio.model.request.AssetRequest;
import ru.isu.portfolio.repository.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AssetController {
    @Autowired
    private AssetRepository mainRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ExchangeRepository exchangeRepository;

//    @GetMapping("/asset/getAllByUserID/{userId}")
//    public ResponseEntity<List<Portfolio>> getAllByUser(@PathVariable("userId") Long user_id) {
//        List<Portfolio> portfolios = mainRepository.findPortfoliosByUserId(user_id);
//        return ResponseEntity.ok(portfolios);
//    }


    @GetMapping("/asset/getAll")
    public ResponseEntity<List<Asset>> getAll() {
        List<Asset> assets = mainRepository.findAll();
        return ResponseEntity.ok(assets);
    }

    @GetMapping("/asset/get/{id}")
    public ResponseEntity<Asset> getById(@PathVariable Long id) {
        Asset portfolio = mainRepository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping(value = "/asset/add")
    public ResponseEntity<?> create(@RequestBody AssetRequest request) {
        Board board = boardRepository.getOne(request.getBoard_id());
        Country country = countryRepository.getOne(request.getCountry_id());
        Currency currency = currencyRepository.getOne(request.getCurrency_id());
        Exchange exchange = exchangeRepository.getOne(request.getExchange_id());

        Asset asset = new Asset(request.getName(), request.getShort_name(), request.getLot_size(), request.getIsin(), request.getAbout(), country, exchange, board, currency);

        mainRepository.save(asset);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asset/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AssetRequest request) {
        Asset asset = mainRepository.findById(id).orElse(null);
        if (asset == null) {
            return ResponseEntity.notFound().build();
        }

        asset.setName(request.getName());
        asset.setShortName(request.getShort_name());
        asset.setIsin(request.getIsin());
        asset.setLotSize(request.getLot_size());
        asset.setAbout(request.getAbout());
        asset.setBoard(boardRepository.getOne(request.getBoard_id()));
        asset.setCountry(countryRepository.getOne(request.getCountry_id()));
        asset.setCurrency(currencyRepository.getOne(request.getCurrency_id()));
        asset.setExchange(exchangeRepository.getOne(request.getExchange_id()));

        mainRepository.save(asset);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asset/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            mainRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}