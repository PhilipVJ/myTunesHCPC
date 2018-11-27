/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import mytunes.be.User;
import mytunes.dal.UserDbDAO;

/**
 *
 * @author Philip
 */
public class MTManager
{
UserDbDAO userDB = new UserDbDAO(); 

public List<User> getAllUsers() throws IOException, SQLException{
    
return userDB.getAllUsers();
    
}

 public void deleteUser(User userToDelete) throws IOException, SQLException
{   
userDB.deleteUser(userToDelete);
}

public void addUser(String username) throws IOException, SQLException
{
userDB.addUser(username);
}

    
}
