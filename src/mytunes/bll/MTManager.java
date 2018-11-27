/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

    
}
