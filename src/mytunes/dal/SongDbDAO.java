/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mytunes.be.Song;

/**
 *
 * @author Philip
 */
public class SongDbDAO {
        
        private DbConnection dbConnector;


    public List<Song> searchSongs(String keyword)
    {
        return null;
    }
    
    public void addSong(Song song) throws SQLServerException, SQLException
    {
        try (Connection con = dbConnector.getConnection()) {
            String sql = "INSERT INTO song" + "(name, artist,album,genre, path)"    //evt trackLength?
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement pStat = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStat.setString(1, song.getName());
            pStat.setString(2, song.getArtist());
            pStat.setString(3, song.getAlbum());
            pStat.setString(4, song.getGenre());
            pStat.setString(5, song.getPath());
            
            int affected = pStat.executeUpdate();
            if(affected < 1)
                throw new SQLException("Could not save song");
            
            
            //Ved ikke om dette fungerer, men det skulle generere id og sætte song id
            
            ResultSet rs = pStat.getGeneratedKeys();
            if (rs.next()) {
                song.setId(rs.getInt(1));
            }
        }
    }
    
    public void editSong(Song song) throws SQLServerException, SQLException
    {
        try (Connection con = dbConnector.getConnection()) {
            String sql = "UPDATE song SET" + "name=?, artist=?, album=?, genre=?, path=?"
                    + "WHERE songId=?";
            PreparedStatement pStat = con.prepareStatement(sql);
            pStat.setString(1, song.getName());
            pStat.setString(2, song.getArtist());
            pStat.setString(3, song.getAlbum());
            pStat.setString(4, song.getGenre());
            pStat.setString(5, song.getPath());
            pStat.setInt(6, song.getId());
            
            int affected = pStat.executeUpdate();
            if (affected < 1)
                throw new SQLException ("Can not edit song");
            }
        catch (SQLException exc) {
            Logger.getLogger(SongDbDAO.class.getName()).log(Level.SEVERE, null, exc);
        }
    }
    
    public void deleteSong()
    {
        
    }
    
    public List<Song> getAllSongs()
    {
        return null;
    }
    
    
}
