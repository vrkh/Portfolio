package ru.isu.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.isu.portfolio.model.db.Exchange;

@Repository
@CrossOrigin(origins = "*")
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

}