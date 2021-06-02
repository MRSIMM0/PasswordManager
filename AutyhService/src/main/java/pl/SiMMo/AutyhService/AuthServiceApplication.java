package pl.SiMMo.AutyhService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.SiMMo.AutyhService.Models.User;
import pl.SiMMo.AutyhService.Models.UserRepo;

@SpringBootApplication
@EnableEurekaClient
public class AuthServiceApplication implements CommandLineRunner {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
	@Autowired
	UserRepo userRepo;


	@Override
	public void run(String... args) throws Exception {
		User user = new User("test",passwordEncoder().encode("test"));

		userRepo.save(user);
		System.out.println(userRepo.findUserByUsername("test").get().getPassword());

	}
}
