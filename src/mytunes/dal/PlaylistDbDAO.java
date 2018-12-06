/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.MTManager;
import mytunes.dal.exception.DALException;



/**
 *
 * @author Philip
 */
public class PlaylistDbDAO
{
    /**
     * Adds a playlist in database with given userId and playlistName
     * @param userId
     * @param playlistName
     * @return
     * @throws DALException 
     */
    public Playlist addPlaylist(int userId, String playlistName) throws DALException
    { 
        try
        {
            DbConnection ds = new DbConnection();
            Connection con = ds.getConnection();
            Playlist addedPlaylist = null;
            
            String SQL = "INSERT INTO Playlist VALUES (?, ?)"; 
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,userId);
            pstmt.setString(2,playlistName);
            pstmt.execute();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            
            if (generatedKeys.next())
            {
                addedPlaylist= new Playlist(generatedKeys.getInt(1), playlistName);
                System.out.println("Following playlist has been added to the database: "+addedPlaylist.getPlaylistName());
                return addedPlaylist;
            }
        return addedPlaylist; 
        } 
        catch ( IOException |SQLException ex)
        {
            throw new DALException("Could not add playlist", ex);
        }   
    }
    
    
    /**
     * Returns all playlists by the given user ID from the database.
     * @param userID
     * @return 
     * @throws mytunes.dal.exception.DALException 
     */
    public List<Playlist> getPlaylistsByUser(int userID) throws DALException
    {
    
        try
        {
            ArrayList<Playlist> allPlaylist = new ArrayList<>();
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            PreparedStatement pstmt = con.prepareStatement("Select * FROM Playlist WHERE userId = (?)");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next())
            {
                int playlistID = rs.getInt("listId");
                String PlaylistName = rs.getString("playlistName");
                allPlaylist.add(new Playlist(playlistID, PlaylistName));
            }
            // Add length in seconds to playlist
            for (Playlist x : allPlaylist)
            {
                List<Song> allSongs = getPlaylistSongs(x);
                int duration = 0;
                
                for (Song y: allSongs)
                {
                    if(!"error".equals(y.getFilepath())){
                        int time = MTManager.getMinToSec(y.getTime());
                        duration+=time;}
                }
                x.addLengthInSeconds(duration);
            }
        return allPlaylist;
        } 
        catch (IOException | SQLException ex)
        {
            throw new DALException("Could not get playlists", ex);
        }
    }
    
    /**
     * Adds a song to the choosen playlist
     * @param songToAdd
     * @param chosenPlaylist
     * @throws DALException 
     */
    public void addSongToPlaylist(Song songToAdd, Playlist chosenPlaylist) throws DALException 
    {
        try
        {
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            String SQL = "INSERT INTO PlaylistContent VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, chosenPlaylist.getId());
            pstmt.setInt(2, songToAdd.getId());
            pstmt.setInt(3, getPlaylistSongs(chosenPlaylist).size()+1);
            pstmt.execute();
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not add song to playlist", ex);
        }
    }
    
    /**
     * This method sets a new name to the specified playlist.
     * @param playlistID
     * @param newName
     * @throws DALException 
     */
    public void renamePlaylist(int playlistID, String newName) throws DALException
    {
        try
        {
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Playlist SET playlistName = (?) WHERE listId = (?)");
            pstmt.setString(1, newName);
            pstmt.setInt(2, playlistID);
            pstmt.execute();
            pstmt.close();
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not rename playlist", ex);
        }
    }    
    
    
    /**
     * This method deletes the playlist and all its content from the database.
     * @param playlistToDelete
     * @throws mytunes.dal.exception.DALException
     */
    public void deletePlaylist(Playlist playlistToDelete) throws DALException
    {
        try
        {
            int playlistId = playlistToDelete.getId();
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            PreparedStatement pstmt2 = con.prepareStatement("DELETE FROM PlaylistContent WHERE playlistID=(?)");
            pstmt2.setInt(1, playlistId);
            pstmt2.execute();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Playlist WHERE listId=(?)");
            pstmt.setInt(1,playlistId);
            pstmt.execute();
            System.out.println("Following playlist has been deleted: "+playlistId);
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not delete playlist", ex);
        }
    }
    
    
    /**
     * Returns the songs in the given playlist
     * @param playList
     * @return
     * @throws DALException 
     */
    public List<Song> getPlaylistSongs(Playlist playList) throws DALException
    {
        try
        {
            SongDbDAO songDB = new SongDbDAO();
            
            ArrayList<Song> playlistSongs = new ArrayList<>();
            int playlistId = playList.getId();
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            PreparedStatement pstmt = con.prepareStatement("Select * FROM PlaylistContent WHERE playlistId = (?)");
            pstmt.setInt(1,playlistId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next())
            {
                int SongID = rs.getInt("songID");
                int SongPosition = rs.getInt("songPosition");
                Song songToAdd = songDB.getSong(SongID);
                
                if(songToAdd!=null)
                {
                    songToAdd.setPosition(SongPosition);
                    playlistSongs.add(songToAdd);
                }
                else
                {
                    Song fileHasBeenDeleted = new Song("File has been deleted", "", "","error", SongID, "");
                    fileHasBeenDeleted.setPosition(SongPosition);
                    playlistSongs.add(fileHasBeenDeleted);
                    
                }
                
            }
            playlistSongs.sort( Comparator.comparing( Song::getPosition ) );
            return playlistSongs;
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not get playlist songs", ex);
        }
    }

    /**
     * Deletes only the song in the given playlist
     * @param chosenPlaylist
     * @param songToDelete
     * @throws DALException 
     */
    public void deleteSongFromPlaylist(Playlist chosenPlaylist, Song songToDelete) throws DALException 
    {
        try 
        {
            int songID = songToDelete.getId();
            int position = songToDelete.getPosition();
            int playlistID = chosenPlaylist.getId();
            int playlistSize = getPlaylistSongs(chosenPlaylist).size();
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            try (PreparedStatement pstmt = con.prepareStatement("DELETE FROM PlaylistContent WHERE songID=(?) AND playlistId=(?) AND songPosition=(?)"))
            {
                pstmt.setInt(1,songID);
                pstmt.setInt(2,playlistID);
                pstmt.setInt(3,position);
                pstmt.execute();
            }
            
            System.out.println("Following song has been deleted: "+songID);
            fixSongPositionsAfterDeletion(playlistSize,playlistID, position);
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not delete song from playlist", ex);
        }
    }
    
    
    /**
     * Each song on a playlist has an position ID. This method makes sure no gaps appear between the ID's. 
     * 
     * @param playlistSize
     * @param playlistId
     * @param positionOfDeleted
     */
    private void fixSongPositionsAfterDeletion(int playlistSize, int playlistId, int positionOfDeleted) throws DALException 
    {
        try
        {
            int position = positionOfDeleted;
            int plSize = playlistSize;
            int playId = playlistId;
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            for (int i=position+1;i<=plSize;i++)
            {
                PreparedStatement pstmt = con.prepareStatement("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?)");
                pstmt.setInt(1, i-1);
                pstmt.setInt(2, playId);
                pstmt.setInt(3, i);
                pstmt.execute();
            }
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not fit song position", ex);
        } 
    }
    
    
    /**
     * When you move a song up the playlist the song chosen should switch ID with the song above it. This method does just that.
     * @param playlistChosen
     * @param songToMoveUp
     * @throws mytunes.dal.exception.DALException
     */
    public void moveSongUp(Playlist playlistChosen, Song songToMoveUp) throws DALException 
    {
        try
        {
            int playlistID = playlistChosen.getId();
            int songPosition = songToMoveUp.getPosition();
            int songID = songToMoveUp.getId();
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            PreparedStatement pstmt2 = con.prepareStatement("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?)");
            pstmt2.setInt(1, songPosition);
            pstmt2.setInt(2, playlistID);
            pstmt2.setInt(3, songPosition-1);
            pstmt2.execute();
            
            PreparedStatement pstmt = con.prepareStatement("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?) AND songID=(?)");
            pstmt.setInt(1, songPosition-1);
            pstmt.setInt(2, playlistID);
            pstmt.setInt(3, songPosition);    
            pstmt.setInt(4, songID);
            pstmt.execute();
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not move song up", ex);
        }
    }
    
    
    /**
    * When you move a song down the playlist it should switch position ID with the song beneath it. This method does just that. 
    * @param playlistChosen
    * @param songToMoveDown
    * @throws mytunes.dal.exception.DALException
    */
    public void moveSongDown(Playlist playlistChosen, Song songToMoveDown) throws DALException
    {
        try
        {
            int playlistID = playlistChosen.getId();
            int songPosition = songToMoveDown.getPosition();
            int songID = songToMoveDown.getId();
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            PreparedStatement pstmt2 = con.prepareStatement("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?)");
            pstmt2.setInt(1, songPosition);
            pstmt2.setInt(2, playlistID);
            pstmt2.setInt(3, songPosition+1);
            pstmt2.execute();
            
            PreparedStatement pstmt = con.prepareStatement("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?) AND songID=(?)");
            pstmt.setInt(1, songPosition+1);
            pstmt.setInt(2, playlistID);
            pstmt.setInt(3, songPosition);
            pstmt.setInt(4, songID);
            pstmt.execute();
        } 
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not move song down", ex);
        }
    }
}
