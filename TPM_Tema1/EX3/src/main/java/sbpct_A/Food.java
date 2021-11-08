package sbpct_A;

public class Food {
    private final int maximumFoodPortion;
    private int currentFoodPortion;
    private boolean canFill;

    public Food(int portionNumber) {
        this.maximumFoodPortion = portionNumber;
        this.currentFoodPortion = portionNumber;
        this.canFill = false;
    }

    public synchronized void reducePortion() {
        if (this.currentFoodPortion > 0) this.currentFoodPortion -= 1;
    }

    public synchronized void fillPortion() {
        if (this.canFill) {
            this.currentFoodPortion = this.maximumFoodPortion;
            this.canFill = false;
        }
    }

    public int getCurrentFoodPortion() {
        return currentFoodPortion;
    }

    public synchronized void setCanFill() {
        this.canFill = true;
    }

    public boolean getCanFill() {
        return this.canFill;
    }
}
