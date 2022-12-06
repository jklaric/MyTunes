import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.datasources.databaseconnection.DatabaseConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {


        public static void main(String[] args){
            Main.launch();
        }

        /**
         * Added database connection in Main.
         * All it at the moment is that it opens a connection, checks if it works, and then closes it.
        **/
        @Override
        public void start(Stage primaryStage) throws Exception {
            DatabaseConnection DatabaseConnector = new DatabaseConnection();

            try (Connection connection = DatabaseConnector.getConnection()){
                System.out.println("Is it open?" + !connection.isClosed());
                if (connection.isClosed())
                    System.out.println("Yes");
                else
                    System.out.println("No");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/view/MyTunesMainWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("My Tunes");
            primaryStage.setScene(scene);
            primaryStage.show();

            /**
             * This will allow us to close the program without an error dump.
             */
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });

        }


    }

