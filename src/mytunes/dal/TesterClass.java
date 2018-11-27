/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.SQLException;
import mytunes.be.Playlist;
import mytunes.be.User;

/**
 *
 * @author Philip
 */
public class TesterClass

{
public static void main(String[] args) throws IOException, SQLServerException, SQLException 
{


//UserDbDAO tester2 = new UserDbDAO();
//User Christian = new User(1,"Christian");
//
//tester2.deleteUser(Christian);
//tester2.getAllUsers();

PlaylistDbDAO test3 = new PlaylistDbDAO();

Playlist ptest = new Playlist(2, "Hardtest");
//test3.addPlaylist(5, "SmallTest");
test3.deletePlaylist(ptest);


}
}
