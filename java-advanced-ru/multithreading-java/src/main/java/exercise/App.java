package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {
        Map<String, Integer> resultMap = new HashMap<>();
        LOGGER.setLevel(Level.ALL);

        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);

        maxThread.start();
        try {
            maxThread.run();
            resultMap.put("max", maxThread.getMaxNumber());
            LOGGER.info("Поток " + maxThread.getName() + " выполнил операцию");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            minThread.join();
            minThread.run();
            resultMap.put("min", minThread.getMinNumber());
            LOGGER.info("Поток " + minThread.getName() + " выполнил операцию");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /*public static void main(String[] args) {
        int[] nums = {-1, -2, -22, 7, 30, -15, -20};
        System.out.println(getMinMax(nums));
    }*/
    // END
}
