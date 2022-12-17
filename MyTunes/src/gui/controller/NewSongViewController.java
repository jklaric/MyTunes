package gui.controller;

import gui.bll.Song;
import gui.datasources.databaseconnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewSongViewController {


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


    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;

    @FXML
    void saveSong(ActionEvent event) throws SQLException {

        this.title=this.titleInput.getText();
        this.artist=this.artistInput.getText();
        this.category=this.categoryInput.getValue();
        this.time=this.timeInput.getText();
        this.file=this.fileInput.getText();

        Song song=new Song(this.title,this.artist,this.category,this.time,this.file);

        con= DatabaseConnection.getConnection();
        String sql="insert into songs values((select nvl(max(id),0)+1 from song),?,?,?,?,?)";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,song.getTitle());
        pstmt.setString(2,song.getArtist());
        pstmt.setString(3,song.getCategory());
        pstmt.setString(4, song.getTime());
        pstmt.setString(5, song.getFilePath());

        rs=pstmt.executeQuery();
    }

    }
