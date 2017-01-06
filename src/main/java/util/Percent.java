package util;

public class Percent {

    private final double percentage;

    public Percent(double percentage) {
        if (percentage < 0.0 || percentage > 1.0) {
            throw new IllegalArgumentException("The percentage has to be between 0.0 and 1.0! ");
        }
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}
