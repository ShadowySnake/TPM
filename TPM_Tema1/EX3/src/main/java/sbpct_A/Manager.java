package sbpct_A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
    private final Food foodPortion;
    private final List<Person> personList = new ArrayList<>();
    //private final List<Thread> personThreads = new ArrayList<>();
    // nu este diferenta mare intre crearea threadurilor manual si folosirea unui thread pool
    private int ended = 0;
    private final ExecutorService executors;

    public Manager(int portionsNumber, int barbarianNumbers){
        this.foodPortion = new Food(portionsNumber);
        Person chef = new Person("Chef");
        addPerson(chef);
        for(int i=0;i<barbarianNumbers;i++){
            String name = "Salbatic" + i;
            addPerson(new Person(name));
        }

       /* for(Person p : personList) {
            this.personThreads.add(new Thread(p));
        }*/

        this.executors = Executors.newFixedThreadPool(barbarianNumbers + 1);
    }

    public void addPerson(Person player) {
        personList.add(player);
        player.setManager(this);
    }

    public synchronized Food getFoodPortions() {
        return foodPortion;
    }

    public synchronized void increaseEnd() {
        this.ended++;
    }

    public synchronized boolean canEnd() {
        return this.ended == this.personList.size() - 1;
    }

    public void start() {
        for (Person p : personList) {
            executors.execute(p);
        }
        executors.shutdown();
        while (!executors.isTerminated()) {
            //System.out.println("Awaiting termination...");
            //trebuie asteptata terminarea threadurilor
        }
        // for-ul de mai sus si while-ul sunt folosite pentru thread pool


        /*for (Thread thread : personThreads){
            thread.start();
        }
        for (Thread thread : personThreads){
            try {
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }*/
        // cele doua foruri de mai sus sunt pentru lansarea cate a unui thread manual
    }

}
