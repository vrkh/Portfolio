package ru.isu.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.isu.portfolio.model.db.Portfolio;

import java.util.List;

@Repository
@CrossOrigin(origins = "*")
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findPortfoliosByUserId(@Param("user_id") Long user_id);

}
