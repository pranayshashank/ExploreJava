package in.pks.journal.javafx.tree;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TreeController {

    @FXML private TreeView<TreeConnection> connectionTree;


    @FXML private void initialize(){
        TreeItem<TreeConnection> conn1 = new TreeItem<>(new TreeConnection("Connection 1"));
        Label conn1Label = new Label();
        TreeItem<TreeConnection> conn2 = new TreeItem<>(new TreeConnection("Connection 2"));
        TreeItem<TreeConnection> conn3 = new TreeItem<>(new TreeConnection("Connection 3"));

        this.connectionTree.setRoot(new TreeItem("Root"));
        this.connectionTree.getRoot().getChildren().addAll(conn1, conn2, conn3);


    }
}
