package in.pks.journal.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleJavaFxApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
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

    private Scene getMainScene(){

        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Hello World!"));

        Scene scene = new Scene(vBox);

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
