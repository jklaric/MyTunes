package gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class MyTunesMainWindowController implements Initializable {

    @FXML
    private Label songLabel;

    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ComboBox<String> speedBox;

    private MediaPlayer mediaPlayer;
    private Media media;

    private File directory;
    private File[] files;

    /**
     * This is a temporary solution to make a mock library of our songs.
     */
    private ArrayList<File> songs;
    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};


    private Timer timer;
    private TimerTask task;

    private boolean running;


    /**
     * This Method is creating an arraylist I am using for a mock library until the database is complete.
     * This Method also implements our volume slider and sets the program to the first song in the music folder.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * Note: I could not figure out how to make the file work by calling only for the music folder, so I had to make
         * an absolute path reference. This is a large part of a temporary patch to make sure all methods work until we have the database.
         * Right-click the music folder on the side, copy path/reference, take the absolute path and replace the pathname in line 70 to make the app work.
         */
        songs = new ArrayList<File>();
        directory = new File("C:\\Users\\Don\\Documents\\GitHub\\MyTunes\\MyTunes\\src\\gui\\music");
        files = directory.listFiles();
        if(files != null){
            for(File file : files){
                songs.add(file);

            }
        }
        changeMediaPlayer();
        playbackSpeed();
        volume();
    }

    /**
     * This method is what is used to begin playing a song.
     * It also starts the timer for the song progress bar, makes sure our volume always matches the volume slider
     * and keeps our speed setting when we play a new song.
     *
     */
    private void playSong()
    {
        beginTimer();
        changeSpeed(null);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }

    /**
     * This method stops our music and pauses the timer.
     */
    private void pauseMedia()
    {
        cancelTimer();
        mediaPlayer.pause();
    }

    /**
     * This method resets our song and the progress bar to the beginning.
     */
    private void resetMedia()
    {
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }


    /**
     * This method determines what happens when we hit the play button.
     * If the player is not running, it will begin playing a song. If the player is running, it will stop.
     */
    @FXML
    void playMedia(ActionEvent event) {
        if(running)
            pauseMedia();
        else {
            playSong();
        }
    }

    /**
     * This method simply determines what happens when we interact with the nextMedia button.
     */
    @FXML
    void nextMedia(ActionEvent event)
    {
        mediaSkip();
    }

    /**
     * This method allows us to skip to the next song.
     * The if statement is checking if we are at the end of our list or not and allowing us to loop the "playlist" infinitely.
     */
    private void mediaSkip()
    {
        if(songNumber < songs.size() - 1)
        {
            songNumber++;
            changeMediaPlayer();
        }
        else
        {
            songNumber = 0;
            changeMediaPlayer();
        }
    }

    /**
     * This method allows us to skip to the previous song.
     * We are checking if we are at the end of our list or not and allowing us to loop the "playlist" infinitely.
     * We also use this method to note our current progress in the song, and if we hit previous past 5 seconds it will begin the song again.
     * If the user then hits previous a second time it will go back a song.
     */
    @FXML
    void previousMedia(ActionEvent event) {
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

    @FXML
    void ClickNewPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewPlaylistView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New Playlist");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void ClickEditPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylistView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Edit playlist");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void ClickNewSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewSongView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New song");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void ClickEditSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditSongView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Edit song");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is what we use to determine the passed time since the song began, and how much time is left.
     * We compare our current time and our end time to get our current progress percentage.
     * if the progress reaches 100%, the timer is reset and our next song plays.
     */
    private void beginTimer()
    {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);

                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * resets our timer and lets us our program know we are no longer running a song.
     */
    private void cancelTimer(){
        running = false;
        timer.cancel();
    }

    /**
     * This method is used to reduce repeating ourselves in our skip and previous song functions, it is the bulk of what
     * actually changes our song.
     * First it stops the song and resets our timer. Then it retrieves a new song changes our label to match it and finally plays the new song.
     */
    private void changeMediaPlayer()
    {
        if(running) {
            mediaPlayer.stop();
            cancelTimer();
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playSong();
        }
        else {
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        }
    }

    /**
     * This method is the logic of our speed change.
     */
    private void playbackSpeed()
    {
        for(int i = 0; i < speeds.length; i++) {
            speedBox.getItems().add(Integer.toString(speeds[i])+"%");
        }
        speedBox.setOnAction(this::changeSpeed);
    }

    /**
     * This method is what we use to let the user set the playback speed of our song.
     */
    public void changeSpeed(ActionEvent event) {
        if(speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        }
        else {
            mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
        }
    }

    /**
     * This method determines how our volume slider works.
     */
    public void volume()
    {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    @FXML
    void clickSearch(ActionEvent event) {

    }
}

