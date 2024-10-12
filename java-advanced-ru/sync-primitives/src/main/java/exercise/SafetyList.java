package exercise;

import java.util.ArrayList;
import java.util.List;

class SafetyList {
    // BEGIN
    private List<Integer> safetyList = new ArrayList<>();

    public synchronized void add(Integer value) {
        safetyList.add(value);
    }

    public Integer get(int indexElement) {
        return safetyList.get(indexElement);
    }

    public Integer getSize() {
        return safetyList.size();
    }
    // END
}
