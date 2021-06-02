package pl.SiMMo.PaswordManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import pl.SiMMo.PaswordManager.Model.Password;

@RestController
public class PasswordController {
   @Autowired
   private WebClient.Builder builder;

   @PostMapping
    public ResponseEntity authenticate(){
       
   }
    @RequestMapping
    public ResponseEntity<Password> getPasswords(){
        return new ResponseEntity<Password>.ok();
    }

}
