/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;


import java.net.URL;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mytunes.be.Song;
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
public class SongDbDAO {
        
       


    public List<Song> searchSongs(String keyword)
    {
        return null;
    }
    
    public void addSong(File mediaFile) throws SQLServerException, SQLException, IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
    MP3File mp3file = new MP3File(mediaFile);
    System.out.println(""+mp3file.hasID3v2Tag());
    AbstractID3v2 ID3 = mp3file.getID3v2Tag();
    String artist = ID3.getLeadArtist();
    String title = ID3.getSongTitle();
    String genre = ID3.getSongGenre();
     String path = mediaFile.toURI().toString();
        
      
       
    
    int duration = 0;
    AudioFile audioFile = AudioFileIO.read(mediaFile);
    duration = audioFile.getAudioHeader().getTrackLength(); 
    DbConnection dbCon = new DbConnection();
    
   
     
        
        try (Connection con = dbCon.getConnection()) {
            String SQL = "INSERT INTO Songs VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,title);
            pstmt.setString(2,path);
            pstmt.setString(3,artist);
            pstmt.setString(4,genre);
            pstmt.setInt(5,duration);
            System.out.println("Ready to execute");
            pstmt.execute();
            
        }
    }
    
    public void editSong(Song song) throws SQLServerException, SQLException
    {
//        try (Connection con = dbConnector.getConnection()) {
//            String sql = "UPDATE song SET" + "name=?, artist=?, album=?, genre=?, path=?"
//                    + "WHERE songId=?";
//            PreparedStatement pStat = con.prepareStatement(sql);
//            pStat.setString(1, song.getName());
//            pStat.setString(2, song.getArtist());
//            pStat.setString(3, song.getAlbum());
//            pStat.setString(4, song.getGenre());
//            pStat.setString(5, song.getPath());
//            pStat.setInt(6, song.getId());
//            
//            int affected = pStat.executeUpdate();
//            if (affected < 1)
//                throw new SQLException ("Can not edit song");
//            }
//        catch (SQLException exc) {
//            Logger.getLogger(SongDbDAO.class.getName()).log(Level.SEVERE, null, exc);
//        }
    }
    
    public void deleteSong()
    {
        
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
         int time = rs.getInt("Time");
         int id = rs.getInt("SongID");
        
        allSongs.add(new Song(artist, title, genre, path, id, time));
      }
                    
        return allSongs;
    }
    

    
}
