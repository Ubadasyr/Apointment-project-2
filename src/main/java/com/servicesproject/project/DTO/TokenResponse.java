package com.servicesproject.project.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String Token;
    private String message;
private String username;
private Long ID;

}
