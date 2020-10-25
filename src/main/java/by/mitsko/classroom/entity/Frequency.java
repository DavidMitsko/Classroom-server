package by.mitsko.classroom.entity;

public enum Frequency {
    DAY(1),
    WEEK(7);

    private int amountOfDays;

    Frequency(int amountOfDays) {
        this.amountOfDays = amountOfDays;
    }

    public int getAmountOfDays() {
        return amountOfDays;
    }
}
