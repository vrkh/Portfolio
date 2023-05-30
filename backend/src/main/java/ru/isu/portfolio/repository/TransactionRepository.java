package ru.isu.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.isu.portfolio.model.db.Transaction;

import java.util.List;

@Repository
@CrossOrigin(origins = "*")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("FROM Transaction AS t WHERE t.portfolioAsset.portfolio.id=:portfolio_id")
    List<Transaction> findAllByPortfolioID(@Param("portfolio_id") Long portfolio_id);

    boolean existsByPortfolioAssetId(Long portfolio_asset_id);
}