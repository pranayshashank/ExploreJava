package in.pks.journal.java.aop.dto;

import java.time.LocalDate;

public class SimpleDTO {

    String place;
    LocalDate date;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
