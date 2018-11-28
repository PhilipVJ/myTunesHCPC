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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.be.User;
import mytunes.gui.model.MTModel;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class MyTunesController implements Initializable
{
private User currentUser;
    @FXML
    private ListView<Playlist> playlistView;
    @FXML
    private ListView<?> playlistSongsView;
    @FXML
    private ListView<?> allSongsView;
    @FXML
    private TextField searchTxt;
    @FXML
    private Label userName;
    
    private MTModel mtmodel;
    @FXML
    private AnchorPane rootPane2;
    /**
     * Initializes the controller class.
     */
    public MyTunesController() throws IOException, SQLException{
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    try
    {
        mtmodel = new MTModel();
    } catch (IOException ex)
    {
        Logger.getLogger(MyTunesController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex)
    {
        Logger.getLogger(MyTunesController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }    

    void setUser(User user) throws IOException, SQLException
    {
        currentUser=user;
        userName.setText(user.getName());
        playlistView.setItems(mtmodel.getPlaylists(currentUser.getID())); // Skal måske smides et andet sted
    }

    @FXML
    private void newPlaylist(ActionEvent event) throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/AddPlaylist.fxml"));
        Parent root = (Parent)loader.load();
        
        AddPlaylistController aController = loader.getController();
        aController.setUser(currentUser);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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

    @FXML
    private void refresh(ActionEvent event) throws IOException, SQLException
    {
     playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
    
    }
    

    

    
}
