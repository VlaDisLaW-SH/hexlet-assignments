package exercise;

// BEGIN
public class MinThread extends Thread {

    private final int[] numbers;
    private static Integer minNumber = 0;

    public MinThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public void run() {
        Integer currentNumber = this.numbers[0];

        for (int i = 1; i < this.numbers.length; i++) {
            if (this.numbers[i] < currentNumber) {
                currentNumber = this.numbers[i];
            }
        }
        minNumber = currentNumber;
    }

    public Integer getMinNumber() {
        return minNumber;
    }
}
// END
