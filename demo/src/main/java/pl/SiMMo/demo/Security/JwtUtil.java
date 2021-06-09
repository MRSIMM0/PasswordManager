package pl.SiMMo.demo.Security;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import java.util.Date;


@Component

public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationDate}")
    private int jwtExpiration;

    @PostConstruct
    public void init(){

    }

    public boolean validateJwtToken(String authToken, ServerWebExchange exchange) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(this.getToken(authToken));
            return true;

        } catch (SignatureException e) {
            this.onError(exchange,"Invalid JWT signature -> ",HttpStatus.UNAUTHORIZED);
            logger.error("Invalid JWT signature -> Message: {} ", e);
            return false;
        } catch (MalformedJwtException e) {
            this.onError(exchange,"Invalid JWT token -> ",HttpStatus.UNAUTHORIZED);
            logger.error("Invalid JWT token -> Message: {}", e);
            return false;

        } catch (ExpiredJwtException e) {
            this.onError(exchange,"Expired JWT token -> ",HttpStatus.UNAUTHORIZED);
            logger.error("Expired JWT token -> Message: {}", e);
            return false;
        } catch (UnsupportedJwtException e) {
            this.onError(exchange,"Unsupported JWT token -> ",HttpStatus.UNAUTHORIZED);
            logger.error("Unsupported JWT token -> Message: {}", e);
            return false;
        } catch (IllegalArgumentException e) {
            this.onError(exchange,"JWT claims string is empty -> ",HttpStatus.UNAUTHORIZED);
            logger.error("JWT claims string is empty -> Message: {}", e);
            return false;

        }
    }

    public Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(this.getToken(token))
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

  private String getToken(String token){
        if (token!=null && token.startsWith("Bearer ")){
            token = token.replace("Bearer ","");
        }
      return token;
   }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

}
