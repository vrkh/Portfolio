package ru.isu.portfolio.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortfolioRequest {
    private String name;
    private String about;
    private Long user_id;
}
