package pl.SiMMo.demo.Security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;

import java.net.URI;

@Component
@RefreshScope
public class AuthenticationFilter implements GatewayFilter {
    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();


        if(routeValidator.isSecured.test(request)){
            if(this.isAuthMissing(request)){

                exchange.mutate().response( this.onError(exchange,"Token header is missing in request", HttpStatus.UNAUTHORIZED)).build();
                exchange.mutate().request(exchange.getRequest().mutate().uri(URI.create("").normalize()).build());
                return chain.filter(exchange);
            }
            final String token = this.getAuthHeader(request);

            if(jwtUtil.validateJwtToken(token,exchange)){
                this.populateWithRequestHeaders(exchange,token);
            }else {

                exchange.mutate().response(this.onError(exchange, "Token header is invalid", HttpStatus.BAD_REQUEST)).build();
                System.out.println(exchange.getResponse().getStatusCode().value());
                return chain.filter(exchange);
            }

        }

        return chain.filter(exchange);
    }


    private ServerHttpResponse onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response;
    }

    private String getAuthHeader(ServerHttpRequest request){
        return request.getHeaders().getOrEmpty("Token").get(0);
    }

    private Boolean isAuthMissing (ServerHttpRequest request){
        return !request.getHeaders().containsKey("Token");
    }

    private void populateWithRequestHeaders(ServerWebExchange exchange,String token) {
        if (jwtUtil.validateJwtToken(token,exchange)) {
            Claims claims = jwtUtil.getAllClaimsFromToken(token);
            exchange.getRequest().mutate()
                    .header("username", String.valueOf(claims.get("username")));

        }
    }
}
