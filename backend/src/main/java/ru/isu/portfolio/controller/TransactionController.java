package ru.isu.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isu.portfolio.model.db.*;
import ru.isu.portfolio.model.request.TransactionRequest;
import ru.isu.portfolio.repository.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {
    @Autowired
    private TransactionRepository mainRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private PortfolioAssetRepository portfolioAssetRepository;

    @GetMapping("/transaction/getAllByPortfolioID/{portfolioId}")
    public ResponseEntity<List<Transaction>> getAllTransactionByPortfolioID(@PathVariable("portfolioId") Long portfolioId) {
        List<Transaction> transactions = mainRepository.findAllByPortfolioID(portfolioId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/portfolio_asset/getAllByPortfolioID/{portfolioId}")
    public ResponseEntity<List<PortfolioAsset>> getAllAssetByPortfolioID(@PathVariable("portfolioId") Long portfolioId) {
        List<PortfolioAsset> portfolioAssets = portfolioAssetRepository.findPortfolioAssetsByPortfolioId(portfolioId);
        return ResponseEntity.ok(portfolioAssets);
    }

    @GetMapping("/transaction/get/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        Transaction transaction = mainRepository.findById(id).orElse(null);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @PostMapping(value = "/transaction/add")
    public ResponseEntity<?> create(@RequestBody TransactionRequest request) {
        PortfolioAsset portfolioAsset;
        if (portfolioAssetRepository.existsByAssetIdAndPortfolioId(request.getAsset_id(), request.getPortfolio_id())) {
            portfolioAsset = portfolioAssetRepository.findByAssetIdAndPortfolioId(request.getAsset_id(), request.getPortfolio_id());
        } else {
            Portfolio portfolio = portfolioRepository.getOne(request.getPortfolio_id());
            Asset asset = assetRepository.getOne(request.getAsset_id());

            portfolioAsset = new PortfolioAsset(portfolio, asset);
            portfolioAssetRepository.save(portfolioAsset);
        }

        Transaction transaction = new Transaction(portfolioAsset, request.getVector(), request.getLotCount(), request.getPrice(), request.getFee(), request.getNote(), request.getDate());

        mainRepository.save(transaction);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transaction/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TransactionRequest request) {
        Transaction transaction = mainRepository.findById(id).orElse(null);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        transaction.setDate(request.getDate());
        transaction.setNote(request.getNote());
        transaction.setPrice(request.getPrice());
        transaction.setLotCount(request.getLotCount());
        transaction.setFee(request.getFee());
        transaction.setVector(request.getVector());

        mainRepository.save(transaction);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transaction/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (mainRepository.existsById(id)) {
            PortfolioAsset portfolioAsset = mainRepository.getOne(id).getPortfolioAsset();

            mainRepository.deleteById(id);

            if (!mainRepository.existsByPortfolioAssetId(portfolioAsset.getId())) {
                portfolioAssetRepository.delete(portfolioAsset);
            }

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}