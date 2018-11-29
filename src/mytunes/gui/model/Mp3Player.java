/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import mytunes.be.Song;

/**
 *
 * @author Philip
 */
public class Mp3Player
{
int currentSong=0;
ObservableList<Song> songsToPlay;

public void play(ObservableList<Song> songs)
{
 songsToPlay=songs;
    System.out.println(""+currentSong);  
    String path=songs.get(0).getFilepath();
    Media media = new Media(path);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();  
    
    
    
    mediaPlayer.setOnEndOfMedia(new Runnable() {
    @Override
    public void run()
    {
       currentSong++;
       playSong(currentSong,songsToPlay);
    }
    }
    );
  }

//    isPlaying = mediaPlayer.getStatus().equals(Status.PLAYING);
    

    
    



public void playSong(int song, ObservableList<Song> songs)

{
    System.out.println(""+song);
    
    String path=songs.get(song).getFilepath();
    Media media = new Media(path);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();  
    
    
    
    mediaPlayer.setOnEndOfMedia(new Runnable() {
    @Override
    public void run()
    {
       currentSong++;
       playSong(currentSong,songsToPlay);
    }
    }
    );
  }
}


