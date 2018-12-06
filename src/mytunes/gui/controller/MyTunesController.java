/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.be.User;
import mytunes.gui.model.MTModel;
import mytunes.gui.model.Mp3Player;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class MyTunesController implements Initializable
{
private User currentUser;
    @FXML
    private TableView<Playlist> playlistView;
    @FXML
    private TableView<Song> allSongsView;
    @FXML
    private TextField searchTxt;
    @FXML
    private Label userName;
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
    @FXML
    private Label nowPlaying;
    @FXML
    private Slider volume;
    @FXML
    private Label currentPL;
    @FXML
    private TableColumn<Playlist, String> playlistNameCol;
    @FXML
    private TableColumn<Playlist, String> playlistLengthCol;
    @FXML
    private TableView<Song> playlistSongView;
    @FXML
    private TableColumn<Song, String> playlistSongArtist;
    @FXML
    private TableColumn<Song, String> playlistSongTitle;
    @FXML
    private TableColumn<Song, String> playlistSongLength;
    @FXML
    private TableColumn<Song, String> allSongsArtist;
    @FXML
    private TableColumn<Song, String> allSongsTitle;
    @FXML
    private TableColumn<Song, String> allSongsGenre;
    @FXML
    private TableColumn<Song, String> allSongsLength;
    
    private MTModel mtmodel;
    private Mp3Player mp3Player;
    
    // 1 fortæller at det sidste markede tableview er playlistSongs - 2 fortælle det er allSongs
    private int chosenView;
    
    // Holder ID'et på den sidst valgte playliste.
    private int chosenPL; 
    
    //Holder en boolean som fortæller om filter-knappen er blevet brugt eller ej. Bruges
    private boolean hasBeenFiltered;
   
   
    
    /**
     * Initializes the controller class.
     */
    public MyTunesController() 
    {
        hasBeenFiltered=false;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mtmodel = new MTModel();
            
        // Initializes the tableviews
        playlistNameCol.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        playlistLengthCol.setCellValueFactory(new PropertyValueFactory<>("lengthInMin"));
            
        playlistSongArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        playlistSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        playlistSongLength.setCellValueFactory(new PropertyValueFactory<>("time"));
            
        allSongsArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        allSongsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        allSongsLength.setCellValueFactory(new PropertyValueFactory<>("time"));
        allSongsGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    
    /*
        Here starts User methods.
    */
    void setUser(User user) 
    {
        currentUser=user;
        userName.setText(user.getName());
    }
    
    /*
        Here starts Playlist related methods.
    */     
    @FXML
    private void newPlaylist(ActionEvent event)
    {
        
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/AddPlaylist.fxml"));
            Parent root = (Parent)loader.load();
        
            AddPlaylistController aController = loader.getController();
            aController.setUser(currentUser);
        
            aController.setModel(mtmodel);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();   
        } 
        catch (IOException ex)
        {
        Logger.getLogger(MyTunesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editPlaylist(ActionEvent event)
    {
        if (!playlistView.getSelectionModel().isEmpty())
        {
            try 
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/EditPlaylistName.fxml"));
                Parent root = (Parent)loader.load();
                EditPlaylistNameController editPlaylistName = loader.getController();
                editPlaylistName.setPlaylist(playlistView.getSelectionModel().getSelectedItem());
                
                editPlaylistName.setModel(mtmodel);
                editPlaylistName.setTableView(playlistView);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (IOException ex) 
            {
                Logger.getLogger(MyTunesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deletePlaylist(ActionEvent event)
    {
        Playlist pl = playlistView.getSelectionModel().getSelectedItem();
        
        if(!playlistView.getSelectionModel().isEmpty())
        {
            mtmodel.deletePlaylist(pl);
            currentPL.setText("Choose a playlist");
        }
    }
    
    @FXML
    private void upPlaylist(MouseEvent event)
    {
        Song songToMoveUp = playlistSongView.getSelectionModel().getSelectedItem();
        Playlist chosenPLObj = new Playlist(chosenPL, "");
    
        if(!playlistSongView.getSelectionModel().isEmpty())
        {
            mtmodel.moveSongUp(chosenPLObj, songToMoveUp);
            refreshPlaylistSongs();
        }
    }
    
    @FXML
    private void downPlaylist(MouseEvent event) 
    {
        Song songToMoveDown = playlistSongView.getSelectionModel().getSelectedItem();
        Playlist chosenPLObj = new Playlist(chosenPL, "");
    
        if(!playlistSongView.getSelectionModel().isEmpty())
        {
            mtmodel.moveSongDown(chosenPLObj, songToMoveDown);
            refreshPlaylistSongs(); 
        }
    }

    @FXML
    private void addSongToUserPlaylist(MouseEvent event)
    {     
    Song songToMove = allSongsView.getSelectionModel().getSelectedItem();
    Playlist chosenPLObj = new Playlist(chosenPL, "");
    
        if(songToMove!=null && chosenPLObj!=null)
        {
            mtmodel.addSongToPlaylist(songToMove,chosenPLObj);
            refreshPlaylistSongs();
            refreshList();
        }
    }

    @FXML
    private void deleteSongFromPlaylist(ActionEvent event) 
    {
        Song songToDelete = playlistSongView.getSelectionModel().getSelectedItem();
        Playlist chosenPLObj = new Playlist(chosenPL, "");
    
        mtmodel.deleteSongFromPlaylist(chosenPLObj, songToDelete);
        refreshList();
    }
    
@FXML
    private void choosePlaylist(MouseEvent event)
    { 
        if (playlistView.getSelectionModel().getSelectedItem()!=null)
        {
            chosenPL = playlistView.getSelectionModel().getSelectedItem().getId();
            currentPL.setText(playlistView.getSelectionModel().getSelectedItem().getPlaylistName());  
            refreshPlaylistSongs();
        }
    }
    
    /*
        Here starts Song related methods.
    */
    @FXML
    private void newSong(ActionEvent event) 
    {    
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/AddSong.fxml"));
            Parent root = (Parent)loader.load();
            AddSongController addSongCon = loader.getController();
        
            addSongCon.setModel(mtmodel);
        
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        catch (IOException ex)
        {
        Logger.getLogger(MyTunesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void editSong(ActionEvent event)
    {
        Song songToEdit = allSongsView.getSelectionModel().getSelectedItem();
        if(!allSongsView.getSelectionModel().isEmpty())
        {
            try 
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/EditSong.fxml"));
                Parent root = (Parent)loader.load();
                EditSongController editSongCon = loader.getController();
                editSongCon.setSong(songToEdit);
                
                editSongCon.setTableView(allSongsView);
                editSongCon.setModel(mtmodel);
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(MyTunesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void deleteSongFromFileLibrary(ActionEvent event) 
    {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete this song from the library?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) 
        {
            Song songToDelete = allSongsView.getSelectionModel().getSelectedItem();
            mtmodel.deleteSongFromLibrary(songToDelete);  
            refreshList();
            refreshPlaylistSongs();
            alert.close();
            
            Alert alert2 = new Alert(AlertType.CONFIRMATION, "Do you want to delete this song from your hard disk?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert2.showAndWait();
            
            if (alert2.getResult() == ButtonType.YES) 
            {
                mtmodel.deleteSongFromHardDisk(songToDelete.getFilepath());
            }
        }
    }
    
    /*
        Here starts Refresh related methods.
    */
    public void refreshList() 
    {
        playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
    }
    
    public void setListViews() 
    {
        playlistView.setItems(mtmodel.getPlaylists(currentUser.getID()));
        allSongsView.setItems(mtmodel.getSongs()); 
    }

    private void refreshPlaylistSongs() 
    {
        Playlist chosenPLObj = new Playlist(chosenPL, "");
        ObservableList<Song>allSongs = mtmodel.getPlaylistSongs(chosenPLObj);
        for (Song x:allSongs)
        {
            if ("error".equals(x.getFilepath()))
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Important information");
                alert.setHeaderText("A mediafile on your playlist has been deleted from the library");
                alert.setContentText("You must delete it from your playlist immediately!");
                alert.showAndWait();
            }
        }
        playlistSongView.setItems(allSongs);
    }

    /*
        Here starts Search related methods.
    */
    @FXML
    private void searchClicked(MouseEvent event) 
    {
        if(hasBeenFiltered==false)
        {
        allSongsView.setItems(mtmodel.searchSong(searchTxt.getText()));
        hasBeenFiltered=true;
        searchTxt.setText("Click again to clear");
        return;
        }
        
        if (hasBeenFiltered==true)
        {
            allSongsView.setItems(mtmodel.getSongs());
            hasBeenFiltered=false;
            searchTxt.clear();
        }
    }

    /*
        Here starts functionally methods for playing songs.
    */
    @FXML
    private void playSong(MouseEvent event)
    {
        // Checker om en sang allerede bliver afspillet. Hvis den gør - så stopper vi den
        if(mp3Player!=null)
        {
            mp3Player.stop();
        } 
        // Derefter ser vi hvilken sang der er markeret og starter med at afspille den.
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
            ObservableList<Song>playlistSongs = playlistSongView.getItems();
            int songIndex = playlistSongView.getSelectionModel().getSelectedIndex();
            mp3Player = new Mp3Player();
    
            mp3Player.initPlay(songIndex, playlistSongs);
            setLabel();
        }   
    }
    
    @FXML
    private void nextSong(MouseEvent event)
    {
        if(mp3Player!=null)
        {
            mp3Player.next();
        }
    }
    
    @FXML
    private void previousSong(MouseEvent event)
    {
        if(mp3Player!=null)
        {
            mp3Player.previous();
        }
    }
    
    @FXML
    private void stopSong(ActionEvent event)
    {
        if(mp3Player!=null)
        {
            mp3Player.stop();        
        }
    }

    @FXML
    private void pauseSong(ActionEvent event)
    {
        if(mp3Player!=null)
        {
            mp3Player.pause();
        }
    }

    @FXML
    private void resumeSong(ActionEvent event)
    {
        if(mp3Player!=null)
        {
            mp3Player.resume();
        }
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
    
    public void setLabel()
    {
       nowPlaying.textProperty().bind(mp3Player.getStringPropertyTitle());       
    }
}