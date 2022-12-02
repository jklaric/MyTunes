package gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public Button playButton, previousButton, nextButton;
    @FXML
    private ImageView play_icon, backward_icon;
    @FXML
    private Label songLabel;

    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private ListView songList,songsWithinPlaylist ,playlistList;

    private ObservableList<String> withinPlaylist = FXCollections.observableArrayList();

    private MediaPlayer mediaPlayer;
    private Media media;

    private ArrayList<File> songs;
    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};


    private Timer timer;

    private boolean running;


    /**
     * This Method is creating an arraylist I am using for a mock library until the database is complete.
     * It also activates several methods for us on initialization.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songs = new ArrayList<>();
        File directory = new File("MyTunes/src/gui/music");
        File[] files = directory.listFiles();
        if(files != null){
            Collections.addAll(songs, files);

        }
        createPlaylist();
        mediaSet();
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
        play_icon.setImage(new Image("icons/pause_icon.png"));
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
    void playMedia() {
        if(running)
        {
            pauseMedia();
            play_icon.setImage(new Image("icons/play_icon.png"));

        }
        else
        {
            playSong();

        }
    }

    /**
     * This method simply determines what happens when we interact with the nextMedia button.
     */
    @FXML
    void nextMedia()
    {
        mediaSkip();
        changeMediaPlayer();
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
        }
        else
        {
            songNumber = 0;
        }
    }

    /**
     * This method allows us to skip to the previous song.
     * We are checking if we are at the end of our list or not and allowing us to loop the "playlist" infinitely.
     * We also use this method to note our current progress in the song, and if we hit previous past 5 seconds it will begin the song again.
     * If the user then hits previous a second time it will go back a song.
     */
    @FXML
    void previousMedia() {
        int i = (int) mediaPlayer.getCurrentTime().toSeconds();
        if(songNumber > 0)
        {
            if(i > 3){
                resetMedia();
                backward_icon.setImage(new Image("icons/backward_icon.png"));
            }
            else {
                songNumber--;
                changeMediaPlayer();
            }
        }
        else {
            if(i > 3){
                resetMedia();
                backward_icon.setImage(new Image("icons/backward_icon.png"));
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
     * This method is also used to change our previous button icon according to the function it has.
     */
    private void beginTimer()
    {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);

                if(current > 3){
                    backward_icon.setImage(new Image("icons/reset_icon.png"));
                }

                if (current / end == 1) {

                    mediaPlayer.stop();
                    cancelTimer();

                    mediaSkip();

                    songLabel.setText(songs.get(songNumber).getName());
                    playSong();

                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * stops our timer and allows our program to know we are no longer running a song using the running boolean.
     */
    private void cancelTimer(){
        running = false;
        timer.cancel();

    }

    /**
     * This method is used to implement the mediaSet(); method after initialization
     * First it checks to see if the player is running, if so it stops the song and resets our timer.
     * Then it retrieves a new song changes our label to match it and finally plays the new song.
     */
    private void changeMediaPlayer()
    {
        if(running) {
            mediaPlayer.stop();
            cancelTimer();
        }
        mediaSet();
        playSong();
    }

    private void mediaSet()
    {
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());
    }
    /**
     * This method is the logic of our speed change.
     */
    private void playbackSpeed()
    {
        for (int speed : speeds) {
            speedBox.getItems().add(speed + "%");
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
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(volumeSlider.getValue() * 0.01));
    }

    /**
     * This method turns our music folder into a playlist and then uses our songsinplaylist listview to display it.
     */
    private void createPlaylist()
    {
        File directory = new File("MyTunes/src/gui/music");
        String[] inPL;
        inPL = directory.list();
        for (String inPlaylist: inPL
        ) {withinPlaylist.add(inPlaylist);

        }
        songsWithinPlaylist.setItems(withinPlaylist);
    }

    @FXML
    void clickSearch(ActionEvent event) {

    }
}

