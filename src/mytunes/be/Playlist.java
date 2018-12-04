/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import mytunes.bll.MTManager;

/**
 *
 * @author Philip
 */
public class Playlist
{
    private int id;
    private SimpleStringProperty playlistName;
    private ArrayList<Song> songs;
    private int userId;
    private int lengthInSeconds;
    private SimpleStringProperty lengthInMin;
    
    public Playlist(int id, String playlistName, int userId)
    {
        this.id = id;
        this.playlistName = new SimpleStringProperty(playlistName);
        this.userId = userId;
        
    }

    public int getId()
    {
        return id;
    }

    public String getPlaylistName()
    {
        return playlistName.get();
    }

    public ArrayList<Song> getSongs()
    {
        return songs;
    }

    @Override
    public String toString()
    {
        if(lengthInSeconds>0){
            return playlistName+"   "+getLengthInMin();
        }
        
        else
        {
            return playlistName.get();
        }
    }

    public void addSongs(Song songToAdd)
    {
        songs.add(songToAdd);
    }
    
    public void addLengthInSeconds(int length)
    {
        lengthInSeconds=length;
        
    }
    
    public String getLengthInMin()
    {
      return MTManager.getSecToMin(lengthInSeconds);
    }
    
    public void setName(String name)
    {
        playlistName.set(name);
    }
}
