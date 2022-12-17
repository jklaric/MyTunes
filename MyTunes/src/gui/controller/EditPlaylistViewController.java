package gui.controller;

import gui.datasources.databaseconnection.DatabaseConnection;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditPlaylistViewController {

    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;

    @FXML
    private TextField newName;

    @FXML
    private TextField playlistId;


    @FXML
    void changeName(ActionEvent event) throws SQLException {

        con= DatabaseConnection.getConnection();
        String sql="update PlayList set name=? where id=?";
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1,newName.getText());
        pstmt.setInt(6, Integer.parseInt(playlistId.getText()) );

        rs=pstmt.executeQuery();
    }
}


