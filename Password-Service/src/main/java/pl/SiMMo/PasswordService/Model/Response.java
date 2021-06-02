package pl.SiMMo.PasswordService.Model;

import java.util.List;

public class Response {
   private List<PasswordEntity> passwords;

    public List<PasswordEntity> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<PasswordEntity> passwords) {
        this.passwords = passwords;
    }
}
