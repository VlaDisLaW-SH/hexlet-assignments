package exercise;

// BEGIN
public class MaxThread extends Thread {

    private final int[] numbers;
    private static Integer maxNumber = 0;

    public MaxThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        Integer currentNumber = this.numbers[0];

        for (int i = 1; i < this.numbers.length; i++) {
            if (this.numbers[i] > currentNumber) {
                currentNumber = this.numbers[i];
            }
        }
        maxNumber = currentNumber;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }
}
// END
