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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
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
import mytunes.gui.model.Mp3Player;
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
    private Label playlistinfo;
    @FXML
    private ImageView searchBtn;
    
    
    private Mp3Player mp3Player;
    
    private int chosenView;
    @FXML
    private Label nowPlaying;
  
    private int chosenPL;
    @FXML
    private Slider volume;
 
    private int markedPl;
    @FXML
    private Label currentPL;
    
    
    

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
        // FXMLLoader loads the AddPlaylist fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/AddPlaylist.fxml"));
        Parent root = (Parent)loader.load();
        
        
        AddPlaylistController aController = loader.getController();
        aController.setUser(currentUser);
        
        
        MyTunesController mTController = this;
        aController.setPrevController(this);
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        
    }

    @FXML
    private void editPlaylist(ActionEvent event) throws IOException
    {

    if (!playlistView.getSelectionModel().isEmpty()){
       

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/EditPlaylistName.fxml"));
    Parent root = (Parent)loader.load();
    EditPlaylistNameController editPlaylistName = loader.getController();
    editPlaylistName.setPlaylist(playlistView.getSelectionModel().getSelectedItem());
    
    MyTunesController mTController = this;
    editPlaylistName.setPrevController(this);
    
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

    }
    

        
        

    }

    @FXML
    private void deletePlaylist(ActionEvent event) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        Playlist pl = playlistView.getSelectionModel().getSelectedItem();
        
        if(!playlistView.getSelectionModel().isEmpty())
        {
            mtmodel.deletePlaylist(pl);
            refreshList();
        }

       mtmodel.deletePlaylist(pl);
      


    }
    @FXML
    private void upPlaylist(MouseEvent event) throws IOException, SQLException
    {
        Song songToMoveUp = playlistSongsView.getSelectionModel().getSelectedItem();
    Playlist chosenPLObj = new Playlist(chosenPL, "", 0);
    
        if(!playlistSongsView.getSelectionModel().isEmpty())
            {
                mtmodel.moveSongUp(chosenPLObj, songToMoveUp);
                refreshPlaylistSongs();
            }
    }
    
    
    @FXML
    private void downPlaylist(MouseEvent event) throws IOException, SQLException
    {
        Song songToMoveDown = playlistSongsView.getSelectionModel().getSelectedItem();
    Playlist chosenPLObj = new Playlist(chosenPL, "", 0);
    
        if(!playlistSongsView.getSelectionModel().isEmpty())
            {
                mtmodel.moveSongDown(chosenPLObj, songToMoveDown);
                refreshPlaylistSongs(); 
            }
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
    AddSongController addSongCon = loader.getController();
       
    MyTunesController mTController = this;
    addSongCon.setPrevController(this);
        
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
    if(!allSongsView.getSelectionModel().isEmpty())
        {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/EditSong.fxml"));
        Parent root = (Parent)loader.load();
        EditSongController editSongCon = loader.getController();
        editSongCon.setSong(songToEdit);
    
        MyTunesController mTController = this;
        editSongCon.setPrevController(this);
    
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        }
    
    
    }


    @FXML
    private void nextSong(MouseEvent event)
    {
       if(mp3Player!=null){
        mp3Player.next();
        
       }
    }
    

    @FXML
    private void playSong(MouseEvent event)
    {
    if (mp3Player!=null){
        mp3Player.stop();
        } 
        
    if (chosenView==2)
    {
    ObservableList<Song>allSongs = allSongsView.getItems();
    int songIndex = allSongsView.getSelectionModel().getSelectedIndex();
    mp3Player = new Mp3Player();
    
    mp3Player.initPlay(songIndex, allSongs);
    setLabel();
    }
    
    if (chosenView==1)
    {
    ObservableList<Song>playlistSongs = playlistSongsView.getItems();
    int songIndex = playlistSongsView.getSelectionModel().getSelectedIndex();
    mp3Player = new Mp3Player();
    
    mp3Player.initPlay(songIndex, playlistSongs);
    setLabel();
    }
        
    }

    @FXML
    private void previousSong(MouseEvent event)
    {
        if(mp3Player!=null){
        mp3Player.previous();
        }
    }


    
    public void refreshList() throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
     playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
     allSongsView.setItems(mtmodel.getSongs());  
    }
    
    public void setListViews() throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
     playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
     allSongsView.setItems(mtmodel.getSongs());
     
     
     
    }

    private void refreshPlaylistSongs() throws IOException, SQLException
    {
    Playlist chosenPLObj = new Playlist(chosenPL, "", 0);
    
    playlistSongsView.setItems(mtmodel.getPlaylistSongs(chosenPLObj)); 
    
    }

@FXML
    private void searchEnter(KeyEvent event) throws IOException, SQLException
    {
    
    }

    @FXML
    private void searchClicked(MouseEvent event) throws IOException, SQLException
    {

allSongsView.setItems(mtmodel.searchSong(searchTxt.getText()));
    }

    @FXML
    private void addSongToUserPlaylist(MouseEvent event) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        
    Song songToMove = allSongsView.getSelectionModel().getSelectedItem();
    Playlist playlistChosen = playlistView.getSelectionModel().getSelectedItem();
    
    if(songToMove!=null && playlistChosen!=null){
    mtmodel.addSongToPlaylist(songToMove,playlistChosen);
    refreshPlaylistSongs();
    refreshList();
    }
    
    
    
    }

    @FXML
    private void deleteSongFromPlaylist(ActionEvent event) throws IOException, SQLException, SQLServerException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {

    
    Song songToDelete = playlistSongsView.getSelectionModel().getSelectedItem();
    Playlist playlistChosen = playlistView.getSelectionModel().getSelectedItem();
    mtmodel.deleteSongFromPlaylist(playlistChosen, songToDelete);

    Playlist chosenPLObj = new Playlist(chosenPL, "", 0);
    
    mtmodel.deleteSongFromPlaylist(chosenPLObj, songToDelete);

    refreshPlaylistSongs();
    refreshList();
    
    }

    @FXML
    private void deleteSongFromFileLibrary(ActionEvent event) throws IOException, SQLException
    {
   Song songToMove = allSongsView.getSelectionModel().getSelectedItem();
   mtmodel.deleteSongFromLibrary(songToMove);
   allSongsView.setItems(mtmodel.getSongs());  
   refreshPlaylistSongs();
  
   
    }

    @FXML
    private void choosePlaylist(MouseEvent event) throws IOException, SQLException
    {
//        System.out.println("Choosing playlist");
//   
    chosenPL = playlistView.getSelectionModel().getSelectedItem().getId();
    
    refreshPlaylistSongs();
    }

    @FXML
    private void stopSong(ActionEvent event)
    {
        if (mp3Player!=null){
        mp3Player.stop();
        }
    }

    @FXML
    private void pauseSong(ActionEvent event)
    {
        if (mp3Player!=null){
        mp3Player.pause();
        }
    }

    @FXML
    private void resumeSong(ActionEvent event)
    {
        if(mp3Player!=null){
        mp3Player.resume();
        }
    }

    @FXML
    private void playlistSongsChosen(MouseEvent event)
    {
    chosenView=1;
    
    }

    @FXML
    private void allSongsChosen(MouseEvent event)
    {
    chosenView=2;
    
    }
    
    public void setLabel(){
       nowPlaying.textProperty().bind(mp3Player.getStringPropertyTitle());
       
           }

    @FXML
    private void setVolume(MouseEvent event)
    {
   double newVolume = volume.getValue();
   if (mp3Player!=null)
   {
       mp3Player.setVolume(newVolume);
   }
    }

    
    
    
    
    


    
}
