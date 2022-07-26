package rw.wallet.e.domain;

/**
 *
 * @author hirwa
 */
public class LoginCredentials {
    private String enteredNationalId;
    private String enteredPassword;

    public LoginCredentials() {
    }

    public LoginCredentials(String enteredNationalId, String enteredPassword) {
        this.enteredNationalId = enteredNationalId;
        this.enteredPassword = enteredPassword;
    }

    public String getEnteredNationalId() {
        return enteredNationalId;
    }

    public void setEnteredNationalId(String enteredNationalId) {
        this.enteredNationalId = enteredNationalId;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }
}
