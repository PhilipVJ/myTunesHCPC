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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.User;
import mytunes.dal.PlaylistDbDAO;
import mytunes.gui.model.MTModel;
import org.farng.mp3.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 * FXML Controller class
 *
 * @author Caspe
 */
public class EditPlaylistNameController implements Initializable
{

    @FXML
    private TextField editPlaylistName;
    @FXML
    private AnchorPane rootPane3;
    
    private Playlist plToEdit; 
    private MTModel mtmodel;
    private User user;
       
    /**
     * Initializes the controller class.
     */
    
    public EditPlaylistNameController() throws IOException, SQLException
    {
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    


    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) rootPane3.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        String newName = editPlaylistName.getText();
        
        if(newName.length()==0)
        {
            editPlaylistName.setText("Please write a new name here");
            return;
        }
        
        mtmodel.editPlaylist(plToEdit.getId(), newName);
        
        
     
        Stage stage = (Stage) rootPane3.getScene().getWindow();
        stage.close();
    }

    public void setPlaylist(Playlist selectedItem)
    {
        plToEdit = selectedItem;
    }
    


    void setModel(MTModel mtmodel)
    {
this.mtmodel=mtmodel;
    }

    void setUser(User currentUser)
    {
    user=currentUser;
    }
}
