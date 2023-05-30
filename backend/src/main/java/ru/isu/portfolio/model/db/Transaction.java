package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_asset_id")
    private PortfolioAsset portfolioAsset;

    @Column(name = "vector")
    private Boolean vector;

    @Column(name = "lot_count")
    @ColumnDefault("1")
    private Integer lotCount;

    @Column(name = "price")
    @ColumnDefault("0")
    private Double price;

    @Column(name = "fee")
    @ColumnDefault("0")
    private Double fee;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private Date date;

    public Transaction(PortfolioAsset portfolioAsset, Boolean vector, Integer lotCount, Double price, Double fee, String note, Date date) {
        this.portfolioAsset = portfolioAsset;
        this.vector = vector;
        this.lotCount = lotCount;
        this.price = price;
        this.fee = fee;
        this.note = note;
        this.date = date;
    }
}
