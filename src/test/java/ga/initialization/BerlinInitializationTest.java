package ga.initialization;

import org.junit.Test;
import util.City;

import java.util.List;

import static org.junit.Assert.*;

public class BerlinInitializationTest {

    @Test
    public void testListSizesAreAsExpected() throws Exception {

        BerlinInitialization initialization = new BerlinInitialization();
        List<City> cities = initialization.createCities();

        assertTrue(cities.size() == 52);

        assertTrue(cities.get(51).getX() == 1740);
        assertTrue(cities.get(51).getY() == 245);
    }
}