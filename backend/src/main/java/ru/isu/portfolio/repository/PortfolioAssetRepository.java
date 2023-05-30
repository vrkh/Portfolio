package ru.isu.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.isu.portfolio.model.db.PortfolioAsset;

import java.util.List;

@Repository
@CrossOrigin(origins = "*")
public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset, Long> {
    boolean existsByAssetIdAndPortfolioId(Long asset_id, Long portfolio_id);

    PortfolioAsset findByAssetIdAndPortfolioId(Long asset_id, Long portfolio_id);

    List<PortfolioAsset> findPortfolioAssetsByPortfolioId(Long portfolio_id);
}
