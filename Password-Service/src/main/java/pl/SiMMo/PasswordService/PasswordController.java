package pl.SiMMo.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.SiMMo.PasswordService.Model.JwtResponse;
import pl.SiMMo.PasswordService.Model.PasswordEntity;
import pl.SiMMo.PasswordService.Model.Response;


@RestController
@RequestMapping("/pass")
public class PasswordController {

    @Autowired
    PasswordRepo passwordRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    WebClient.Builder web;

    @GetMapping("/get")
    public ResponseEntity<Response> getPasswords(@RequestHeader("Token") String token) {

        JwtResponse response = this.getUserIdFromAuthService(token);
        Response res = new Response();
        res.setPasswords(passwordRepo.findAllByUserId(Long.valueOf(response.getId())));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add")
    public ResponseEntity<PasswordEntity> addPassword(@RequestHeader("Token") String token,
                                                      @RequestBody PasswordEntity passwordEntity) {
        JwtResponse response = this.getUserIdFromAuthService(token);
        passwordEntity.setEndPassword(passwordEncoder.encode(passwordEntity.getEndPassword()));
        passwordEntity.setUserId(Long.valueOf(response.getId()));
        passwordRepo.save(passwordEntity);

        return ResponseEntity.ok(passwordEntity);
    }

    @PostMapping("/delete")
    public ResponseEntity deletePassword(@RequestHeader("Token") String token,
                                         @RequestBody() String passwordId) {
        if (passwordRepo.findById(Long.valueOf(passwordId)).isPresent()) {

            final PasswordEntity password = passwordRepo.getById(Long.valueOf(passwordId));

            if (password.getUserId().equals(Long.valueOf(this.getUserIdFromAuthService(token).getId()))) {
                passwordRepo.deleteById(Long.valueOf(passwordId));
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        else
            {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    private JwtResponse getUserIdFromAuthService(String token) {
        return web.build().post().uri("lb://AUTH-SERVICE/auth/getClaims").header("Token", token).retrieve().bodyToMono(JwtResponse.class).block();

    }

}
