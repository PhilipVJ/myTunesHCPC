/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.be.User;
import mytunes.dal.PlaylistDbDAO;
import mytunes.dal.SongDbDAO;
import mytunes.dal.UserDbDAO;
import mytunes.dal.exception.DALException;

/**
 *
 * @author Philip
 * Connects dal to bll
 */
public class MTManager
{
    private UserDbDAO userDB = new UserDbDAO(); 
    private PlaylistDbDAO playlistDB = new PlaylistDbDAO();
    private SongDbDAO songDB = new SongDbDAO();

    /*
        Here starts User related methods.
    */
    public List<User> getAllUsers()
    {
        try
        {
            return userDB.getAllUsers();
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteUser(User userToDelete) 
    {   
        try
        {
            userDB.deleteUser(userToDelete);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User addUser(String username)
    {
        try
        {
            return userDB.addUser(username);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
        Here starts Playlist related methods.
    */
    public List<Playlist> getPlaylists(int userID) 
    {
        try
        {
            return playlistDB.getPlaylistsByUser(userID);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Playlist addPlaylist(int userID, String playlistName) 
    {
        try
        {
            return playlistDB.addPlaylist(userID, playlistName);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editPlaylist(int id, String newName) 
    {
        try
        {
            playlistDB.renamePlaylist(id, newName);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePlaylist (Playlist playlistToDelete) 
    {
        try
        {
            playlistDB.deletePlaylist(playlistToDelete);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Song> getPlaylistSongs(Playlist chosenPlaylist)
    {
        try
        {
            return playlistDB.getPlaylistSongs(chosenPlaylist);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void deleteSongFromPlaylist(Playlist chosenPlaylist, Song deleteSongs) 
    {
        try
        {
            playlistDB.deleteSongFromPlaylist(chosenPlaylist, deleteSongs);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSongToPlaylist(Song songToMove, Playlist playlistChosen)
    {
        try
        {
            playlistDB.addSongToPlaylist(songToMove, playlistChosen);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void moveSongUp(Playlist playlistChosen, Song songToMoveUp)
    {
        try
        {
            playlistDB.moveSongUp(playlistChosen, songToMoveUp);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void moveSongDown(Playlist playlistChosen, Song songToMoveDown)
    {
        try
        {
            playlistDB.moveSongDown(playlistChosen, songToMoveDown);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
        Here starts Song related methods.
    */
    public Song addSong(Song songToAdd) 
    {
        try
        {
            return songDB.addSong(songToAdd);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Song> getSongs() 
    {
        try
        {
            return songDB.getAllSongs();
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editSong(Song editedSong)
    {
        try
        {
            songDB.editSong(editedSong);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSongFromLibrary(Song songToDelete)
    {
        try
        {
            songDB.deleteSongFromLibrary(songToDelete);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Song> searchSong(String text) 
    {
        try
        {
            return songDB.searchSongs(text);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*
        This method converts seconds to a string format containing both minutes and seconds.
    */
    public static String getSecToMin(int time)
    {
        String inMinutes;
        int inMin = time/60;
        int remainingSec = time-(inMin*60);
        
        if (remainingSec>=10)
        {
            inMinutes=""+inMin+":"+remainingSec;
            return inMinutes;
        }
        inMinutes=""+inMin+":0"+remainingSec;   
        return inMinutes;
    }
    
    /*
        This method does the opposite. It takes a formatted string and translates it into seconds again.
    */
    public static int getMinToSec(String time)
    {
        String timeWork = time;
        String[] timeInTwo;
        timeInTwo= timeWork.split(":");
        String p1 = timeInTwo[0];
        String p2 = timeInTwo[1];
        int numberp1 = Integer.parseInt(p1);
        int numberp2 = Integer.parseInt(p2);
    
        int total = numberp1*60;
        int totalup = total+numberp2;
    
        return totalup; 
    }   

    public void deleteSongFromHardDisk(String filepath)
    {
        try
        {
            songDB.deleteSongFromHardDisk(filepath);
        } 
        catch (DALException ex)
        {
            Logger.getLogger(MTManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}