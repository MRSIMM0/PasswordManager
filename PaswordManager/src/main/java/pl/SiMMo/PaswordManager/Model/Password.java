package pl.SiMMo.PaswordManager.Model;

public class  Password {

    private String passwordId;

    private String userId;

    private String password;

    public Password(String passwordId, String password) {
        this.passwordId = passwordId;
        this.password = password;
    }

    public String getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(String passwordId) {
        this.passwordId = passwordId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
