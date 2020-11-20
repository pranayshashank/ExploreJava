package in.pks.journal.java.pattern.observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class EventSubscriber implements Observer {

    private final String name;

    public EventSubscriber (String name){
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(this.name + " [" + ((Event)o).getId() + "] [" + System.nanoTime() + "] read: " + ((Event)o).feed());
    }

    public String getName() {
        return name;
    }
}
