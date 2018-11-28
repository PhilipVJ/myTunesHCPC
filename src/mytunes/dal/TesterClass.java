/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.be.User;
import org.farng.mp3.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 *
 * @author Philip
 */
public class TesterClass

{
public static void main(String[] args) throws IOException, SQLServerException, SQLException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException 
{


//UserDbDAO tester2 = new UserDbDAO();
//User Christian = new User(1,"Christian");
//
//tester2.deleteUser(Christian);
//tester2.getAllUsers();
    
 SongDbDAO tester4 = new SongDbDAO();
 ArrayList<Song>allSongs=tester4.getAllSongs();
 for (Song x : allSongs)
 {
     System.out.println(""+x.getTitle());
 }

    

}
}
