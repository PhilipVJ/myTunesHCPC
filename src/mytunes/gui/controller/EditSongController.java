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
import mytunes.be.Song;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class EditSongController implements Initializable
{

    @FXML
    private AnchorPane rootPane2;
    @FXML
    private TextField title;
    @FXML
    private TextField artist;
    @FXML
    private TextField genre;
    @FXML
    private TextField time;
    
    private Song songToEdit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    


    @FXML
    private void cancel(ActionEvent event)
    {
    Stage stage = (Stage) rootPane2.getScene().getWindow();
    stage.close();
    }

    @FXML
    private void saveSong(ActionEvent event)
    {
    }

    void setSong(Song songToEdit)
    {
       this.songToEdit = songToEdit;
       this.title.setText(songToEdit.getTitle());
       this.artist.setText(songToEdit.getArtist());
       this.genre.setText(songToEdit.getGenre());
       this.time.setText(songToEdit.getTime());
    }
    
}
