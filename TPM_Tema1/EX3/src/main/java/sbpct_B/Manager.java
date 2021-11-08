package sbpct_B;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    private final int maximumEat;
    private final Food foodPortion;
    private final List<Person> personList = new ArrayList<>();
    private final List<Integer> portionsEaten = new ArrayList<>();
    private final ExecutorService executors;

    public Manager(int portionsNumber, int barbarianNumbers, int maxPortionsToEat) {
        this.foodPortion = new Food(portionsNumber);
        this.maximumEat = maxPortionsToEat;
        Person chef = new Person("Chef");
        addPerson(chef);
        for (int i = 0; i < barbarianNumbers; i++) {
            String name = "Salbatic" + i;
            addPerson(new Person(name));
        }
        this.executors = Executors.newFixedThreadPool(barbarianNumbers + 1);
    }

    public void addPerson(Person player) {
        personList.add(player);
        portionsEaten.add(0);
        player.setManager(this);
    }

    public int getMinimumEat() {
        int min = this.maximumEat;
        for (int i = 1; i < portionsEaten.size(); i++) {
            if (min > portionsEaten.get(i)) min = portionsEaten.get(i);
        }
        return min;
    }

    public int getSpecificEat(Person person) {
        return portionsEaten.get(personList.indexOf(person));
    }

    public synchronized void increaseSpecificEat(Person person) {
        portionsEaten.set(personList.indexOf(person), getSpecificEat(person) + 1);
    }


    public int getMaximumEat() {
        return this.maximumEat;
    }

    public synchronized Food getFoodPortions() {
        return foodPortion;
    }


    public void start() throws InterruptedException {
        for (Person p : personList) {
            executors.execute(p);
        }
        executors.shutdown();
        while (!executors.isTerminated()) {
            //System.out.println("Awaiting termination...");
            //trebuie asteptata terminarea threadurilor
        }
    }

}
