package gui.bll;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class PlayBack {

    private final static String fileInputPath = "MyTunes/src/gui/music";

    private static MediaPlayer mediaPlayer;
    private Media media;

    private int songNumber;


    private ArrayList<File> songs;


    private boolean running;

    /**
     * This method is what is used to begin playing a song.
     * It also starts the timer for the song progress bar, makes sure our volume always matches the volume slider
     * and keeps our speed setting when we play a new song.
     *
     */
    public void playSong()
    {
        running = true;
        mediaPlayer.play();

    }

    public void pauseMedia()
    {
        running = false;
        mediaPlayer.pause();

    }

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
     * We are checking if we are at the end of our list or not and allowing us to loop the "playlist" infinitely.
     * We also use this method to note our current progress in the song, and if we hit previous past 5 seconds it will begin the song again.
     * If the user then hits previous a second time it will go back a song.
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
     * This method is used to implement the mediaSet(); method after initialization
     * First it checks to see if the player is running, if so it stops the song and resets our timer.
     * Then it retrieves a new song changes our label to match it and finally plays the new song.
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

    public void mediaSet()
    {
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    /**
     * This method is what we use to determine the passed time since the song began, and how much time is left.
     * We compare our current time and our end time to get our current progress percentage.
     * if the progress reaches 100%, the timer is reset and our next song plays.
     * This method is also used to change our previous button icon according to the function it has.
     */







    public void fileToPlaylist()
    {
        songs = new ArrayList<>();
        File directory = new File(fileInputPath);
        File[] files = directory.listFiles();
        if(files != null){
            Collections.addAll(songs, files);

        }

    }

    public boolean isRunning()
    {
        return running;
    }


    public int currentSongNumber()
    {
        return songNumber;
    }
    public MediaPlayer mediaPlayerAccess()
    {
        return mediaPlayer;
    }
    public Media mediaAccess()
    {
        return media;
    }




    public String returnSongLabel()
    {
        String songLabelName;
        songLabelName = songs.get(songNumber).getName();
        return songLabelName;
    }
}
