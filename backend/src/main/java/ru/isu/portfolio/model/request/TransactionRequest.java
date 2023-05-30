package ru.isu.portfolio.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TransactionRequest {
    private Boolean vector;
    private Integer lotCount;
    private Double price;

    private Double fee;

    private String note;
    private Date date;
    Long asset_id;
    Long portfolio_id;
}
