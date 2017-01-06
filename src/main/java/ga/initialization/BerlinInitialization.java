package ga.initialization;

import util.City;

import java.util.ArrayList;
import java.util.List;

public class BerlinInitialization implements InitializationStrategy {

    @Override
    public List<City> createCities() {

        List<City> cities = new ArrayList<>();

        cities.add(new City(565, 575));
        cities.add(new City(25, 185));
        cities.add(new City(345, 750));
        cities.add(new City(945, 685));
        cities.add(new City(845, 655));

        cities.add(new City(880, 660));
        cities.add(new City(25, 230));
        cities.add(new City(525, 1000));
        cities.add(new City(580, 1175));
        cities.add(new City(650, 1130));

        cities.add(new City(1605, 620));
        cities.add(new City(1220, 580));
        cities.add(new City(1465, 200));
        cities.add(new City(1530, 5));
        cities.add(new City(845, 680));

        cities.add(new City(725, 370));
        cities.add(new City(145, 665));
        cities.add(new City(415, 635));
        cities.add(new City(510, 875));
        cities.add(new City(560, 365));

        cities.add(new City(300, 465));
        cities.add(new City(520, 585));
        cities.add(new City(480, 415));
        cities.add(new City(835, 625));
        cities.add(new City(975, 580));

        cities.add(new City(1215, 245));
        cities.add(new City(1320, 315));
        cities.add(new City(1250, 400));
        cities.add(new City(660, 180));
        cities.add(new City(410, 250));

        cities.add(new City(420, 555));
        cities.add(new City(575, 655));
        cities.add(new City(1150, 1160));
        cities.add(new City(700, 580));
        cities.add(new City(685, 595));

        cities.add(new City(685, 610));
        cities.add(new City(770, 610));
        cities.add(new City(795, 645));
        cities.add(new City(720, 635));
        cities.add(new City(760, 650));

        cities.add(new City(475, 960));
        cities.add(new City(95, 260));
        cities.add(new City(875, 920));
        cities.add(new City(700, 500));
        cities.add(new City(555, 815));

        cities.add(new City(830, 485));
        cities.add(new City(1170, 65));
        cities.add(new City(830, 610));
        cities.add(new City(605, 625));
        cities.add(new City(595, 360));

        cities.add(new City(1340, 725));
        cities.add(new City(1740, 245));

        return cities;
    }
}
