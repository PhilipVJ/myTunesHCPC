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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.be.Playlist;
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
    @FXML
    private AnchorPane rootPane3;
    
    private Playlist plToEdit; 
    private MTModel mtmodel;
    private TableView<Playlist> playlistTableView;
       
     /**
     * Initializes the controller class.
     */
    public EditPlaylistNameController()
    {
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }    

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) rootPane3.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event)
    {
        String newName = editPlaylistName.getText();
        
        if(newName.length()==0)
        {
            editPlaylistName.setText("Please write a new name here");
            return;
        }
        
        mtmodel.editPlaylist(plToEdit.getId(), newName);
        playlistTableView.refresh();
     
     
        Stage stage = (Stage) rootPane3.getScene().getWindow();
        stage.close();
    }

    public void setPlaylist(Playlist selectedItem)
    {
        plToEdit = selectedItem;
    }
    
    public void setModel(MTModel model){
        mtmodel = model;
    }

    void setTableView(TableView<Playlist> playlistView)
    {
        playlistTableView = playlistView;
    }
}
