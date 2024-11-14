public class Slot {
    private String name;
    private Integer hour;

    private boolean free;


    public void setFree(boolean free) {
        this.free = free;
    }
    public boolean getFree() {
        return this.free;
    }

    public Integer getHour() {
        return this.hour;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
}
