package ru.isu.portfolio.model.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private Double total;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, List<String> roles, Double total) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.total = total;
    }
}

