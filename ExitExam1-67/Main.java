import controller.CowStrikeController;
import model.Cow;
import model.Machine;
import view.CowStrikeView;

public class Main {
    public static void main(String[] args) {

        // Create 100 cows
        Cow[] cows = new Cow[100];
        for (int i = 0; i < cows.length; i++) {
            int teats = Math.random() < 0.05 ? (int) (Math.random() * 5) + 1 : 4;
            boolean isMale = Math.random() < 0.05; // 5% chance of being male
            int targetMachine = (int) (Math.random() * 10) + 1;
            cows[i] = new Cow(i, teats, isMale, targetMachine);
        }

        // เครื่องรีด 10 เครื่อง
        Machine[] machines = new Machine[10];
        for (int i = 0; i < machines.length; i++) {
            machines[i] = new Machine(i + 1);
        }

        CowStrikeView view = new CowStrikeView();
        CowStrikeController controller = new CowStrikeController(cows, machines, view);

        // start program
        controller.process();
    }
}
