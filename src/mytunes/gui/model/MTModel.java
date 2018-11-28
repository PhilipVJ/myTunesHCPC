/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.User;
import mytunes.bll.MTManager;

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

    
}
