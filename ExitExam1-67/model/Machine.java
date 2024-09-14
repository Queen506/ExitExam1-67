package model;

public class Machine {
    private int machineId;
    private MachineHead[] heads;

    public Machine(int machineId) {
        this.machineId = machineId;
        this.heads = new MachineHead[4];
        for (int i = 0; i < heads.length; i++) {
            heads[i] = new MachineHead(machineId * 4 + i + 1); // Unique ID for each head
        }
    }

    public int getMachineId() {
        return machineId;
    }

    public MachineHead[] getHeads() {
        return heads;
    }

    public boolean hasReadyHeads() {
        int readyHeads = 0;
        for (MachineHead head : heads) {
            if (head.getState().equals("เตรียมพร้อม")) {
                readyHeads++;
            }
        }
        return readyHeads >= 4;
    }
}
