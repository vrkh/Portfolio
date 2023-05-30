package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "exchange")
@Getter
@Setter
@NoArgsConstructor
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Exchange(String name) {
        this.name = name;
    }
}
