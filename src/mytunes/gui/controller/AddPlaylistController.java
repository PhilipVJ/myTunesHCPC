/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.be.User;
import mytunes.gui.model.MTModel;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class AddPlaylistController implements Initializable
{
    
    @FXML
    private TextField playListName; 
    @FXML
    private AnchorPane rootPane2;
    
    private User currentUser;
    private MTModel mtmodel;
   
    
    /**
     * Initializes the controller class.
     */
    public AddPlaylistController()
    {
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    void setUser(User user)
    {
        currentUser=user;
    }

    @FXML
    private void addPlaylist(ActionEvent event)
    {
        String playlistName = playListName.getText();
        if(playlistName.length()==0)
        {
            playListName.setText("Please write a Playlist name");
            return;
        }
        mtmodel.addPlaylist(currentUser.getID(), playlistName);
        Stage stage = (Stage) rootPane2.getScene().getWindow();
   
        stage.close(); 
    }
    


    void setModel(MTModel mtmodel)
    {
       this.mtmodel = mtmodel;
    }
            
}
