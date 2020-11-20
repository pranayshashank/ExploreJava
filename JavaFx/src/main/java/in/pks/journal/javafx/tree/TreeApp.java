package in.pks.journal.javafx.tree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class TreeApp extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setAlwaysOnTop(true);  // makes sure that your fx application remains on top of any other application on your desktop

        primaryStage.setTitle("My first JavaFx App.");

        primaryStage.setScene(getMainScene());  // you need to set up a scene.

        primaryStage.setMinHeight(200d);
        primaryStage.setMinWidth(300d);

        primaryStage.setOnCloseRequest((event)->{
            System.out.println("Window closing event: " + event.getEventType());
        });

        primaryStage.show();
    }

    private Scene getMainScene() {
        FXMLLoader loader = new FXMLLoader();

        try {
            SplitPane pane = loader.load(getClass().getClassLoader().getResourceAsStream("tree.fxml"));

            return new Scene(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
