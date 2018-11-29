/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

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
import mytunes.dal.PlaylistDbDAO;
import mytunes.gui.model.MTModel;

/**
 * FXML Controller class
 *
 * @author Caspe
 */
public class EditPlaylistNameController implements Initializable
{

    @FXML
    private TextField editPlaylistName;
    private AnchorPane rootPane2;
    private Playlist plToEdit;
    
    private MTModel mtmodel;
    @FXML
    private AnchorPane rootPane3;
    /**
     * Initializes the controller class.
     */
    
    public EditPlaylistNameController() throws IOException, SQLException
    {
    mtmodel = new MTModel();
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
    private void save(ActionEvent event) throws IOException, SQLException
    {
        String newName = editPlaylistName.getText();
        
        
        mtmodel.editPlaylist(plToEdit.getId(), newName);
    
   
    Stage stage = (Stage) rootPane3.getScene().getWindow();
    stage.close();
    }

    public void setPlaylist(Playlist selectedItem)
    {
        plToEdit = selectedItem;
    }

    
}