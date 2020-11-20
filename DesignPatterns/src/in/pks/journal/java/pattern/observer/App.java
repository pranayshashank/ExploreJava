package in.pks.journal.java.pattern.observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<Event> eventList = new ArrayList<>();
        for (int i=1; i<=10; i++){
            if (i<=4){
                eventList.add(new Event("SPORT_NEWS_" + i));
            } else if (i>4 && i <=8){
                eventList.add(new Event("TECH_NEWS_" + i));
            } else {
                eventList.add(new Event("POLITICS_NEWS_" + i));
            }
        }
        System.out.println("Events created.");


        List<EventSubscriber> subscriberList = new ArrayList<>();
        EventSubscriber eventSubscriberA = new EventSubscriber("Pranay");
        EventSubscriber eventSubscriberB = new EventSubscriber("Ishy");
        EventSubscriber eventSubscriberC = new EventSubscriber("Ekta");

        eventList.get(0).subscribe(eventSubscriberC, eventSubscriberB, eventSubscriberB);
        eventList.get(1).subscribe(eventSubscriberC, eventSubscriberB, eventSubscriberA);
        eventList.get(2).subscribe(eventSubscriberA, eventSubscriberC);
        eventList.get(3).subscribe(eventSubscriberA, eventSubscriberB, eventSubscriberC);
        eventList.get(4).subscribe(eventSubscriberC);
        eventList.get(5).subscribe(eventSubscriberB);
        eventList.get(6).subscribe(eventSubscriberB, eventSubscriberC);
        eventList.get(7).subscribe(eventSubscriberA, eventSubscriberB);
        eventList.get(8).subscribe(eventSubscriberA);
        eventList.get(9).subscribe(eventSubscriberA, eventSubscriberC);

        for (int i=0; i<10; i++){
            if (i%2==0){
                eventList.get(i).updateFeed("news for " + (i+1) + " @ " + LocalDateTime.now());
            } else {
                eventList.get(i).updateFeed("something added @ " + LocalDateTime.now());
            }
        }

        eventList.get(0).unsubscribe(eventSubscriberC);
        eventList.get(0).updateFeed("\n A breaking news is added.");
    }
}
