package ru.isu.portfolio.model.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupRequest {
    private String username;
    private String password;
}

