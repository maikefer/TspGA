package ga.selection;

import ga.Individual;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TournamentSelection implements SelectionStrategy {

    private final int tournamentNumber;

    public TournamentSelection(int tournamentNumber) {
        this.tournamentNumber = tournamentNumber;
    }

    @Override
    public Pair<Individual, Individual> selectParents(List<Individual> individuals, Random random) {

        Individual winner1 = selectOneIndividual(individuals, random);
        Individual winner2 = selectOneIndividual(individuals, random);

        return new Pair<>(winner1, winner2);
    }

    private Individual selectOneIndividual(List<Individual> individuals, Random random) {
       return IntStream.range(0, tournamentNumber)
                .mapToObj(j -> individuals.get(random.nextInt(individuals.size())))
                .min((x, y) -> x.getFitness() - y.getFitness())
                .orElse(individuals.get(0));
    }
}
