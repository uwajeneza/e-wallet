package rw.wallet.e.model;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import rw.wallet.e.controller.GenericDao;
import rw.wallet.e.domain.Account;
import rw.wallet.e.domain.LoginCredentials;
import rw.wallet.e.domain.User;

/**
 *
 * @author nicole
 */
@SessionScoped
@ManagedBean(name = "ewallet")
public class Ewallet {

    public Ewallet() {
    }
    
    /*Class Instantiation*/
    private Account account = new Account();
    private User user = new User();
    private GenericDao genericDao = new GenericDao();
    private LoginCredentials credentials = new LoginCredentials();
    String loggedInUser;
    String nationalIdOfLoggedInUser;
    Double userBalance;
    Account userAccount;
    
    
    /*Getter and Setters*/
    public GenericDao getGenericDao() {
        return genericDao;
    }

    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }
    
    public Account getAccount() {
        return account;
    }

    public User getUser() {
        return user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCredentials(LoginCredentials credentials) {
        this.credentials = credentials;
    }

    public LoginCredentials getCredentials() {
        return credentials;
    }
    
    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    public String getNationalIdOfLoggedInUser() {
        return nationalIdOfLoggedInUser;
    }

    public void setNationalIdOfLoggedInUser(String nationalIdOfLoggedInUser) {
        this.nationalIdOfLoggedInUser = nationalIdOfLoggedInUser;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }
    
    
    
    /*NAVIGATION*/
    public String goToUserInfo(){
        return "accountDetails";
    }

    public String goToSend(){
        return "send";
    }

    public String goToBalance(){
        System.out.println("The national Id of the logged in user is: "+nationalIdOfLoggedInUser);
        
        userAccount = genericDao.findUserAccount(loggedInUser);
        System.out.println("The user's account name is: "+userAccount.getAccountName());
        userBalance = userAccount.getAmount();
        return "balance";
    }

    public String goToLogin(){
        return "login";
    }
    
    public String goToCreateAccount(){
        return "create-account";
    }
    
    public String goToIndex(){
        return "index";
    }
    
    public String goToNext(){
        //GENERATING DETAILS ABOUT THE ACCOUNT.
        //Generating a random number as the account number
        long min = 1000000;
        long max = 1000000000;
        
        //Generate random int value from 50 to 100 
        long randomNumber = (long)Math.floor(Math.random()*(max-min+1)+min);
        account.setAccountNumber(randomNumber);
        account.setAccountName(user.getFirstName()+" "+user.getLastName());
        account.setNationalId(user.getNationalId());
        return "steptwo";
    }

    public String goToPrevious(){
        return "create-account";
    }
    
    public String createAccount(){
        FacesMessage saveMessage;
        try {            
            //SAVING THE ACCOUNT
            genericDao.createAccount(account);
            
            //SAVING THE USER
            genericDao.createUser(user);
            
            saveMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,"Account successfuly created!","Make sure all required Infomation is provided.");
            FacesContext.getCurrentInstance().addMessage("success-message", saveMessage);
            return "accountDetails";
           
        } catch (Exception e) {
            saveMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL,"Failed to create Account! | "+e.getMessage()+"","Make sure all required Infomation is provided.");
            FacesContext.getCurrentInstance().addMessage("error-message", saveMessage);
            return "create-account";        
        }
    }
    
    
    
    /*LOGIN*/
    public String login(){
        try {
            String enteredNationalId = credentials.getEnteredNationalId();
            String enteredPassword = credentials.getEnteredPassword();
            
            User foundUser = genericDao.findUser(enteredNationalId);
            String savedNationalId = foundUser.getNationalId();
            String savedPassword = foundUser.getPassword1();
            
            if (foundUser == null) {
                FacesMessage loginMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"National Id Not Recognized","Try again");
                FacesContext.getCurrentInstance().addMessage("error-message", loginMessage);
                return "login";   
            } else if (enteredNationalId.equalsIgnoreCase(savedNationalId) || enteredPassword.equalsIgnoreCase(savedPassword)) {
                
                loggedInUser = foundUser.getFirstName()+" "+foundUser.getLastName();
                nationalIdOfLoggedInUser = foundUser.getNationalId();
                return "accountDetails";
                   
            } else {
                FacesMessage loginMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Incorrect username or password","Try again");
                FacesContext.getCurrentInstance().addMessage("error-message", loginMessage);
                return "login";
            }
        } catch (Exception e) {
            FacesMessage loginMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Incorrect username or password","Try again");
            FacesContext.getCurrentInstance().addMessage("error-message", loginMessage);
            return "login";
        }
    }
    
    /*TRANSFERING OR SENDING MONEY*/
    public String send(){
        try {
            Double sentAmount = 1000.0;
            String recieverName = "John Doe";
            Double myNewBalance = 1000.0;
            if (true) {
                user.setFirstName("John");
                user.setLastName("Doe");
                FacesMessage sendMessage = new FacesMessage(FacesMessage.SEVERITY_INFO ,"Successfully Sent "+sentAmount+" to "+recieverName+". You new Balance is: "+myNewBalance,"Success!");
                FacesContext.getCurrentInstance().addMessage("error-message", sendMessage);
                return "accountDetails";   
            } else {
                FacesMessage sendMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Transfer Failed! Try Again!","Try again");
                FacesContext.getCurrentInstance().addMessage("error-message", sendMessage);
                return "send";   
            }
        } catch (Exception e) {
            FacesMessage sendMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"There is a problem!","Try again");
            FacesContext.getCurrentInstance().addMessage("error-message", sendMessage);
            return "send";
        }
    }
    
    
}
