package gui.controller;

<<<<<<< Updated upstream
import gui.bll.Song;
import gui.datasources.databaseconnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewSongViewController {
    @FXML
    private Button fileChooseButton;
    //public javafx.scene.control.TextField artistInput;
    //public TextField titleInput;
    //public TextField timeInput;
    //public TextField fileInput;
    //public ComboBox<String> categoryInput;
    @FXML
    private TextField artistInput;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private TextField fileInput;

    @FXML
    private TextField timeInput;

    @FXML
    private TextField titleInput;

    private String title;

    private String artist;

    private String category;

    private String time;

    private String file;



    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    @FXML
    void saveSong(ActionEvent event) throws SQLException {

        this.title = this.titleInput.getText();
        this.artist = this.artistInput.getText();
       this.category = this.categoryInput.getValue();
        this.time = this.timeInput.getText();
        this.file = this.fileInput.getText();

        Song song = new Song(this.title, this.artist, this.category, this.time, this.file);

        con = DatabaseConnection.getConnection();
        String sql = "insert into songs values((select nvl(max(id),0)+1 from song),?,?,?,?,?)";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, song.getTitle());
        pstmt.setString(2, song.getArtist());
        pstmt.setString(3, song.getCategory());
        pstmt.setString(4, song.getTime());
        pstmt.setString(5, song.getFilePath());

        rs = pstmt.executeQuery();
    }
/**
 * Method to open file explorer and choose a song file when a new button is pressed */
    public void chooseFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        setFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());
        String selectedFile = file.getPath();
        fileInput.setText(selectedFile);

    }
/**
 * Mehod to configure the file chooser and select which file type are accepted*/
    private static void setFileChooser(FileChooser fileChooser) {

        fileChooser.setTitle("Select song");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("WAV", "*.wav")
        );
    }

    /**
     * Method that closes the window when cancel button is pressed (no song created)
     * */
    public void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
=======
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewSongViewController implements Initializable {
    @FXML
    public ComboBox genreComboBox;
    public Button moreButton;

    ObservableList<String> genreList = FXCollections.observableArrayList("Pop", "EDM", "Rock", "Rap", "Metal",
            "Reggae", "Experimental", "Dance", "Jazz", "Blues", "Traditional", "Folk", "Classical");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SetupComboBox();

    }

    public void SetupComboBox(){
        genreComboBox.setValue("Select genre");
        genreComboBox.setItems(genreList);
    }

    public void CreateNewGenre(){
        TextInputDialog genrePopup = new TextInputDialog();
        genrePopup.setHeaderText("Enter genre name!");
        genrePopup.setContentText("Genre:");
        Optional<String> input = genrePopup.showAndWait();
        if (input.isPresent()){
            genreList.add(input.get());
            genreComboBox.setItems(genreList);
        }
>>>>>>> Stashed changes
    }
}


