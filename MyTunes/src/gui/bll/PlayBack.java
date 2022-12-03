package gui.bll;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class PlayBack {

    private final static String fileInputPath = "MyTunes/src/gui/datasources/playlists/music";

    private static MediaPlayer mediaPlayer;
    private Media media;

    private int songNumber;


    private ArrayList<File> songs;


    private boolean running;

    /**
     * This method is what is used to begin playing a song.
     * It lets us know our player is running and plays the selected song.
     */
    public void playSong()
    {
        running = true;
        mediaPlayer.play();

    }

    /**
     * This method is used to pause.
     * It lets us know our player is not running and pauses our media.
     */
    public void pauseMedia()
    {
        running = false;
        mediaPlayer.pause();

    }

    /**
     * This is what we use for our reset function. It simply seeks to the beginning of the song.
     */
    public void resetMedia()
    {
        mediaPlayer.seek(Duration.seconds(0));
    }

    /**
     * This method allows us to skip to the next song.
     * The if statement is checking if we are at the end of our list or not and allowing us to loop the "playlist" infinitely.
     */
    public void mediaSkip()
    {
        if(songNumber < songs.size() - 1)
        {
            songNumber++;
        }
        else
        {
            songNumber = 0;
        }
        changeMediaPlayer();
    }

    /**
     * This method allows us to skip to the previous song.
     * We are checking if we are at the end of our list or not first.
     * We also use this method to note our current progress in the song, and if we hit previous past 3 seconds it will begin the song again.
     * If the user then hits previous a second time under 3 seconds it will go back a song.
     */
    public void mediaBack()
    {
        int i = (int) mediaPlayer.getCurrentTime().toSeconds();
        if(songNumber > 0)
        {
            if(i > 3){
                resetMedia();
            }
            else {
                songNumber--;
                changeMediaPlayer();
            }
        }
        else {
            if(i > 3){
                resetMedia();
            }
            else {
                songNumber = songs.size() - 1;
                changeMediaPlayer();
            }
        }
    }





    /**
     * This method is used to prevent multiple songs playing at once.
     * First it checks to see if the player is running, if so it stops the song and lets us know our media is not running.
     * Then it plays the next song.
     */
    public void changeMediaPlayer()
    {
        if(running) {
            mediaPlayer.stop();
            running = false;
        }
        mediaSet();
        playSong();
    }

    /**
     * What we use when we instantiate our media player and when we are changing our media.
     */
    public void mediaSet()
    {
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }


    public void fileToPlaylist()
    {
        songs = new ArrayList<>();
        File directory = new File(fileInputPath);
        File[] files = directory.listFiles();
        if(files != null){
            Collections.addAll(songs, files);

        }

    }

    /**
     * In some scenarios we need to know if our player is running or not.
     */
    public boolean isRunning()
    {
        return running;
    }

    /**
     * Allows us to access our media player from the controller class.
     */
    public MediaPlayer mediaPlayerAccess()
    {
        return mediaPlayer;
    }

    /**
     * Gives us access to our media when needed.
     */
    public Media mediaAccess()
    {
        return media;
    }

    /**
     * Gives us access to our song number when needed.
     */
    public int currentSongNumber()
    {
        return songNumber;
    }

    /**
     * What we use to let our controller know what the song name is.
     */
    public String returnSongLabel()
    {
        String songLabelName;
        songLabelName = songs.get(songNumber).getName();
        return songLabelName;
    }
}
