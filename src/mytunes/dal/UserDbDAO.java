/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import mytunes.be.User;

/**
 *
 * @author Philip
 */
public class UserDbDAO
{
    
public User addUser(String username) throws IOException, SQLServerException, SQLException
{
 DbConnection tester = new DbConnection();
 Connection con = tester.getConnection();
 
 String SQL = "INSERT INTO Users VALUES (?)";
 PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
 pstmt.setString(1,username);
 pstmt.execute();
 
 return null;
}

public void deleteUser(User userToDelete)
{
    
}

public List<User> getAllUsers()
{
    return null;
}
    
}
