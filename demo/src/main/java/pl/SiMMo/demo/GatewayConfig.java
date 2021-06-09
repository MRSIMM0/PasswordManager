package pl.SiMMo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.SiMMo.demo.Security.AuthenticationFilter;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        //To-Do Routes
        return builder

                .routes()
                .route("AUTH-SERVICE", p-> p.path("/auth/**").filters(f->f.filter(filter)).uri("lb://AUTH-SERVICE"))
                .route("PASSWORD-SERVICE", p -> p.path("/pass/**").filters( f-> f.filter(filter)).uri("lb://PASSWORD-SERVICE"))
                .build();
    }

}
