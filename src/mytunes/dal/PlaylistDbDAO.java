/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
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
public class PlaylistDbDAO
{
    
    public Playlist addPlaylist(int userId, String playlistName) throws IOException, SQLServerException, SQLException
    {
        DbConnection ds = new DbConnection();
        Connection con = ds.getConnection();
        Playlist addedPlaylist = null;
        
        String SQL = "INSERT INTO Playlist VALUES (?, ?)";
        PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1,userId);
        pstmt.setString(2,playlistName);
        pstmt.execute();
  
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) 
            {
            if (generatedKeys.next()) 
                {
                addedPlaylist= new Playlist(generatedKeys.getInt(1), playlistName, userId);
                
                System.out.println("Following playlist has been added to the database: "+addedPlaylist.getName());
                return addedPlaylist;
                }

            }
 return addedPlaylist;
       
        
    }
    
    public List<Playlist> getPlaylistsByUser(int userID) throws IOException, SQLServerException, SQLException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
       ArrayList<Playlist> allPlaylist = new ArrayList<>();
       DbConnection dc = new DbConnection();
       Connection con = dc.getConnection();
        

            Statement statement = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement
            ("Select * FROM Playlist WHERE userId = (?)");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
         
            while (rs.next())
            {
            int playlistID = rs.getInt("listId");
            String PlaylistName = rs.getString("playlistName");
            allPlaylist.add(new Playlist(playlistID, PlaylistName, userID));
             
                
            }
// Add length in seconds to playlist


for (Playlist x : allPlaylist){
    List<Song> allSongs = getPlaylistSongs(x);
    int duration = 0;
    
    for (Song y: allSongs){
    int time = MTManager.getMinToSec(y.getTime());
   duration+=time;
   
    }
    x.addLengthInSeconds(duration);
}
    
      
        
 return allPlaylist;
    }
    public void addSongToPlaylist(Song songToAdd, Playlist chosenPlaylist) throws IOException, SQLServerException, SQLException
    {
    DbConnection dc = new DbConnection();
    Connection con = dc.getConnection();
    
  
    
    Statement statement = con.createStatement();
    String SQL = "INSERT INTO PlaylistContent VALUES (?, ?, ?)";
    PreparedStatement pstmt = con.prepareStatement(SQL);
    pstmt.setInt(1, chosenPlaylist.getId());
    pstmt.setInt(2, songToAdd.getId());
    pstmt.setInt(3, getNextSongPosition(chosenPlaylist));
    System.out.println("Ready to execute");
    pstmt.execute();

    }
    
    public void renamePlaylist(int playlistID, String newName) throws IOException, SQLServerException, SQLException
    {
       
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            Statement statement = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement
            ("UPDATE Playlist SET playlistName = (?) WHERE listId = (?)");
            pstmt.setString(1, newName);
            pstmt.setInt(2, playlistID);
            pstmt.execute();
            pstmt.close();
                            
             
 
             
    }
    

    
    public void deletePlaylist(Playlist playlistToDelete) throws IOException, SQLServerException, SQLException
    {
        int playlistId = playlistToDelete.getId();

        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Playlist WHERE listId=(?)");
        pstmt.setInt(1,playlistId);
        pstmt.execute();
        pstmt.close();
        System.out.println("Following playlist has been deleted: "+playlistId);
    }
    
    public List<Song> getPlaylistSongs(Playlist playList) throws IOException, SQLServerException, SQLException
    {
        SongDbDAO songDB = new SongDbDAO();
        
        ArrayList<Song> playlistSongs = new ArrayList<>();
        int playlistId = playList.getId();
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        PreparedStatement pstmt = con.prepareStatement
         ("Select * FROM PlaylistContent WHERE playlistId = (?)");
        pstmt.setInt(1,playlistId);
        ResultSet rs = pstmt.executeQuery();
         
            while (rs.next())
            {
            int SongID = rs.getInt("songID");
            int SongPosition = rs.getInt("songPosition");
           Song songToAdd = songDB.getSong(SongID);
           songToAdd.setPosition(SongPosition);
           playlistSongs.add(songToAdd);

            }
      playlistSongs.sort( Comparator.comparing( Song::getPosition ) ); 
      return playlistSongs;
  
        
    }

    private int getNextSongPosition(Playlist chosenPlaylist) throws IOException, SQLException
    {
        int nextId=1;
        List<Song> allSongsFromPlaylist = getPlaylistSongs(chosenPlaylist);

        for (Song x : allSongsFromPlaylist)
        {
                       
            if (x.getPosition()==nextId)
            {
                nextId++;
            }
        }
        System.out.println(""+nextId);
        return nextId;  // Metoden her kan sagtens gøres kortere. Den skal bare returnere den position i rækken. Der er ikke længere huller den skal tjekke.
    }
    
    public void deleteSongFromPlaylist(Playlist chosenPlaylist, Song songToDelete) throws IOException, SQLServerException, SQLException
    {
        int songID = songToDelete.getId();
        int position = songToDelete.getPosition();
        int playlistID = chosenPlaylist.getId();
        int playlistSize = getPlaylistSongs(chosenPlaylist).size();
        
        
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        PreparedStatement pstmt = con.prepareStatement("DELETE FROM PlaylistContent WHERE songID=(?) AND playlistId=(?) AND songPosition=(?)");
        pstmt.setInt(1,songID);
        pstmt.setInt(2,playlistID);
        pstmt.setInt(3,position);
        pstmt.execute();
        pstmt.close();
        System.out.println("Following song has been deleted: "+songID);
        fixSongPositionsAfterDeletion(playlistSize,playlistID, position);
    }
    
    private void fixSongPositionsAfterDeletion(int playlistSize, int playlistId, int positionOfDeleted) throws IOException, SQLServerException, SQLException
    {
            int position = positionOfDeleted;
            int plSize = playlistSize;
            int playId = playlistId;
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
                      
            
            for (int i=position+1;i<=plSize;i++){
            System.out.println("Working");
            PreparedStatement pstmt = con.prepareStatement
            ("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?)");
            pstmt.setInt(1, i-1);
            pstmt.setInt(2, playId);
            pstmt.setInt(3, i);
            pstmt.execute();
            pstmt.close();
            }
        
    }

    public void moveSongUp(Playlist playlistChosen, Song songToMoveUp) throws IOException, SQLException
    {
       int playlistID = playlistChosen.getId();
       int songPosition = songToMoveUp.getPosition();
       int songID = songToMoveUp.getId();
       
       DbConnection dc = new DbConnection();
       Connection con = dc.getConnection();
       
       PreparedStatement pstmt2 = con.prepareStatement
       ("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?)");
       pstmt2.setInt(1, songPosition);
       pstmt2.setInt(2, playlistID);
       pstmt2.setInt(3, songPosition-1);
       pstmt2.execute();
       
    
       PreparedStatement pstmt = con.prepareStatement
       ("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?) AND songID=(?)");
       pstmt.setInt(1, songPosition-1);
       pstmt.setInt(2, playlistID);
       pstmt.setInt(3, songPosition);
       pstmt.setInt(4, songID);
       pstmt.execute();
       
       
       
       
    }

    public void moveSongDown(Playlist playlistChosen, Song songToMoveDown) throws IOException, SQLServerException, SQLException
    {
       int playlistID = playlistChosen.getId();
       int songPosition = songToMoveDown.getPosition();
       int songID = songToMoveDown.getId();
       
       DbConnection dc = new DbConnection();
       Connection con = dc.getConnection();
       
       PreparedStatement pstmt2 = con.prepareStatement
       ("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?)");
       pstmt2.setInt(1, songPosition);
       pstmt2.setInt(2, playlistID);
       pstmt2.setInt(3, songPosition+1);
       pstmt2.execute();
       
    
       PreparedStatement pstmt = con.prepareStatement
       ("UPDATE PlaylistContent SET songPosition = (?) WHERE playlistId = (?) AND songPosition=(?) AND songID=(?)");
       pstmt.setInt(1, songPosition+1);
       pstmt.setInt(2, playlistID);
       pstmt.setInt(3, songPosition);
       pstmt.setInt(4, songID);
       pstmt.execute();
       
    }
    
}
