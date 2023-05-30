package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "portfolio_asset")
@Getter
@Setter
@NoArgsConstructor
public class PortfolioAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @Column(name = "lot_count")
    @ColumnDefault("0")
    private Integer lotCount;

    @Column(name = "sum_price")
    @ColumnDefault("0")
    private Double sum_price;

    @Column(name = "avg_price")
    @ColumnDefault("0")
    private Double avg_price;

    public PortfolioAsset(Portfolio portfolio, Asset asset) {
        this.portfolio = portfolio;
        this.asset = asset;
        this.lotCount = 0;
        this.sum_price = 0D;
        this.avg_price = 0D;
    }
}