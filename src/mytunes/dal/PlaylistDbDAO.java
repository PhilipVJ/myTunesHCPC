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
import java.util.ArrayList;
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
                addedPlaylist= new Playlist(generatedKeys.getInt(1), playlistName, userId);
                
                System.out.println("Following playlist has been added to the database: "+addedPlaylist.getName());
                return addedPlaylist;
                }

            }
 return addedPlaylist;
       
        
    }
    
    public List<Playlist> getPlaylistsByUser(int userID) throws IOException, SQLServerException, SQLException
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
       for (Playlist x : allPlaylist){
           System.out.println(""+x.getName());
       }
       return allPlaylist;
    }
    
    
    public void addSongToPlaylist(Song songToAdd)
    {

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
