package io.github.martnsbreno.security.jwt;

import io.github.martnsbreno.VendasApplication;
import io.github.martnsbreno.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JWTService {
    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken (Usuario usuario) {
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date data = Date.from(instant);
        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura ).compact();
    }

    private Claims obterClaims ( String token ) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
    }

    public boolean tokenValido( String token) {
        try {
            Claims claims = obterClaims(token);
            java.util.Date expiration = claims.getExpiration();
            LocalDateTime date = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(VendasApplication.class);
        JWTService jwtService = run.getBean(JWTService.class);
        Usuario usuario = Usuario.builder().login("lucy").build();
        String token = jwtService.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValid = jwtService.tokenValido(token);
        System.out.println("token está valido?" + isTokenValid);

        System.out.println(jwtService.obterLoginUsuario(token));
    }
}
