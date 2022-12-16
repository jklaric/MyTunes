package gui.controller;

import gui.bll.Song;
import gui.datasources.databaseconnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditSongViewController {

    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;

    @FXML
    private TextField newArtist;

    @FXML
    private ComboBox<String> newCategory;

    @FXML
    private TextField newFile;

    @FXML
    private TextField newTime;

    @FXML
    private TextField newTitle;

    @FXML
    private TextField songID;

    @FXML
    void editSong(ActionEvent event) throws SQLException {

        Song song =new Song();

        song.setTitle(newTitle.getText());
        song.setArtist(newArtist.getText());
        song.setTime(newTime.getText());
        song.setCategory(newCategory.getValue());
        song.setFilePath(newFile.getText());

        con= DatabaseConnection.getConnection();
        String sql="update songs set title=?,artist=?,category=?,time=?,filePath=? where id=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,song.getTitle());
        pstmt.setString(2,song.getArtist());
        pstmt.setString(3,song.getCategory());
        pstmt.setString(4, song.getTime());
        pstmt.setString(5, song.getFilePath());
        pstmt.setInt(6, Integer.parseInt(songID.getText()) );

        rs=pstmt.executeQuery();



    }
}
