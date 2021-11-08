package sbpct_A;

public class Person implements Runnable {
    private final String personName;
    private Manager currentManager;

    public Person(String givenName) {
        this.personName = givenName;
    }

    public void setManager(Manager mht) {
        this.currentManager = mht;
    }

    private boolean play() throws InterruptedException {
        Food portions = currentManager.getFoodPortions();
        if (this.currentManager.canEnd()) {
            System.out.println("Chef thread Stopped!");
            return false;
        }
        if (this.personName.equals("Chef") && portions.getCanFill()) portions.fillPortion();
        if (!this.personName.equals("Chef") && !portions.getCanFill() && (portions.getCurrentFoodPortion() > 0)) {
            portions.reducePortion();
            this.currentManager.increaseEnd();
            System.out.println(this.personName + " thread Stopped!");
            return false;
        }
        if (!this.personName.equals("Chef") && !portions.getCanFill() && (portions.getCurrentFoodPortion() == 0))
            portions.setCanFill();
        return true;
    }

    public void run() {
        try {
            while (true) {
                //System.out.println(this.personName);
                if (!this.play()) break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
