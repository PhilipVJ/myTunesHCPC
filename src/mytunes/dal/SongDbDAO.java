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

import mytunes.be.Song;
import org.farng.mp3.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 *
 * @author Philip
 */
public class SongDbDAO 
{
  /**
   * This method returns a list of song objects which contains the searchword.
   * @param keyword
   * @return
   * @throws IOException
   * @throws SQLServerException
   * @throws SQLException 
   */      
    public List<Song> searchSongs(String keyword) throws IOException, SQLServerException, SQLException
    {
        ArrayList<Song> searchedSongs = new ArrayList();
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        Statement statement = con.createStatement();
        PreparedStatement pstmt = con.prepareStatement("Select * FROM Songs WHERE Artist LIKE ? OR Title LIKE ?");
        pstmt.setString(1, "%"+keyword+"%");
        pstmt.setString(2, "%"+keyword+"%");
        ResultSet rs = pstmt.executeQuery();
      
        while (rs.next())
        {
            String title = rs.getString("Title");
            String path = rs.getString("Filepath");
            String artist = rs.getString("Artist");
            String genre = rs.getString("Genre");
            String time = rs.getString("Time");
            int id = rs.getInt("SongID");
            Song searchedSong=new Song(artist, title, genre, path, id, time);
            searchedSongs.add(searchedSong); 
        }
        return searchedSongs;
    }
    
    public Song addSong(Song songToAdd) throws SQLServerException, SQLException, IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        String artist = songToAdd.getArtist();
        String title = songToAdd.getTitle();
        String genre = songToAdd.getGenre();
        String path = songToAdd.getFilepath();
        String time = songToAdd.getTime();
        Song newSong=null;
        

        DbConnection dbCon = new DbConnection();

        
        try (Connection con = dbCon.getConnection()) 
        {
            
            String SQL = "INSERT INTO Songs VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,title);
            pstmt.setString(2,path);
            pstmt.setString(3,artist);
            pstmt.setString(4,genre);
            pstmt.setString(5,time);
            
            pstmt.execute();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys(); 
            {
            if (generatedKeys.next()) 
                {
                newSong= new Song(artist, title, genre, path, generatedKeys.getInt(1), time);
                
                
                return newSong;
                }
            }
        }
    return newSong;
    }
    
    public void editSong(Song song) throws SQLServerException, SQLException, IOException
    {
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
   
        try (PreparedStatement pstmt = con.prepareStatement("UPDATE Songs SET Title = (?), Artist = (?), Genre = (?) WHERE songId = (?)"))
        {
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setString(3, song.getGenre());
            pstmt.setInt(4, song.getId());
            pstmt.execute();
        }
    }

    public void deleteSongFromLibrary(Song songToDelete) throws IOException, SQLServerException, SQLException
    {
        int songID = songToDelete.getId();

        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        
        try (PreparedStatement pstmt = con.prepareStatement("DELETE FROM Songs WHERE songID=(?)"))
        {
            pstmt.setInt(1,songID);
            pstmt.execute();
        }
        System.out.println("Following song has been deleted: "+songID); 
    }
    
    public ArrayList<Song> getAllSongs() throws IOException, SQLServerException, SQLException
    {
        ArrayList<Song> allSongs = new ArrayList<>();
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("Select * FROM Songs;");
        while (rs.next())
        {
            String title = rs.getString("Title");
            String path = rs.getString("Filepath");
            String artist = rs.getString("Artist");
            String genre = rs.getString("Genre");
            String time = rs.getString("Time");
            int id = rs.getInt("SongID");
        
            allSongs.add(new Song(artist, title, genre, path, id, time));
        }
                    
        return allSongs;
    }
    
    public Song getSong(int songID) throws SQLException, IOException
    {
        Song songToGet=null;
        DbConnection dc = new DbConnection();
        Connection con = dc.getConnection();
        
        Statement statement = con.createStatement();
        PreparedStatement pstmt = con.prepareStatement("Select * FROM Songs WHERE songID= (?)");
        pstmt.setInt(1, songID);
        ResultSet rs = pstmt.executeQuery();
      
        while (rs.next())
        {
         String title = rs.getString("Title");
         String path = rs.getString("Filepath");
         String artist = rs.getString("Artist");
         String genre = rs.getString("Genre");
         String time = rs.getString("Time");
         int id = rs.getInt("SongID");
         songToGet=new Song(artist, title, genre, path, id, time);     
        }
      return songToGet;
    } 
}
