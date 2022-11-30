package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyTunesMainWindowController {
    public void clickSearch(ActionEvent actionEvent) {
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
