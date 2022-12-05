package gui.controller;


import gui.bll.PlayBack;

import gui.dal.DataAccess;
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
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class MyTunesMainWindowController implements Initializable {
    @FXML
    public Button playButton, previousButton, nextButton, searchButton;
    @FXML
    private ImageView play_icon, backward_icon, search_icon;
    @FXML
    private Label songLabel;
    @FXML
    public ProgressBar songProgressBar;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private ListView songList,songsWithinPlaylist ,playlistList;
    @FXML
    private TextField filterField;

    PlayBack playBack;
    DataAccess dataaccess;



    private Timer timer;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private double current;
    private double end;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //get PlayBack model from bll
        playBack = new PlayBack();
        dataaccess = new DataAccess();

        playBack.fileToPlaylist();
        playBack.mediaSet();
        playbackSpeed();
        volume();
        settingsAssurance();
        dataaccess.makeLibraries();
        listViewSetup();
    }

    /**
     * Fills our list views on application start.
     */
    private void listViewSetup()
    {
        playlistList.setItems(dataaccess.returnPlaylistList());
        songList.setItems(dataaccess.returnLibraryView());
        songsWithinPlaylist.setItems(dataaccess.returnPlaylist());
    }

    /**
     * This is what is used when we click the play/pause button.
     * First it checks if the media is running.
     * If it is, it pauses it and changes the icon to the play icon.
     * Then it clears our timer, and finally pauses our song.
     *
     * If it is not running, it simply starts our timer and plays the current song.
     */
    @FXML
    void playMedia() {
        if(playBack.isRunning()){
            play_icon.setImage(new Image("icons/play_icon.png"));
            timer.cancel();
            playBack.pauseMedia();

        }
        else{
            beginTimer();
            playBack.playSong();
            play_icon.setImage(new Image("icons/pause_icon.png"));
        }
        settingsAssurance();
    }

    /**
     * This method simply determines what happens when we interact with the nextMedia button.
     * We simply reset our timer and skip to the next file in the playlist.
     */
    @FXML
    void nextMedia()
    {
        timer.cancel();
        playBack.mediaSkip();
        beginTimer();
        settingsAssurance();
    }

    /**
     * This method allows us to skip to the previous song.
     * First we stop our timer, then we get the previous song in the list or reset the song.
     * It then resets our progressbar, and begins our timer for the new song.
     */
    @FXML
    void previousMedia() {
        timer.cancel();
        playBack.mediaBack();
        beginTimer();
        settingsAssurance();
    }

    /**
     * This method determines how our volume slider works.
     */
    public void volume()
    {
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> playBack.mediaPlayerAccess().setVolume(volumeSlider.getValue() * 0.01));
    }

    /**
     * This method is the logic of our speed change.
     * It changes our speed percentage based off our array of speeds.
     */
    public void playbackSpeed()
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
            playBack.mediaPlayerAccess().setRate(1);
        }
        else {
            playBack.mediaPlayerAccess().setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
        }
    }

    /**
     * This method is important for some of our extra functions.
     * The timer assures our progress bar works properly, and changes our previous button to replay button based on time.
     * Finally, it will automatically skip to the next song when one finishes.
     */
    public void beginTimer()
    {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {

                current = playBack.mediaPlayerAccess().getCurrentTime().toSeconds();
                end = playBack.mediaAccess().getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if(current > 3){
                    backward_icon.setImage(new Image("icons/reset_icon.png"));
                }
                else
                {
                    backward_icon.setImage(new Image("icons/backward_icon.png"));
                }

                if (current / end == 1) {
                    playBack.mediaSkip();
                    settingsAssurance();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * This method assures that our volume, playback speed, and song label all match user settings at all times.
     */
    private void settingsAssurance()
    {
        playBack.mediaPlayerAccess().setVolume(volumeSlider.getValue() * 0.01);
        songLabel.setText(playBack.returnSongLabel());
        changeSpeed(null);
    }

    @FXML
    void clickSearch(ActionEvent event) {
    }


    /**
     *opens a new window to create a playlist*/
    @FXML
    void ClickNewPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewPlaylistView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New Playlist");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *opens a new window to edit a playlist*/
    @FXML
    void ClickEditPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylistView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Edit playlist");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens a new window to create a new song*/
    @FXML
    void ClickNewSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewSongView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New song");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens a window to edit a song*/
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
     * refresh button for playlist list, removed function after refactoring. May implement again if list does not update automatically.
     * @param actionEvent
     */
    public void refreshPlaylists(ActionEvent actionEvent) {
    }

    /**
     * opens a window to let the user decide if they want to permanently delete a song/playlist*/

    public void ClickDeleteOptionsView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/DeleteOptionsView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Delete item");
        stage.setScene(scene);
        stage.show();
    }

    public void FilterSongs(){
        if (searchButton.isPressed()) {
            search_icon.setImage(new Image("icons/reset_icon.png"));
            songList.setItems(null);
            String filterParameter = filterField.getText();
            songList.setItems(dataaccess.returnFilteredSongs(filterParameter));
        }
    }
}

