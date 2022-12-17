package gui.controller;

import gui.bll.Playlist;
import gui.datasources.databaseconnection.DatabaseConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewPlaylistViewController implements Initializable{

    public AnchorPane newPlaylistWindow;
    @FXML
    public TextField newPlaylistNamePrompt;
    public Button newPlaylistSave;
    public Button newPlaylistCancel;
    public Text errorOnCreate;

    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;
    /**
     * First we set up our error text, just in case the user finds a way to enter an invalid filename.
     * Next we try and assure that our file can only be created with valid characters by using a text formatter for user input.
     */
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

    /**
     * This method takes the user input and tries to make a new file (playlist) with it.
     * If successful the window closes after the file is created.
     * If unsuccessful the window will return a text error.
     * @param actionEvent
     */
    public void savePlaylist(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) newPlaylistSave.getScene().getWindow();
        String root="MyTunes/src/gui/datasources/playlists";

        String name=newPlaylistNamePrompt.getText();

        // Specify an abstract pathname in the File object.
        // In our case we use the root of the playlist folder, add a separator to make it a proper path and then add the user input.
        File f = new File(root+File.separator + newPlaylistNamePrompt.getText() );
        System.out.println(f.getPath());
        // check if the directory can be created using the specified path name
        if (f.mkdir() == true) {
            stage.close();
        } else {
            errorOnCreate.setText("Can not create directory, please check playlist name and try again.");
        }

        Playlist playlist=new Playlist(name);

        con= DatabaseConnection.getConnection();
        String sql="insert into PlayList values((select nvl(max(id),0)+1 from PlayList),?)";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,playlist.getName());

        rs=pstmt.executeQuery();

    }

    /**
     *
     * @param actionEvent closes the window when the user clicks cancel.
     */
    public void closeNewPlaylistWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) newPlaylistSave.getScene().getWindow();
        stage.close();
    }


}

