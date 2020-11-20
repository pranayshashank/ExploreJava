package core.beans;

public interface DisplayUnit {

    String display(String message);

    long getId();


    default void init(){
        System.out.println(String.format("Initializing bean %s and id %d",
                this.getClass().getName(),
                this.getId()));
    }

    default void shutdown(){
        System.out.println(String.format("Shutdown bean %s and id %d",
                this.getClass().getName(),
                this.getId()));
    }
}
