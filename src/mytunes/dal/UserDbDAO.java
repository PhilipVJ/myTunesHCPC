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
import java.util.List;
import mytunes.be.Playlist;
import mytunes.be.User;
import mytunes.dal.exception.DALException;

/**
 *
 * @author Philip
 */
public class UserDbDAO
{
    /**
     * Adds a user to the database with the specified name
     * @param username
     * @return
     * @throws DALException 
     */
    public User addUser(String username) throws DALException
    {
        try
        {
            DbConnection tester = new DbConnection();
            Connection con = tester.getConnection();
            User addedUser = null;
            
            String SQL = "INSERT INTO Users VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,username);
            pstmt.execute();
             
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next())
            {
                addedUser= new User(generatedKeys.getInt(1), username);
                System.out.println("Following user has been added to the database: "+addedUser.getName()+"   "+addedUser.getID());
                return addedUser;
            }
            return addedUser;
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not add user", ex);
        }
    }

    public void deleteUser(User userToDelete) throws DALException
    {
        try
        {
            int userID = userToDelete.getID();
            
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();
            
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Users WHERE id=(?)");
            pstmt.setInt(1,userID);
            pstmt.execute();
            pstmt.close();
            System.out.println("Following user has been deleted: "+userToDelete.getName());
            
            PlaylistDbDAO pDbDAO = new PlaylistDbDAO();
            List<Playlist> allPlaylists = pDbDAO.getPlaylistsByUser(userToDelete.getID());
            
            for (Playlist x: allPlaylists)
            {
                pDbDAO.deletePlaylist(x);
            }
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not delete user", ex);
        }
    }
/**
 * Returns all users from the database.
 * @return
 * @throws DALException 
 */
    public List<User> getAllUsers() throws DALException
    {
        try
        {
            ArrayList<User> allUsers = new ArrayList<>();
            DbConnection dc = new DbConnection();
            Connection con = dc.getConnection();      
            
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * FROM Users;");
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                allUsers.add(new User(id,username));
            }
            return allUsers;
        }
        catch (IOException |SQLException ex)
        {
            throw new DALException("Could not get all users", ex);
        }
    }
}
