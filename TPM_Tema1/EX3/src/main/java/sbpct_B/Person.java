package sbpct_B;

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
        //System.out.println(currentManager.getMinimumEat());
        if (currentManager.getMinimumEat() == currentManager.getMaximumEat()) {
            System.out.println("Chef thread stopped!");
            return false;
        }
        if (this.personName.equals("Chef") && portions.getCanFill()) portions.fillPortion();
        if (!this.personName.equals("Chef") && !portions.getCanFill() && (portions.getCurrentFoodPortion() > 0)) {
            if (currentManager.getSpecificEat(this) == currentManager.getMinimumEat()) {
                portions.reducePortion();
                currentManager.increaseSpecificEat(this);
                System.out.println(this.personName + " ate for the " + currentManager.getSpecificEat(this) + " time");
                if (currentManager.getSpecificEat(this) == currentManager.getMaximumEat()) {
                    System.out.println(this.personName + " thread Stopped!");
                    return false;
                }
            }
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
