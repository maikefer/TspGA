package geneticAlgorithms;

/**
 * A Tsp just for testing purposes
 * @author Maike
 *
 */
public class EasyTsp extends TravelingSalesmanProblem {


	/**
	 * Constructor for a random generated Tsp
	 * @param dimension
	 * @param popSize
	 * @param genSize
	 */
	public EasyTsp(int dimension, int popSize, int genSize){
		super(dimension, popSize, genSize, 1.0F, 0.0F, 0.0F);
	}
}
