package view;

public class CowStrikeView {
    public void displayStatus(int milkCollected, int interventions, int cowsProcessed, int problemCows) {
        System.out.println("น้ำนมที่รีดได้แล้ว: " + milkCollected + " ลิตร");
        System.out.println("จำนวนครั้งที่แทรกแซงโปรแกรม: " + interventions);
        System.out.println("จำนวนวัวที่รีดแล้ว: " + cowsProcessed);
        System.out.println("จำนวนวัวที่มีปัญหา: " + problemCows);
    }

}
