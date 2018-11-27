/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Philip
 */
public class TesterClass

{
public static void main(String[] args) throws IOException, SQLServerException, SQLException 
{
DbConnection tester = new DbConnection();
tester.getConnection();

UserDbDAO tester2 = new UserDbDAO();
tester2.addUser("Mogens");
    
}
}
