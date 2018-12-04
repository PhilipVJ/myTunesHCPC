/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.MTModel;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class AddSongController implements Initializable
{

    @FXML
    private TextField title;
    @FXML
    private TextField artist;
    @FXML
    private TextField genre;
    @FXML
    private TextField time;
    @FXML
    private TextField filepath;
    @FXML
    private AnchorPane rootPane2;
    
    private MTModel mtmodel;
 
    

    /**
     * Initializes the controller class.
     */
    public AddSongController() throws IOException, SQLException
    {
     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void chooseFile(ActionEvent event) throws IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Music File");
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        File mediafile = fileChooser.showOpenDialog(stage);
   
        MP3File mp3file = new MP3File(mediafile);
        String path = mediafile.toURI().toString();
        
        if ( mp3file.hasID3v2Tag()==true)
        {
            AbstractID3v2 ID3 = mp3file.getID3v2Tag();
   
            String artist = ID3.getLeadArtist();
            String title = ID3.getSongTitle();
            String genre = ID3.getSongGenre();
    
            int duration = 0;
            AudioFile audioFile = AudioFileIO.read(mediafile);
            duration = audioFile.getAudioHeader().getTrackLength();
            String time = mtmodel.getSecToMin(duration);
    
            this.title.setText(title);
            this.artist.setText(artist);
            this.time.setText(time);
            this.genre.setText(genre);
            this.filepath.setText(path);
        }
        
        else 
        {
            int duration = 0;
            AudioFile audioFile = AudioFileIO.read(mediafile);
            duration = audioFile.getAudioHeader().getTrackLength();
            String time = mtmodel.getSecToMin(duration);
            int lastIndex = path.lastIndexOf('/');
            String toPrint = path.substring(lastIndex+1, path.length()-4);
    
            this.title.setText(toPrint);
            this.artist.setText("");
            this.time.setText(time);
            this.genre.setText("");
            this.filepath.setText(path);    
        }
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        Stage stage = (Stage) rootPane2.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveSong(ActionEvent event) throws IOException, TagException, CannotReadException, org.jaudiotagger.tag.TagException, ReadOnlyFileException, InvalidAudioFrameException, SQLException
    {
        String songTitle = this.title.getText();
        String songArtist = this.artist.getText();
        String songTime = this.time.getText();
        String songGenre = this.genre.getText();
        String songFilepath = this.filepath.getText();
    
        Song songToAdd = new Song(songArtist, songTitle, songGenre, songFilepath, 0, songTime);
        mtmodel.addSong(songToAdd);
    
        Stage stage = (Stage) rootPane2.getScene().getWindow();
      
        stage.close();
    }



    void setMode(MTModel mtmodel)
    {
       this.mtmodel = mtmodel;
    }
}
