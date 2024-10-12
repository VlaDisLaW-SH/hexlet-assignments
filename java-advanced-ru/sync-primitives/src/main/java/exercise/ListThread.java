package exercise;

// BEGIN
public class ListThread extends Thread {

    private SafetyList list;

    public ListThread(SafetyList list) {
        this.list = list;
    }

    @Override
    public void run() {

        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1);
                list.add((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// END
