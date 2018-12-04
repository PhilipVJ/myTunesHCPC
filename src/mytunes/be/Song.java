/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Philip
 */
public class Song
{
    
    public SimpleStringProperty artist;
    public SimpleStringProperty title;
    public SimpleStringProperty genre;
    public String filepath;
    public int id;
    public SimpleStringProperty time;
    public int songPosition;
    
    public Song(String artist, String title, String genre, String filepath, int id, String time)
    {
        this.artist=new SimpleStringProperty(artist);
        this.title=new SimpleStringProperty(title);
        this.genre=new SimpleStringProperty(genre);
        this.filepath=filepath;
        this.id=id;
        this.time=new SimpleStringProperty(time);
                
    }

    public String getArtist()
    {
        return artist.get();
    }

    public String getTitle()
    {
        return title.get();
    }

    public String getGenre()
    {
        return genre.get();
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
        return time.get();
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
    
    public void setArtist(String name)
    {
     artist.set(name);
    }
    
    public void setTitle(String name){
        title.set(name);
    }
    
    public void setGenre(String name)
    {
        genre.set(name);
    }
  
}
    
            


