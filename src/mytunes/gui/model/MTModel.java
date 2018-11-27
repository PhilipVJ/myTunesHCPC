/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import mytunes.be.User;
import mytunes.bll.MTManager;

/**
 *
 * @author Philip
 */
public class MTModel
{
MTManager mtmanager = new MTManager();

public List<User> getUsers() throws IOException, SQLException
{
return mtmanager.getAllUsers();

}

    
}
