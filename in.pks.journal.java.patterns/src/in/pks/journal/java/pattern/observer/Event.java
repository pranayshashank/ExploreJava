package in.pks.journal.java.pattern.observer;

import java.util.Objects;
import java.util.Observable;

public class Event extends Observable {

    private final String id;
    private final StringBuilder newsFeed = new StringBuilder();

    public Event(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String feed(){
        return this.newsFeed.toString();
    }

    public void updateFeed(String data){
        this.newsFeed.append(data);
        setChanged();
        notifyObservers();
    }

    public void subscribe(EventSubscriber... observers){
        if (Objects.nonNull(observers)){
            for(EventSubscriber observer : observers){
                this.addObserver(observer);
                //System.out.println(observer.getName() + " : Subscribed for: " + this.getId());
            }
            System.out.println();
        }
    }

    public void unsubscribe(EventSubscriber... observers){
        if (Objects.nonNull(observers)){
            for(EventSubscriber observer : observers){
                this.deleteObserver(observer);
                //System.out.println(observer.getName() + " : Unsubscribed for: " + this.getId());
            }
            System.out.println();
        }
    }
}
