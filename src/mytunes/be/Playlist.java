/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import mytunes.bll.MTManager;

/**
 *
 * @author Philip
 */
public class Playlist
{
    private int id;
    private String playlistName;
    private ArrayList<Song> songs;
    private int userId;
    private int lengthInSeconds;
    
    public Playlist(int id, String playlistName, int userId)
    {
        this.id = id;
        this.playlistName = playlistName;
        this.userId = userId;
        
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return playlistName;
    }

    public ArrayList<Song> getSongs()
    {
        return songs;
    }

    @Override
    public String toString()
    {
        if (lengthInSeconds>0){
          return playlistName+"   "+getFormattedLength();
        }
        else{
        return playlistName;
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
    
    public String getFormattedLength()
    {
      return MTManager.getSecToMin(lengthInSeconds);
    }

    
}
