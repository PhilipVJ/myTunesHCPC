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

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import mytunes.be.Playlist;
import mytunes.be.Song;
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
public class MyTunesController implements Initializable
{
private User currentUser;
    @FXML
    private ListView<Playlist> playlistView;
    @FXML
    private ListView<Song> playlistSongsView;
    @FXML
    private ListView<Song> allSongsView;
    @FXML
    private TextField searchTxt;
    @FXML
    private Label userName;
    
    private MTModel mtmodel;
    @FXML

    private AnchorPane rootPane2;

    @FXML
    private ImageView previousSongbtn;
    
    @FXML
    private ImageView playSongbtn;
    @FXML
    private ImageView nextSongBtn;
    @FXML
    private Label playlistinfo;
    @FXML
    private ImageView searchBtn;
    
    

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
//        playlistView.setItems(mtmodel.getPlaylists(currentUser.getID())); // Skal måske smides et andet sted
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
    private void deletePlaylist(ActionEvent event) throws IOException, SQLException
    {
        Playlist pl = playlistView.getSelectionModel().getSelectedItem();
        
        if(pl==null)
        {
            playlistinfo.setText("Please select a playlist");
        }
       mtmodel.deletePlaylist(pl);
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
    private void deleteSong(ActionEvent event) throws IOException, SQLException
    {
       Song songs = allSongsView.getSelectionModel().getSelectedItem();
       mtmodel.deleteSong(songs);
    }

    @FXML
    private void newSong(ActionEvent event) throws IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException, SQLException
    {
//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Open Music File");
//    Stage stage = (Stage) rootPane2.getScene().getWindow();
//    File mediafile = fileChooser.showOpenDialog(stage);
//    mtmodel.addSong(mediafile);
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/AddSong.fxml"));
        Parent root = (Parent)loader.load();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    
    
//    MediaPlayer mediaPlayer = new MediaPlayer(hit);
//    mediaPlayer.play();
    
    

    
    
    }

    @FXML
    private void editSong(ActionEvent event) throws IOException
    {
    Song songToEdit = allSongsView.getSelectionModel().getSelectedItem();
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/EditSong.fxml"));
    Parent root = (Parent)loader.load();
    EditSongController editSongCon = loader.getController();
    editSongCon.setSong(songToEdit);
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();
    
    }


    @FXML
    private void nextSong(MouseEvent event)
    {
    }

    @FXML
    private void playSong(MouseEvent event)
    {
    }

    @FXML
    private void previousSong(MouseEvent event)
    {
    }


    @FXML
    private void refresh(ActionEvent event) throws IOException, SQLException
    {
     playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
     allSongsView.setItems(mtmodel.getSongs(currentUser.getID()));
    
     
    
    }
    
    public void setListViews() throws IOException, SQLException
    {
     playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
     allSongsView.setItems(mtmodel.getSongs(currentUser.getID()));
     
     
     
    }

    @FXML

    private void refreshPlaylistSongs(ActionEvent event) throws IOException, SQLException
    {
    Playlist chosenPlaylist = playlistView.getSelectionModel().getSelectedItem();
     
     playlistSongsView.setItems(mtmodel.getPlaylistSongs(chosenPlaylist));
    }

    private void searchEnter(KeyEvent event)
    {
    }

    @FXML
    private void searchClicked(MouseEvent event)
    {

    }
    
    


    
}
