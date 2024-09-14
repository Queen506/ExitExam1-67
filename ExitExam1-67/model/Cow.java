package model;

public class Cow {
    private int id;
    private int udderCount;
    private boolean isMale;
    private int targetMachine;
    private boolean isFrustrated;

    public Cow(int id, int udderCount, boolean isMale, int targetMachine) {
        this.id = id;
        this.udderCount = udderCount; // จำนวนเต้า
        this.isMale = isMale; // เพศ
        this.targetMachine = targetMachine; // เลขเครื่องรีด
        this.isFrustrated = false; // สถานะ(หงุดหงิดหรือไม่)
    }

    public int getId() {
        return id;
    }

    public int getUdderCount() {
        return udderCount;
    }

    public boolean hasValidUdder(int expectedUdderCount) { // จะเช็คจำนวนเต้า
        return udderCount == expectedUdderCount;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getTargetMachine() {
        return targetMachine;
    }

    public void setFrustrated(boolean frustrated) {
        this.isFrustrated = frustrated;
    }

    public boolean isFrustrated() {
        return isFrustrated;
    }

    public boolean isProblematic() {
        return this.isMale || !this.hasValidUdder(4);
    }
}
