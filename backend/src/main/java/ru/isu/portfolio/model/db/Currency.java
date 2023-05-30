package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Currency(String name) {
        this.name = name;
    }
}
