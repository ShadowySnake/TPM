import modList.modifiedOptimistic;
public class TestThread<T> implements Runnable{
    private final modifiedOptimistic<T> listToMod;
    //private final OptimisticList<T> listToMod;
    private final String operation;
    private final T item;

    public TestThread(modifiedOptimistic<T> list, String op, T item){
        this.listToMod = list;
        this.operation = op;
        this.item = item;
    }

    /*public TestThread(OptimisticList<T> list, String op, T item){
        this.listToMod = list;
        this.operation = op;
        this.item = item;
    }*/

    public void run(){
        try {
            if(operation.equals("add")) {
                if (!listToMod.add(item)) throw new Exception("FAILED TO ADD");
            }
                else if(operation.equals("remove")) {
                    if(!listToMod.remove(item)) throw new Exception("FAILED TO REMOVE");
            }
                    else if(!listToMod.contains(item)) throw new Exception("ELEMENT NOT FOUND");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
