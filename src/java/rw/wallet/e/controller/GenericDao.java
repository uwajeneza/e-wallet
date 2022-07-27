package rw.wallet.e.controller;

import java.util.List;
import org.hibernate.*;
import rw.wallet.e.domain.Account;
import rw.wallet.e.domain.LoginCredentials;
import rw.wallet.e.domain.User;
import rw.wallet.e.util.HibernateUtil;

/**
 *
 * @author hirwa
 */
public class GenericDao {
    Session session = null;
    Transaction transaction = null;
    
    /*USER CRUD*/
    public void createUser(User user){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void updateUser(User user){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void deleteUser(User user){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public List<User> listUser(User user){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        List<User> listOfUsers = session.createCriteria(User.class).list();
        transaction.commit();
        session.close();
        return listOfUsers;
    }
    
    /*ACCOUNT CRUD*/
    public void createAccount(Account account){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.save(account);
        transaction.commit();
        session.close();
    }

    public void updateAccount(Account account){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.update(account);
        transaction.commit();
        session.close();
    }

    public void deleteAccount(Account account){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.delete(account);
        transaction.commit();
        session.close();
    }

    public List<Account> listAccounts(Account account){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        List<Account> listOfAccounts = session.createCriteria(Account.class).list();
        transaction.commit();
        session.close();
        return listOfAccounts;
    }
    
    /*CREDENTIAL CRUD*/
    public void saveCredentials(LoginCredentials credentials){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.save(credentials);
        transaction.commit();
        session.close();        
    }
    
    public List<LoginCredentials> listAccounts(LoginCredentials credentials){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        List<LoginCredentials> listOfCredentialLogins = session.createCriteria(LoginCredentials.class).list();
        transaction.commit();
        session.close();
        return listOfCredentialLogins;
    }
    
    public User findUser(String nationalId){
        User foundUser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            foundUser = (User)session.get(User.class, nationalId);
            session.close();
            if (foundUser==null) {
                return null;
            } else {
                return foundUser;
            }
        } catch (HibernateException e) {
            return null;
        }
    }

    public Account findAccount(long accountNumber){
        Account foundAccount = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            foundAccount = (Account)session.get(Account.class, accountNumber);
            session.close();
            if (foundAccount==null) {
                return null;
            } else {
                return foundAccount;
            }
        } catch (HibernateException e) {
            return null;
        }
    }
}
