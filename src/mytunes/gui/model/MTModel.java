/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
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

public void deleteUser(User userToDelete) throws IOException, SQLException
{
mtmanager.deleteUser(userToDelete); 

}

public void addUser(String username) throws IOException, SQLException
{
mtmanager.addUser(username);

}

public ObservableList<Playlist> getPlaylists(int userID) throws IOException, SQLException
{
playlists = FXCollections.observableList(mtmanager.getPlaylists(userID));
return playlists;
}

public void addPlaylist(int userID, String playlistName) throws IOException, SQLException
    {        
        mtmanager.addPlaylist(userID, playlistName);
    }
    
public void addSong(File mediaFile) throws IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException, SQLException
{
    mtmanager.addSong(mediaFile);
}


public void deletePlaylist(Playlist playlistToDelete) throws IOException, SQLException
{
    mtmanager.deletePlaylist(playlistToDelete);
}
}
