package ru.isu.portfolio.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
public class QuoteRequest {
    Long asset_id;
    Date date;
    Double price;
}