package ru.isu.portfolio.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageResponse {
    private String message ;

    public MessageResponse(String s) {
        this.message = s;
    }
}
