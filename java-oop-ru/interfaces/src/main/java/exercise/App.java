package exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// BEGIN
public class App {
    public static List buildApartmentsList(List<Home> apartments, int n) {
        apartments.sort(Comparator.comparingDouble(Home::getArea));
        return new ArrayList<>(apartments.subList(0, n));
    }
}
// END
