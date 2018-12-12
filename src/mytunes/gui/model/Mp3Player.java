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
import mytunes.be.Song;

/**
 *
 * @author Philip
 */
public class Mp3Player
{
    private int currentSong=0;
    private ObservableList<Song> songsToPlay;
    private Media media;
    private MediaPlayer mediaPlayer;
    private StringProperty currentTitle;

    public Mp3Player()
    {
        currentTitle = new SimpleStringProperty();
    }

    /**
    * Initializes the player class and calls the play function, which plays through 
    * the entire list.
    * @param songIndex
    * @param songs 
    */
    public void initPlay(int songIndex, ObservableList<Song> songs)
    {
        currentSong=songIndex; 
        songsToPlay=songs;
        play(currentSong,songsToPlay);
    }

    public void play(int songListNr, ObservableList<Song> songs)
    {
        if (currentSong==songs.size() || currentSong==-1)
        {
            return;
        }
        String path=songs.get(songListNr).getFilepath();
        media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        String songLabel = ""+songs.get(songListNr).getArtist()+" - "+songs.get(songListNr).getTitle();
        currentTitle.set(songLabel);
          
        mediaPlayer.play();  

        mediaPlayer.setOnEndOfMedia(new Runnable() 
        {
            @Override
            public void run()
            {
                //Plays the next song in the list 
                currentSong++;
                play(currentSong,songsToPlay);
            }
        });
    }

    public void stop()
    {
        mediaPlayer.stop();
        currentTitle.set("Song has been stopped");
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
        if (currentSong<songsToPlay.size()-1)
        {
            stop();
            currentSong++;
            play(currentSong, songsToPlay);
        }
    }

    public void previous()
    {
        if (currentSong>0)
        {
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