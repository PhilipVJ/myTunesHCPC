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
    public String time;
    public int songPosition;
    
    public Song(String artist, String title, String genre, String filepath, int id, String time)
    {
        this.artist=artist;
        this.title=title;
        this.genre=genre;
        this.filepath=filepath;
        this.id=id;
        this.time=time;
                
    }

    public String getArtist()
    {
        return artist;
    }

    public String getTitle()
    {
        return title;
    }

    public String getGenre()
    {
        return genre;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public int getId()
    {
        return id;
    }

    public String getTime()
    {
        return time;
    }

    @Override
    public String toString()
    {
        return  ""+artist+" - "+title+"     "+genre+"    "+time;
    }
    
    public void setPosition(int position){
        songPosition=position;
    }
    
    public int getPosition()
    {
    return songPosition;
    }
    
    
    
  
}
    
            


