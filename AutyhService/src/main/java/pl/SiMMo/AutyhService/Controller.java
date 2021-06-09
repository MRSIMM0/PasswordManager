package pl.SiMMo.AutyhService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.SiMMo.AutyhService.Models.JwtResponse;
import pl.SiMMo.AutyhService.Models.RequestForm;
import pl.SiMMo.AutyhService.Models.User;
import pl.SiMMo.AutyhService.Models.UserRepo;
import pl.SiMMo.AutyhService.Security.jwt.JwtProvider;

@RestController
@RequestMapping("/auth")
@Slf4j

public class Controller {




  private   AuthenticationManager authenticationManager;

  private JwtProvider jwtProvider;

  private UserRepo userRepo;

  private PasswordEncoder passwordEncoder;


    public Controller(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody RequestForm loginRequest) {
        if (userRepo.findUserByUsername(loginRequest.getUsername()).isPresent()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            JwtResponse res = new JwtResponse();
            res.setAccessToken(jwt);
            res.setId(userRepo.findUserByUsername(loginRequest.getUsername()).get().getId().toString());

            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RequestForm requestForm){
        if(userRepo.findUserByUsername(requestForm.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
        else{
            User user =new User(requestForm.getUsername(),passwordEncoder.encode(requestForm.getPassword()));
            userRepo.save(user);
            return ResponseEntity.status(200).build();
        }
    }

    @PostMapping("/getClaims")
    public ResponseEntity<?> getClaims(@RequestHeader(name = "Token") String Token){

        String token = getJwt(Token);

        if (jwtProvider.validateJwtToken(token)){
            String username = jwtProvider.getUserNameFromJwtToken(token);


            JwtResponse response = new JwtResponse();

            response.setToken(token);
            try {
                response.setId(userRepo.findUserByUsername(username).get().getId().toString());
            }catch (Exception e){
                e.getCause();
            }
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String getJwt(String request) {

        if (request != null && request.startsWith("Bearer ")) {
            return request.replace("Bearer ","");
        }

        if (request != null && request.startsWith("Bearer")) {
            return request.replace("Bearer","");
        }

        return request;
    }

}
