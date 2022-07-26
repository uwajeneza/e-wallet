package rw.wallet.e.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author hirwa
 */
@Entity
public class User implements Serializable {
    @Id
    private String nationalId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private long accountNumber;
    private String password1;
    private String password2;

    public User() {
    }

    
        
}
