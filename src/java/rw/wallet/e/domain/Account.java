package rw.wallet.e.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author hirwa
 */
@Entity
public class Account implements Serializable {
    @Id
    private long accountNumber;
    private String nationalId;
    private String accountName;
    private double amount;
    private User user;

    public Account() {
        amount= 1000.0;
    }

    public Account(long accountNumber, String nationalId, String accountName, double amount, User user) {
        this.accountNumber = accountNumber;
        this.nationalId = nationalId;
        this.accountName = accountName;
        this.amount = amount;
        this.user = user;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
}
