///**
// * A Tsp for testing purposes.
// * It uses real City - Data but only as much as you want it to.
// */
//public class TspMultipleCities implements InitializationStrategy {
//
//    private int amountCities;
//    private int[][] arrayCopy;
//
//    public TspMultipleCities(int amountCities) {
//        this.amountCities = amountCities;
//        this.arrayCopy = new TspBerlin().initialize();
//    }
//
//    @Override
//    public int[][] initialize() {
//        int[][] nodes = new int[arrayCopy.length][arrayCopy[0].length];
//
//        for (int i = 0; i < this.amountCities; i++) {
//            System.arraycopy(arrayCopy[i], 0, nodes[i], 0, 2);
//        }
//        return nodes;
//    }
//}
