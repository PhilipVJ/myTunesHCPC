/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Playlist;
import mytunes.be.Song;
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
    private ObservableList<Song> songs;
    private ObservableList<Song> playlistSongs;
    private MTManager mtmanager;


    public MTModel()
    {
        mtmanager = new MTManager();
    }
    
    /*
        Here starts User related methods.
    */
    public ObservableList<User> getUsers() 
    {
        users = FXCollections.observableList(mtmanager.getAllUsers());
        return users;
    }

    public void deleteUser(User userToDelete) 
    {
        mtmanager.deleteUser(userToDelete);
        List<User> allUsers = users;

        for (User x: allUsers)
        {
            if (x.getID()==userToDelete.getID())
            {
                allUsers.remove(x);
                return;
            }
        }
    }

    public void addUser(String username) 
    {
        users.add(mtmanager.addUser(username));
    }

    /*
        Here starts Playlist related methods.
    */
    public ObservableList<Playlist> getPlaylists(int userID)
    {
        playlists = FXCollections.observableList(mtmanager.getPlaylists(userID));
        return playlists;
    }

    public void addPlaylist(int userID, String playlistName) 
    {        
        playlists.add(mtmanager.addPlaylist(userID, playlistName));
    }
    
    public void deletePlaylist(Playlist playlistToDelete) 
    {
        mtmanager.deletePlaylist(playlistToDelete);
        for (Playlist x:playlists)
        {
            if (x.getId()==playlistToDelete.getId())
            {
                playlists.remove(x);
                break;
            }
        }
        playlistSongs.clear();
    }
    
    public ObservableList<Song> getPlaylistSongs(Playlist chosenPlaylist)
    {
        playlistSongs = FXCollections.observableList(mtmanager.getPlaylistSongs(chosenPlaylist));
        return playlistSongs;
    }
    
    public void deleteSongFromPlaylist(Playlist chosenPlaylist, Song songToDelete)
    {
        mtmanager.deleteSongFromPlaylist(chosenPlaylist,songToDelete);
        for (Song x:playlistSongs)
        {
            if (x.getId()==songToDelete.getId())
            {
                playlistSongs.remove(x);
                break;
            }
        }    
    }

    public void addSongToPlaylist(Song songToMove, Playlist playlistChosen) 
    {
        mtmanager.addSongToPlaylist(songToMove,playlistChosen);
    }

    public void editPlaylist(int id, String newName) 
    {
        mtmanager.editPlaylist(id, newName); 
        
        for (Playlist x:playlists)
        {
            if (x.getId()==id)
            {
                x.setName(newName);
                break;
            }
        }
    }
    
    public void moveSongUp(Playlist playlistChosen, Song songToMoveUp)
    {
        mtmanager.moveSongUp(playlistChosen, songToMoveUp);
    }

    public void moveSongDown(Playlist playlistChosen, Song songToMoveDown)
    {
        mtmanager.moveSongDown(playlistChosen, songToMoveDown);
    }
    
    
    /*
        Here starts Song related methods.
    */
    public void addSong(Song songToAdd)
    {
        songs.add(mtmanager.addSong(songToAdd));
    }

    public ObservableList<Song> getSongs() 
    {
        songs = FXCollections.observableList(mtmanager.getSongs());
        return songs;
    }

    public void editSong(Song editedSong)
    {
        mtmanager.editSong(editedSong);
        for (Song x:songs)
        {
            if (x.getId()==editedSong.getId())
            {
                x.setArtist(editedSong.getArtist());
                x.setTitle(editedSong.getTitle());
                x.setGenre(editedSong.getGenre());
                break;
            }
        }
    }
    
    public void deleteSongFromLibrary(Song songToDelete)
    {
        mtmanager.deleteSongFromLibrary(songToDelete);
        for (Song x:songs)
        {
            if(x.getId()==songToDelete.getId())
            {
                songs.remove(x);
                break;
            }
        }
        
        for (Song x:playlistSongs)
        {
            if(x.getId()==songToDelete.getId())
            {
                playlistSongs.remove(x);
                break;
            }    
        }
    }
    
    /*
        Converts seconds to minutes
    */
    public String getSecToMin(int time)
    {
        return mtmanager.getSecToMin(time);
    }
    
    /*
        An observablelist to search for songs.
    */
    public ObservableList<Song> searchSong(String text)
    {
        ObservableList<Song> searchedSongs = FXCollections.observableList(mtmanager.searchSong(text));
        return searchedSongs;
    }
}
