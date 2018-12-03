/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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

public List<Playlist> getPlaylists(int userID) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
{
return playlistDB.getPlaylistsByUser(userID);
}

public Playlist addPlaylist(int userID, String playlistName) throws IOException, SQLException
{
return playlistDB.addPlaylist(userID, playlistName);

}

public void editPlaylist(int id, String newName) throws IOException, SQLException
{
    playlistDB.renamePlaylist(id, newName);
}

public void addSong(Song songToAdd) throws SQLException, SQLServerException, IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
{
songDB.addSong(songToAdd);
}

public void deletePlaylist (Playlist playlistToDelete) throws IOException, SQLException
{
    playlistDB.deletePlaylist(playlistToDelete);
}

public static String getSecToMin(int time)
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

public static int getMinToSec(String time){
    String timeWork = time;
    String[] timeInTwo = new String[2];
    timeInTwo= timeWork.split(":");
    String p1 = timeInTwo[0];
    String p2 = timeInTwo[1];
    int numberp1 = Integer.parseInt(p1);
    int numberp2 = Integer.parseInt(p2);
    
    int total = numberp1*60;
    int totalup = total+numberp2;
   
    
    return totalup;
    
}

public List<Song> getSongs() throws IOException, SQLException
{
    return songDB.getAllSongs();
}

public void editSong(Song editedSong) throws SQLException, SQLServerException, IOException
{
songDB.editSong(editedSong);
}


public List<Song> getPlaylistSongs(Playlist chosenPlaylist) throws IOException, SQLException
{
return playlistDB.getPlaylistSongs(chosenPlaylist);
}


public void deleteSongFromPlaylist(Playlist chosenPlaylist, Song deleteSongs) throws IOException, SQLException
{
    playlistDB.deleteSongFromPlaylist(chosenPlaylist, deleteSongs);
    
}

public void addSongToPlaylist(Song songToMove, Playlist playlistChosen) throws IOException, SQLException
{
  playlistDB.addSongToPlaylist(songToMove, playlistChosen);
}

public void deleteSongFromLibrary(Song songToDelete) throws IOException, SQLException
{
  songDB.deleteSongFromLibrary(songToDelete);
}

public void moveSongUp(Playlist playlistChosen, Song songToMoveUp) throws IOException, SQLException
{
playlistDB.moveSongUp(playlistChosen, songToMoveUp);
}

public void moveSongDown(Playlist playlistChosen, Song songToMoveDown) throws IOException, SQLException
    {
     playlistDB.moveSongDown(playlistChosen, songToMoveDown);
    }

    public List<Song> searchSong(String text) throws IOException, SQLException
    {
    return songDB.searchSongs(text);
    }

 
}