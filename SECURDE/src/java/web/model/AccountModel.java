package web.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import org.mindrot.jbcrypt.BCrypt;
import web.Account;
import web.WebConnection;

public final class AccountModel {
    private final ArrayList<Account> list;
    private final Connection con;
    private static AccountModel instance = null;

    protected AccountModel() throws SQLException, ServletException {
        list = new ArrayList<>();
        con = WebConnection.getInstance().getDataSource().getConnection();
        updateModelList();
    }

    public static AccountModel getInstance() throws SQLException, ServletException {
        if(instance == null) {
            instance = new AccountModel();
        }

        return instance;
    }

    protected void updateModelList() throws SQLException {
        list.removeAll(list);
        String sql = "SELECT * FROM " + Account.TABLE_NAME;
        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()) {
            Account toAdd;

            int accountID = rs.getInt(Account.ACCOUNT_ID);
            int loginAttempts = rs.getInt(Account.LOG_IN_ATTEMPT);
            String userName = rs.getString(Account.USERNAME);
            String password = rs.getString(Account.PASSWORD);
            String firstName = rs.getString(Account.FIRSTNAME);
            String lastName = rs.getString(Account.LASTNAME);
            String middleInitial = rs.getString(Account.MIDDLEINITIAL);
            String email = rs.getString(Account.EMAIL);
            Date lockDate = rs.getDate(Account.LOCK_DATE);

            toAdd = new Account.AccountBuilder()
                        .accountId(accountID)
                        .username(userName)
                        .email(email)
                        .password(password)
                        .firstName(firstName)
                        .lastName(lastName)
                        .middleInitial(middleInitial)
                        .loginAttempt(loginAttempts)
                        .lockDate(lockDate)
                        .build();
            list.add(toAdd);
        }
    }

    public Iterator<?> getModelList() {
        return list.iterator();
    }

    public Account getAccountByID(int id) {
        for(Account a: list) {
            if(a.getID() == id)
                return a;
        }return null;
    }
	
    public void addAccount(Account toAdd) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO " + Account.TABLE_NAME + 
                        "(" + Account.USERNAME + ", " + Account.PASSWORD +
                        ", " + Account.FIRSTNAME + ", " + Account.LASTNAME +
                        ", " + Account.MIDDLEINITIAL + ", " + Account.EMAIL +
                        ") VALUES(?, ?, ?, ?, ?, ?)");

        String hashedPass = BCrypt.hashpw(toAdd.getPassword(), BCrypt.gensalt());

        ps.setString(1, toAdd.getUsername());
        ps.setString(2, hashedPass);
        ps.setString(3, toAdd.getFirstName());
        ps.setString(4, toAdd.getLastName());
        ps.setString(5, toAdd.getMiddleInitial());
        ps.setString(6, toAdd.getEmail());
        ps.executeUpdate();

        updateModelList();
    }
	
    public Account getAccountByUsernameOrEmail(String username) {
        for(Account a: list) {
            if(a.getUsername().equals(username) || a.getEmail().equals(username))
                return a;
        }
        return null;
    }
        
    public boolean validateAccount(String username, String password) throws SQLException {
        String sql = "SELECT " + Account.EMAIL + ", " + Account.USERNAME +
                ", " + Account.PASSWORD + ", " + Account.LOCK_DATE + " FROM " + Account.TABLE_NAME + 
                " WHERE " + Account.USERNAME + "=? OR " + Account.EMAIL+ "=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, username);
        ResultSet rs = ps.executeQuery();
        rs.beforeFirst();

        while(rs.next()) {

            try {
                Date rsLockDate = rs.getDate(Account.LOCK_DATE);
                if(!rsLockDate.before(Date.valueOf(LocalDate.now()))) {
                    return false;
                } else {
                    this.resetAttempts(username);
                }
            } catch(NullPointerException ex) {

            }

            String rsPassword = rs.getString(Account.PASSWORD);
            if(BCrypt.checkpw(password, rsPassword)) {
                return true;
            }
        }

        return false;
    }
        
    public boolean isAdmin(int accountId) throws SQLException {
        String sql = "SELECT * FROM " + Account.ADMIN_TABLE_NAME + " WHERE " +
                Account.ACCOUNT_ID + " = ?;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accountId);

        return ps.executeQuery().isBeforeFirst();
    }

    public int addLoginAttempt(String username) throws SQLException {
        try {
            Account account = this.getAccountByUsernameOrEmail(username);

            account.addLoginAttempt();

            String sql = "UPDATE " + Account.TABLE_NAME + " SET " + 
                        Account.LOG_IN_ATTEMPT + " = ? WHERE " + Account.ACCOUNT_ID +
                        " = ?;";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, account.getLoginAttempts());
            ps.setInt(2, account.getID());

            ps.executeUpdate();
            
            updateModelList();
            
            return account.getLoginAttempts();
        } catch(NullPointerException ex) {
            updateModelList();
            
            return 0;
        }
    }
    
    public void setLockDate(String user) throws SQLException {
        Account account = getAccountByUsernameOrEmail(user);
        
        String sql = "UPDATE " + Account.TABLE_NAME + " SET " +
                    Account.LOCK_DATE + " = CURRENT_TIMESTAMP WHERE " +
                    Account.ACCOUNT_ID + " = ?";
        
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setInt(1, account.getID());
        ps.executeUpdate(); 
        
        updateModelList();
    }
    
    public void resetAttempts(String user) throws SQLException {
        Account account = getAccountByUsernameOrEmail(user);
        
        String sql = "UPDATE " + Account.TABLE_NAME + " SET " +
                    Account.LOG_IN_ATTEMPT + " = 0, " + 
                    Account.LOCK_DATE + "= NULL WHERE " +
                    Account.ACCOUNT_ID + " = ?";
        
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setInt(1, account.getID());
        ps.executeUpdate();
        
        updateModelList();
    }
}


