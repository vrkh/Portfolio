package ru.isu.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.isu.portfolio.model.db.Currency;

@Repository
@CrossOrigin(origins = "*")
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
