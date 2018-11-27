/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.Song;


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
                addedPlaylist= new Playlist(generatedKeys.getInt(1), playlistName);
                
                System.out.println("Following playlist has been added to the database: "+addedPlaylist.getName());
                return addedPlaylist;
                }

            }
 return addedPlaylist;
       
        
    }
    
    public List<Playlist> getAllPlayLists()
    {
        return null;
    }
    
    public void addSongToPlaylist(Song songToAdd)
    {
        
    }
    
    public void deleteSongFromPlaylist(Song songToDelete)
    {
        
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
    
    
    
}
