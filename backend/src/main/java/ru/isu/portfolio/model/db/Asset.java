package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "asset")
@Getter
@Setter
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "lot_size")
    @ColumnDefault("1")
    private Integer lotSize;

    @Column(name = "isin")
    private String isin;

    @Column(name = "about")
    private String about;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "exchange_id")
    private Exchange exchange;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    public Asset(String name, String shortName, String symbol, Integer lotSize, String isin, String about, Country country, Exchange exchange, Board board, Currency currency) {
        this.name = name;
        this.shortName = shortName;
        this.symbol = symbol;
        this.lotSize = lotSize;
        this.isin = isin;
        this.about = about;
        this.country = country;
        this.exchange = exchange;
        this.board = board;
        this.currency = currency;
    }
}
