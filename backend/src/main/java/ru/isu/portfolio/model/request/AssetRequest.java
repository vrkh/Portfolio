package ru.isu.portfolio.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssetRequest {
    String name;
    String short_name;
    String symbol;
    String isin;
    String about;
    Integer lot_size;
    Long board_id;
    Long country_id;
    Long currency_id;
    Long exchange_id;
}
