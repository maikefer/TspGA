package ga.selection;

import ga.Individual;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public interface SelectionStrategy {

    Pair<Individual, Individual> selectParents(List<Individual> individuals, Random random);

}
