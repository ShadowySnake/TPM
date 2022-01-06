import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import modList.modifiedOptimistic;

public class Main {
    public static void main(String[] args){
        //OptimisticList<Integer> list = new OptimisticList<>();
        modifiedOptimistic<Integer> list = new modifiedOptimistic<>();

        //Adding the 1000000 elements
        for (int i=1;i <= 100000; i++){
            list.add(i);
        }

        // Creating the threads that will run an add, remove or contains
        TestThread<Integer> th1 = new TestThread<>(list, "add", 100001);
        TestThread<Integer> th2 = new TestThread<>(list, "add", 100002);
        TestThread<Integer> th3 = new TestThread<>(list, "add", 100003);
        TestThread<Integer> th4 = new TestThread<>(list, "add", 100004);
        //

        //Executing the threads using an executor
        ExecutorService executors = Executors.newFixedThreadPool(4);
        executors.execute(th1);
        executors.execute(th2);
        executors.execute(th3);
        executors.execute(th4);
        executors.shutdown();
        //

        //list.printList();
        // used to check if the list is actually updated; print list only exists in modifiedOptimistic

    }
}
