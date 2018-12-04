/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.be.User;
import mytunes.bll.MTManager;
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


public class MTModel

{
private ObservableList<User> users;
private ObservableList<Playlist> playlists;
private ObservableList<Song> songs;
private ObservableList<Song> playlistSongs;


private MTManager mtmanager;


public MTModel() throws IOException, SQLException
{
mtmanager = new MTManager();

}

public ObservableList<User> getUsers() throws IOException, SQLException
{
users = FXCollections.observableList(mtmanager.getAllUsers());

return users;

}

public void deleteUser(User userToDelete) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
{
mtmanager.deleteUser(userToDelete);
List<User> allUsers = users;

for (User x: allUsers)
{
    if (x.getID()==userToDelete.getID()){
        allUsers.remove(x);
        return;
    }
    
}

}

public void addUser(String username) throws IOException, SQLException
{
users.add(mtmanager.addUser(username));


}

public ObservableList<Playlist> getPlaylists(int userID) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
{
playlists = FXCollections.observableList(mtmanager.getPlaylists(userID));
return playlists;
}

public void addPlaylist(int userID, String playlistName) throws IOException, SQLException
    {        
        mtmanager.addPlaylist(userID, playlistName);
    }
    
public void addSong(Song songToAdd) throws IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException, SQLException
{
    mtmanager.addSong(songToAdd);
}


public void deletePlaylist(Playlist playlistToDelete) throws IOException, SQLException
{
    mtmanager.deletePlaylist(playlistToDelete);
}

public String getSecToMin(int time)
{
   return mtmanager.getSecToMin(time);
}



public ObservableList<Song> getSongs() throws IOException, SQLException
{
    songs = FXCollections.observableList(mtmanager.getSongs());
    return songs;
}



public void editSong(Song editedSong) throws SQLException, SQLServerException, IOException
{
mtmanager.editSong(editedSong);
}


public ObservableList<Song> getPlaylistSongs(Playlist chosenPlaylist) throws IOException, SQLException
{
playlistSongs = FXCollections.observableList(mtmanager.getPlaylistSongs(chosenPlaylist));
return playlistSongs;
       

}

public void deleteSongFromPlaylist(Playlist chosenPlaylist, Song songToDelete) throws IOException, SQLException
{
    mtmanager.deleteSongFromPlaylist(chosenPlaylist,songToDelete);
}

public void addSongToPlaylist(Song songToMove, Playlist playlistChosen) throws IOException, SQLException
{
 mtmanager.addSongToPlaylist(songToMove,playlistChosen);
}

public void deleteSongFromLibrary(Song songToDelete) throws IOException, SQLException
{
mtmanager.deleteSongFromLibrary(songToDelete);

}



    public void editPlaylist(int id, String newName) throws IOException, SQLException
    {
        mtmanager.editPlaylist(id, newName);
    }

public void moveSongUp(Playlist playlistChosen, Song songToMoveUp) throws IOException, SQLException
{
     mtmanager.moveSongUp(playlistChosen, songToMoveUp);
}

    public void moveSongDown(Playlist playlistChosen, Song songToMoveDown) throws IOException, SQLException
    {
       mtmanager.moveSongDown(playlistChosen, songToMoveDown);
    }

    public ObservableList<Song> searchSong(String text) throws IOException, SQLException
    {
      ObservableList<Song> searchedSongs = FXCollections.observableList(mtmanager.searchSong(text));
        return searchedSongs;
    }
}
