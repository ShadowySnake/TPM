package sbpct_B;

public class Starter {
    public static void main(String[] args) throws InterruptedException {
        try {
            Manager manager = new Manager(3, 500, 3);
            manager.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
