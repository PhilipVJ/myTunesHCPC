/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.be.User;
import mytunes.dal.PlaylistDbDAO;
import mytunes.dal.SongDbDAO;
import mytunes.dal.UserDbDAO;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 *
 * @author Philip
 */
public class MTManager
{
UserDbDAO userDB = new UserDbDAO(); 
PlaylistDbDAO playlistDB = new PlaylistDbDAO();
SongDbDAO songDB = new SongDbDAO();

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

public List<Playlist> getPlaylists(int userID) throws IOException, SQLException
{
return playlistDB.getPlaylistsByUser(userID);
}

public void addPlaylist(int userID, String playlistName) throws IOException, SQLException
{
playlistDB.addPlaylist(userID, playlistName);
}


public void addSong(Song songToAdd) throws SQLException, SQLServerException, IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
{
songDB.addSong(songToAdd);
}

public void deletePlaylist (Playlist playlistToDelete) throws IOException, SQLException
{
    playlistDB.deletePlaylist(playlistToDelete);
}

public String getSecToMin(int time)
{
   String inMinutes= "";
   
   int inMin = time/60;
   int remainingSec = time-(inMin*60);
   
   if (remainingSec>=10){
   inMinutes=""+inMin+":"+remainingSec;
   
   return inMinutes;}
   
   inMinutes=""+inMin+":0"+remainingSec;
   
   return inMinutes;
   
}


}