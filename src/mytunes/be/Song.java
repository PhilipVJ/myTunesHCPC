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
public class Song
{
    
    public String artist;
    public String title;
    public String genre;
    public String filepath;
    public int id;
    public int time;
    
    public Song(String artist, String title, String genre, String filepath, int id, int time)
    {
        this.artist=artist;
        this.title=title;
        this.genre=genre;
        this.filepath=filepath;
        this.id=id;
        this.time=time;
                
    }
    
    public String getTitle()
    {
        return title;
    }
  
}
    
            


