package gui.controller;

import gui.dal.DataAccess;
import gui.datasources.databaseconnection.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import gui.datasources.databaseconnection.DatabaseConnection;

public class DeleteOptionsViewController {


    /**
     * Method that deletes a selected song/playlist
     * */

    Connection con=null;
    ResultSet rs=null;
    PreparedStatement pstmt=null;

    @FXML
    private TextField songID;


    public void ClickFinalDelete(ActionEvent actionEvent) {

        try {

            con=DatabaseConnection.getConnection();
            String sql="delete from songs where id=?";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(songID.getText()) );
            rs=pstmt.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }



    }

    /**
     * Method that closes the window when cancel button is pressed (and no item deleted)
     * */
    public void ClickCancel(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
