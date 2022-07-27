package rw.wallet.e.model;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import rw.wallet.e.controller.GenericDao;
import rw.wallet.e.domain.Account;
import rw.wallet.e.domain.LoginCredentials;
import rw.wallet.e.domain.Send;
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
    private Send sending = new Send();
    String loggedInUser;
    String nationalIdOfLoggedInUser;
    Double userBalance;
    Account userAccount;
    User foundUser;
    
    
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

    public User getFoundUser() {
        return foundUser;
    }

    public void setFoundUser(User foundUser) {
        this.foundUser = foundUser;
    }

    public Send getSending() {
        return sending;
    }

    public void setSending(Send sending) {
        this.sending = sending;
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
        
        userAccount = genericDao.findAccount(foundUser.getAccountNumber());
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
            //SAVING THE USER
            user.setAccountNumber(account.getAccountNumber());
            //SAVING THE ACCOUNT
            genericDao.createAccount(account);
            genericDao.createUser(user);
            
            loggedInUser = user.getFirstName()+" "+user.getLastName();
                
            saveMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,"Account successfuly created!","Make sure all required Infomation is provided.");
            FacesContext.getCurrentInstance().addMessage("success-message", saveMessage);
            return "login";
           
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
            
            foundUser = genericDao.findUser(enteredNationalId);
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
            Double sentAmount = sending.getSentAmount();
            System.out.println("The amount to be sent is: "+sentAmount);
            long recieverAccountNumber = sending.getRecieverAccount();
            System.out.println("The account of the reciever is: "+recieverAccountNumber);
            Double myPreviousBalance = userBalance;
            System.out.println("My previous Balance is: "+myPreviousBalance);
            Double myNewBalance = myPreviousBalance - sentAmount;
            System.out.println("My new balance after transfer: "+myNewBalance);
            
            Account recieverAccount = genericDao.findAccount(recieverAccountNumber);
            Double amountOnRecieverAccount = recieverAccount.getAmount();
            System.out.println("The amount of money on the reciever account: "+amountOnRecieverAccount);
            
            if (myPreviousBalance < sentAmount) {
                FacesMessage sendMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Sorry, your balance is not enough to send the amount you want to send.","Try again");
                FacesContext.getCurrentInstance().addMessage("error-message", sendMessage);
                return "send";   
            } else {
                user.setFirstName("John");
                user.setLastName("Doe");
                
                account.setAmount(myPreviousBalance-sentAmount);
                account.setAccountNumber(account.getAccountNumber());
                account.setAccountName(account.getAccountName());
                account.setNationalId(account.getNationalId());
                
                recieverAccount.setAmount(amountOnRecieverAccount+sentAmount);
                recieverAccount.setAccountName(recieverAccount.getAccountName());
                recieverAccount.setAccountNumber(recieverAccountNumber);
                recieverAccount.setNationalId(recieverAccount.getNationalId());
                        
                genericDao.updateAccount(account);
                genericDao.updateAccount(recieverAccount);
                
                Account myAccountAfterSendingMoney = genericDao.findAccount(foundUser.getAccountNumber());
                
                FacesMessage sendMessage = new FacesMessage(FacesMessage.SEVERITY_INFO ,"Successfully Sent "+sentAmount+" to "+recieverAccount.getAccountName()+". You new Balance is: "+myAccountAfterSendingMoney.getAmount(),"Success!");
                FacesContext.getCurrentInstance().addMessage("error-message", sendMessage);
                return "accountDetails";   
            }
        } catch (Exception e) {
            FacesMessage sendMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"There is a problem! | "+e.getMessage(),"Try again");
            FacesContext.getCurrentInstance().addMessage("error-message", sendMessage);
            return "send";
        }
    }
    
    
}
