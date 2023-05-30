package ru.isu.portfolio.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "portfolio")
@Getter
@Setter
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "about")
    private String about;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total")
    @ColumnDefault("0")
    private Double total;

    public Portfolio(String name, User user, String about) {
        this.name = name;
        this.user = user;
        this.about = about;
        this.total = 0D;
    }
}