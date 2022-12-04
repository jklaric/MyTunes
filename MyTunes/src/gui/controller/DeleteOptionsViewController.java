package gui.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;

public class DeleteOptionsViewController {


    /**
     * Method that deletes a selected song/playlist
     * */
    public void ClickFinalDelete(ActionEvent actionEvent) {

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
