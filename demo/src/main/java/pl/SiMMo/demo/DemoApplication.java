package pl.SiMMo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class DemoApplication {

//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder){
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.uri("lb://MOVIE-INFO-SERVICE"))
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


	}



}
