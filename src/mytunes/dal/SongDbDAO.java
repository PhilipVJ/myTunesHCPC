/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;



import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mytunes.be.Song;
import mytunes.dal.exception.DALException;

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
   * @throws mytunes.dal.exception.DALException
   */      
    public List<Song> searchSongs(String keyword) throws DALException
    {
        try
        {
            ArrayList<Song> searchedSongs = new ArrayList();
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
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
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not get search results", ex);
        }
    }
    
    
    /**
     * Adds a song to the database
     * @param songToAdd
     * @return
     * @throws DALException 
     */
    public Song addSong(Song songToAdd) throws DALException 
    {
        try
        {
            String artist = songToAdd.getArtist();
            String title = songToAdd.getTitle();
            String genre = songToAdd.getGenre();
            String path = songToAdd.getFilepath();
            String time = songToAdd.getTime();
            Song newSong=null;
            DbConnection dbCon = new DbConnection();
            Connection con = dbCon.getConnection();
            String SQL = "INSERT INTO Songs VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,title);
            pstmt.setString(2,path);
            pstmt.setString(3,artist);
            pstmt.setString(4,genre);
            pstmt.setString(5,time);
            pstmt.execute(); 
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next())
            {
                newSong= new Song(artist, title, genre, path, generatedKeys.getInt(1), time);
                return newSong;
            }
            return newSong;
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not add song", ex);
        }
    }
    
    
    /**
     * Edits a songs Title, Artist or Genre with it's given songID
     * @param song
     * @throws DALException 
     */
    public void editSong(Song song) throws DALException
    {
        try
        {
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            PreparedStatement pstmt = con.prepareStatement("UPDATE Songs SET Title = (?), Artist = (?), Genre = (?) WHERE songId = (?)");
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setString(3, song.getGenre());
            pstmt.setInt(4, song.getId());
            pstmt.execute();
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not edit song", ex);
        }
    }

    
    /**
     * Deletes the song from the database
     * @param songToDelete
     * @throws DALException 
     */
    public void deleteSongFromLibrary(Song songToDelete) throws DALException
    {
        try
        {
            int songID = songToDelete.getId();
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Songs WHERE songID=(?)");
            pstmt.setInt(1,songID); 
            pstmt.execute();
            System.out.println("Following song has been deleted: "+songID);
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not delete song from library", ex);
        }
    }
    
    
    /**
     * Returns all songs
     * @return
     * @throws DALException 
     */
    public ArrayList<Song> getAllSongs() throws DALException
    {
        try
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
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not get all songs", ex);
        }
    }
    
    
    /**
     * Returns the song with it's songID
     * @param songID
     * @return
     * @throws DALException 
     */
    public Song getSong(int songID) throws DALException 
    {
        try
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
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not get song", ex);
        }
    } 

    
    /**
     * Deletes song from Local PC
     * @param filepath
     * @throws DALException 
     */
    public void deleteSongFromHardDisk(String filepath) throws DALException
    {
        try
        {
            System.out.println("Deleting song from hard disk: "+filepath);
            File fileToDelete = new File(new URI(filepath));
            if(fileToDelete.delete()==true)
            {
                System.out.println("File has been deleted");
            }
        } 
        catch (URISyntaxException ex)
        {
            throw new DALException("Could not delete file from hard disk", ex);
        }
    }
}
