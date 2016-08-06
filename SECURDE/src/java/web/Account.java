package web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Miko Garcia
 */
public class Account {
    
    public final static String TABLE_NAME =     "accounts";
    public final static String ACCOUNT_ID =     "account_id";
    public final static String USERNAME =       "username";
    public final static String EMAIL =          "email";
    public final static String PASSWORD =       "password";
    public final static String FIRSTNAME =      "f_name";
    public final static String LASTNAME =       "l_name";
    public final static String MIDDLEINITIAL =  "m_initial";
    
    private int accountId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String middleInitial;
    
    public static class AccountBuilder {
        private int accountId = 0;
        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
        private String middleInitial;
        
        public AccountBuilder() {
            
        }
        
        public AccountBuilder accountId(int accountId) {
            this.accountId = accountId;
            return this;
        }
        
        public AccountBuilder username(String username) {
            this.username = username;
            return this;
        }
        
        public AccountBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public AccountBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public AccountBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public AccountBuilder middleInitial(String middleInitial) {
            this.middleInitial = middleInitial;
            return this;
        }
        
        public AccountBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public Account build() {
            return new Account(accountId, username, password, email, firstName, lastName, middleInitial);
        }
    }
 
    private Account(int accountId, String username, String password, String email, String firstName, String lastName, String middleInitial) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
    }
    
    public void giveID(int accountId) {
        this.accountId = accountId;
    }
    
    public int getID() {
        return this.accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }
    
    @Override
    public String toString() {
        return this.username + " " + this.password;
    }
}
