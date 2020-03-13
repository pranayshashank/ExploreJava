package in.pks.journal.javafx.tree;


import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class TreeConnection extends HBox {

    private boolean active = false;

    private final Label name;

    public TreeConnection (String name){
        this.name = new Label(name);
        this.getChildren().addAll(this.name);
        this.name.setStyle("-fx-border-color: aqua; -fx-border-width: 0 0 1 0;");

        ContextMenu addMenu = new ContextMenu();
        MenuItem set = new MenuItem("Connect");
        MenuItem reset = new MenuItem("Disconnect");
        addMenu.getItems().addAll(set, reset);


    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name.getText();
    }
}
