/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import mytunes.be.Song;
import mytunes.gui.model.Mp3Player;

/**
 *
 * @author Philip
 */
public class TesterClass
{
    
public static void main(String[] args) throws IOException 
{
    
Mp3Player testerclass = new Mp3Player();
JFXPanel fxPanel = new JFXPanel();

Song testSong3= new Song("test", "Clip3", "genre", "file:/C:/Users/phili/Documents/BachGavotteShort.mp3", 1, "3:02");
Song testSong= new Song("test", "Clip1", "genre", "file:/C:/Users/phili/Documents/harley-davidson-daniel_simon.mp3", 2, "3:02");
Song testSong2 = new Song("test", "Clip2", "genre", "file:/C:/Users/phili/Documents/service-bell_daniel_simion", 3, "3:02");
ObservableList<Song> songs = FXCollections.observableArrayList();

songs.add(testSong);
songs.add(testSong2);
songs.add(testSong3);

for (Song x:songs){ System.out.println(""+x.getFilepath());}


testerclass.play(songs);
}


}
