/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.User;
import mytunes.gui.model.MTModel;
import org.farng.mp3.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class AddPlaylistController implements Initializable
{
private User currentUser;
    @FXML
    private TextField playListName;
    private MTModel mtmodel;
    @FXML
    private AnchorPane rootPane2;
    
    private MyTunesController mTController;
    /**
     * Initializes the controller class.
     */
    public AddPlaylistController() throws IOException, SQLException
    {
    mtmodel = new MTModel();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
     void setUser(User user) throws IOException, SQLException
    {
        currentUser=user;
    }

    @FXML
    private void addPlaylist(ActionEvent event) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
    String playlistName = playListName.getText();
    mtmodel.addPlaylist(currentUser.getID(), playlistName);
    Stage stage = (Stage) rootPane2.getScene().getWindow();
    mTController.refreshList();
    stage.close();
    
    }
    
    public void setPrevController(MyTunesController prev)
    {
    mTController=prev;
    }
            
}
