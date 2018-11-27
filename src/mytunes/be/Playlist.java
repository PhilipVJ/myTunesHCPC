/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

/**
 *
 * @author Philip
 */
public class Playlist
{
    private int id;
    private String playlistName;
    private String songs;
    
    public Playlist(int id, String playlistName)
    {
        this.id = id;
        this.playlistName = playlistName;
        
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return playlistName;
    }

    public String getSongs()
    {
        return songs;
    }

    @Override
    public String toString()
    {
        return playlistName;
    }

    public void addSongs(String songs)
    {
        this.songs = songs;
    }

    
}
