package core.beans;

public class CRTDisplay implements DisplayUnit{
    private final String resolution = "600x400";
    private final long id = System.currentTimeMillis();

    @Override
    public String display(String message) {
        return String.format("CRT Displays: %s", message);
    }

    public String getResolution() {
        return resolution;
    }

    public long getId() {
        return id;
    }
}
