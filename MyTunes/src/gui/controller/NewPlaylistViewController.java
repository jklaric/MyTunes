package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPlaylistViewController implements Initializable{

    public AnchorPane newPlaylistWindow;
    @FXML
    public TextField newPlaylistNamePrompt;
    public Button newPlaylistSave;
    public Button newPlaylistCancel;
    public Text errorOnCreate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            errorOnCreate = new Text();

        newPlaylistNamePrompt.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\w") || change.getText().equals("")) {
                return change;
            } else {
                change.setText("");
                change.setRange(
                        change.getRangeStart(),
                        change.getRangeStart()
                );
                return change;
            }
        }));
    }


    public void savePlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) newPlaylistSave.getScene().getWindow();
        String root="MyTunes/src/gui/datasources/playlists";

        // specify an abstract pathname in the File object
        File f = new File(root+File.separator + newPlaylistNamePrompt.getText() );
        System.out.println(f.getPath());
        // check if the directory can be created
        // using the specified path name
        if (f.mkdir() == true) {
            stage.close();
        } else {
            errorOnCreate.setText("Can not create directory, please check playlist name and try again.");
        }

    }
    public void closeNewPlaylistWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) newPlaylistSave.getScene().getWindow();
        stage.close();
    }


}

