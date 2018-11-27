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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mytunes.be.User;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class MyTunesController implements Initializable
{
User currentUser;
    @FXML
    private ListView<?> playlistView;
    @FXML
    private ListView<?> playlistSongsView;
    @FXML
    private ListView<?> allSongsView;
    @FXML
    private TextField searchTxt;
    @FXML
    private Label userName;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    void setUser(User user)
    {
        currentUser=user;
        userName.setText(user.getName());
    }

    @FXML
    private void newPlaylist(ActionEvent event)
    {
    }

    @FXML
    private void editPlaylist(ActionEvent event)
    {
    }

    @FXML
    private void deletePlaylist(ActionEvent event)
    {
    }

    @FXML
    private void upPlaylist(ActionEvent event)
    {
    }

    @FXML
    private void downPlaylist(ActionEvent event)
    {
    }

    @FXML
    private void deleteSong(ActionEvent event)
    {
    }

    @FXML
    private void newSong(ActionEvent event)
    {
    }

    @FXML
    private void editSong(ActionEvent event)
    {
    }

    @FXML
    private void searchBtn(ActionEvent event)
    {
    }

    @FXML
    private void playSong(ActionEvent event)
    {
    }

    @FXML
    private void previousSong(ActionEvent event)
    {
    }

    @FXML
    private void nextSong(ActionEvent event)
    {
    }
    
}
