package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quote")
@Getter
@Setter
@NoArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    @ColumnDefault("0")
    private Double price;

    public Quote(Asset asset, Date date, Double price) {
        this.asset = asset;
        this.date = date;
        this.price = price;
    }
}