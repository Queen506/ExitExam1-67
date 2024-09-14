package model;

public class MachineHead {
    private int id;
    private String state;
    private Cow cow;

    public MachineHead(int id) {
        this.id = id;
        this.state = "ว่าง";
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Cow getCow() {
        return cow;
    }

    public void setCow(Cow cow) {
        this.cow = cow;
    }

    public void clean() {
        setState("กำลังทำความสะอาด");
    }

    public void prepare() {
        setState("เตรียมพร้อม");
    }

    public void milk() {
        setState("กำลังรีดนม");
    }

    public void removeCow() {
        this.cow = null;
        setState("ว่าง");
    }

    public boolean hasValidUdderForCow() {
        return this.cow != null && this.cow.hasValidUdder((this.id - 1) % 4 + 1);
    }
}
