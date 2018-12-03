/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import mytunes.be.Song;
import mytunes.gui.controller.MyTunesController;

/**
 *
 * @author Philip
 */
public class Mp3Player
{
int currentSong=0;
ObservableList<Song> songsToPlay;
Media media;
MediaPlayer mediaPlayer;

StringProperty currentTitle;

public Mp3Player(){
    currentTitle = new SimpleStringProperty();
}

/**
 * Initializes the player class and calls the play function, which plays through 
 * the entire list.
 * @param songs 
 */
public void initPlay(int songIndex, ObservableList<Song> songs)
{
    currentSong=songIndex; 
    songsToPlay=songs;
    play(currentSong,songsToPlay);
    
    
    
    

  }

//    isPlaying = mediaPlayer.getStatus().equals(Status.PLAYING);
    

    
    



public void play(int songListNr, ObservableList<Song> songs)

{
    System.out.println(""+songListNr);
    System.out.println("listsize"+songs.size());
    if (currentSong==songs.size() || currentSong==-1)
    {
        return;
    }
    
    String path=songs.get(songListNr).getFilepath();
    media = new Media(path);
    mediaPlayer = new MediaPlayer(media);
    String songLabel = ""+songs.get(songListNr).getArtist()+" - "+songs.get(songListNr).getTitle();
    System.out.println("Song label"+songLabel);
    currentTitle.set(songLabel);
    System.out.println(""+currentTitle.toString());
    
    mediaPlayer.play();  

    mediaPlayer.setOnEndOfMedia(new Runnable() {
    @Override
    public void run()
    {
       
       //Plays the next song in the list 
       currentSong++;
        System.out.println("Currentsong:"+currentSong);
       play(currentSong,songsToPlay);
    }
    }
    );
  }

    public void stop()
    {
        mediaPlayer.stop();
    }
    
    public void pause()
    {
        mediaPlayer.pause();
    }

    public void resume()
    {
        mediaPlayer.play();
    }

    public void next()
    {
        if (currentSong<songsToPlay.size()-1){
        System.out.println("Next song");
        stop();
        currentSong++;
        play(currentSong, songsToPlay);
        }
    }

    public void previous()
    {
        if (currentSong>0){
        System.out.println("Previous song");
        stop();
        currentSong--;
        play(currentSong, songsToPlay); 
        }
        
    }
    
    
     public StringProperty getStringPropertyTitle()
    {
        return currentTitle;
    }
     
    public void setVolume(double volume)
    {
        mediaPlayer.setVolume(volume);
    }

}


