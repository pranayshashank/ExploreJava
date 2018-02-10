package in.pks.journal.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.locks.ReentrantLock;

public class MultiSceneJavaFxApplication extends Application {

    private Scene scene1;
    private Scene scene2;
    private final MenuBar menuBar = new MenuBar();
    private boolean isInitializedMenu = false;
    private ReentrantLock LOCK = new ReentrantLock();

    @Override
    public void start(Stage primaryStage) throws Exception {

        createScene1();
        createScene2();

        primaryStage.setScene(scene1);

        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(350);

        initMenuBar(primaryStage);

        primaryStage.show();
    }

    private void createScene1(){
        scene1 = new Scene(new VBox(), 400, 350);
        ((VBox)scene1.getRoot()).setStyle("-fx-background-color: #34f3a1;");
    }

    private void createScene2(){
        VBox root = new VBox();

        root.setStyle("-fx-background-color: #a13d96;");
        root.getChildren().add(new Label("This is VBox layout with MenuBar."));
        scene2 = new Scene(root, 400, 350);
    }

    private void initMenuBar(Stage primaryStage){
        try{
            LOCK.lock();
            if(!isInitializedMenu){
                Menu menu = new Menu("Switch Scene..");

                MenuItem item1 = new MenuItem("Scene-1");
                MenuItem item2 = new MenuItem("Scene-2");

                item1.setOnAction((event)->{
                    primaryStage.setScene(scene1);
                    //((GridPane)scene2.getRoot()).getChildren().remove(menuBar);
                    if(!scene1.getRoot().getChildrenUnmodifiable().contains(menuBar)){
                        ((VBox)scene1.getRoot()).getChildren().addAll(menuBar);
                    }
                });

                item2.setOnAction((event)->{
                    primaryStage.setScene(scene2);
                    //((VBox)scene1.getRoot()).getChildren().remove(menuBar);
                    if(!scene2.getRoot().getChildrenUnmodifiable().contains(menuBar)){
                        ((VBox)scene2.getRoot()).getChildren().add(0, menuBar);
                    }
                });

                menu.getItems().addAll(item1, item2);

                menuBar.getMenus().addAll(menu);
                menuBar.setVisible(true);

                ((VBox)scene1.getRoot()).getChildren().addAll(menuBar);
            }
        } finally {
            LOCK.unlock();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}