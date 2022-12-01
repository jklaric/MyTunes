package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MyTunesMainWindowController {
    @FXML
    private ImageView up_icon;
    @FXML
    private ImageView down_icon;
    @FXML
    private ImageView search_icon;
    @FXML
    private ImageView left_icon;
    @FXML
    private ImageView backward_icon;
    @FXML
    private ImageView play_icon;
    @FXML
    private ImageView forward_icon;
    @FXML
    private ImageView volume_icon;


    @FXML
    public void initialize() {
        up_icon.setImage(new Image("icons/up_icon.png"));
        down_icon.setImage(new Image("icons/down_icon.png"));
        search_icon.setImage(new Image("icons/search_icon.png"));
        left_icon.setImage(new Image("icons/left_icon.png"));
        backward_icon.setImage(new Image("icons/backward_icon.png"));
        play_icon.setImage(new Image("icons/play_icon.png"));
        forward_icon.setImage(new Image("icons/forward_icon.png"));
        volume_icon.setImage(new Image("icons/volume_icon.png"));
    }

    public void ClickNewPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewPlaylistView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New Playlist");
        stage.setScene(scene);
        stage.show();
    }

    public void ClickEditPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylistView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Edit playlist");
        stage.setScene(scene);
        stage.show();
    }

    public void ClickNewSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/NewSongView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("New song");
        stage.setScene(scene);
        stage.show();
    }

    public void ClickEditSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditSongView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Edit song");
        stage.setScene(scene);
        stage.show();
    }
}
