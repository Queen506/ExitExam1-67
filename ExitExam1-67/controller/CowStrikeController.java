package controller;

import model.Cow;
import model.Machine;
import model.MachineHead;
import view.CowStrikeView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class CowStrikeController {
    private Cow[] cows;
    private Machine[] machines;
    private CowStrikeView view;
    private Queue<Cow> cowQueue = new LinkedList<>();
    private int milkCollected = 0;
    private int interventions = 0;
    private int cowsProcessed = 0;
    private int problemCows = 0;

    public CowStrikeController(Cow[] cows, Machine[] machines, CowStrikeView view) {
        this.cows = cows;
        this.machines = machines;
        this.view = view;

        // Add cows to the queue
        for (Cow cow : cows) {
            cowQueue.add(cow);
        }
    }

    public void process() {
        // Clean heads and assign cows
        for (Machine machine : machines) {
            for (MachineHead head : machine.getHeads()) {
                if (head.getState().equals("ว่าง") && !cowQueue.isEmpty()) {
                    Cow cow = cowQueue.poll();
                    head.setCow(cow);
                    head.clean();
                }
            }
        }

        // Prepare heads
        for (Machine machine : machines) {
            for (MachineHead head : machine.getHeads()) {
                if (head.getState().equals("กำลังทำความสะอาด")) {
                    head.prepare();
                }
            }
        }

        // Milk heads if at least 4 are ready
        for (Machine machine : machines) {
            if (machine.hasReadyHeads()) {
                for (MachineHead head : machine.getHeads()) {
                    if (head.getState().equals("เตรียมพร้อม")) {
                        head.milk();
                    }
                }
            }
        }

        // Process milking
        List<Cow> newQueue = new ArrayList<>();
        for (Machine machine : machines) {
            for (MachineHead head : machine.getHeads()) {
                if (head.getState().equals("กำลังรีดนม")) {
                    Cow cow = head.getCow();
                    if (cow != null) {
                        if (cow.getTargetMachine() == machine.getMachineId()) {
                            milkCollected += 1; // Produce 1 liter of milk
                        } else {
                            cow.setFrustrated(true);
                            milkCollected += 0.5; // Produce 0.5 liters of milk
                        }
                        head.removeCow();
                        cowsProcessed++;
                    }
                }
            }
        }

        // Requeue cows based on their target machine
        while (!cowQueue.isEmpty()) {
            Cow cow = cowQueue.poll();
            int targetMachine = cow.getTargetMachine() - 1;
            boolean placed = false;
            for (int i = 0; i < machines.length; i++) {
                int machineIndex = (targetMachine + i) % machines.length;
                for (MachineHead head : machines[machineIndex].getHeads()) {
                    if (head.getState().equals("ว่าง")) {
                        head.setCow(cow);
                        head.clean();
                        placed = true;
                        break;
                    }
                }
                if (placed)
                    break;
            }
            if (!placed) {
                newQueue.add(cow);
            }
        }
        cowQueue.addAll(newQueue);

        // Check for problematic cows
        problemCows = 0;
        for (Cow cow : cows) {
            if (cow.isProblematic()) {
                problemCows++;
            }
        }

        // Handle interventions
        handleInterventions();

        // Update status and display
        view.displayStatus(milkCollected, interventions, cowsProcessed, problemCows);
    }

    private void handleInterventions() {
        List<Cow> cowsToRemove = new ArrayList<>();
        List<Cow> cowsOnMachines = new ArrayList<>();

        // Check machine heads for cows with insufficient udder count
        for (Machine machine : machines) {
            for (MachineHead head : machine.getHeads()) {
                if (head.getState().equals("กำลังรีดนม")) {
                    if (!head.hasValidUdderForCow()) {
                        Cow cow = head.getCow();
                        if (cow != null) {
                            cowQueue.add(cow);
                            head.removeCow();
                            System.out.println("หัวเครื่องรีด id " + head.getId()
                                    + " เล็งเต้าที่ไม่ถูกต้องสำหรับวัว id " + cow.getId());
                            interventions++;
                        }
                    }
                }
            }
        }

        // Check for male cows in the machines
        for (Machine machine : machines) {
            for (MachineHead head : machine.getHeads()) {
                Cow cow = head.getCow();
                if (cow != null && cow.isMale()) {
                    System.out.println("วัวเพศผู้ id" + cow.getId() + " ถูกนำเข้าสู่เครื่องรีด");
                    head.removeCow();
                    cowQueue.add(cow);
                    interventions++;
                }
            }
        }

        // Check for frustrated cows
        int frustratedCowsCount = 0;
        for (Cow cow : cows) {
            if (cow.isFrustrated()) {
                frustratedCowsCount++;
            }
        }

        if (frustratedCowsCount >= 8) {
            interventions++;
            for (Cow cow : cows) {
                if (cow.isFrustrated()) {
                    cowsToRemove.add(cow);
                }
            }

            for (Cow cow : cowsToRemove) {
                cowQueue.remove(cow);
                for (Machine machine : machines) {
                    for (MachineHead head : machine.getHeads()) {
                        if (head.getCow() != null && head.getCow().equals(cow)) {
                            head.removeCow();
                        }
                    }
                }
            }

            // Move non-frustrated cows back to the queue
            for (Machine machine : machines) {
                for (MachineHead head : machine.getHeads()) {
                    Cow cow = head.getCow();
                    if (cow != null && !cow.isFrustrated()) {
                        cowsOnMachines.add(cow);
                        head.removeCow();
                    }
                }
            }
            cowQueue.addAll(cowsOnMachines);
        }
    }
}
