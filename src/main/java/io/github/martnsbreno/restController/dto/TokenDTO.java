package io.github.martnsbreno.restController.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String loginUsuario;
    private String token;
}
