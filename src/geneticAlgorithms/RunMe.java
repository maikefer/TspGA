package geneticAlgorithms;

/**
 * This is the runnable class who starts the tsp-solution-finding process.
 * @author Maike Rees, November 2015
 *
 *TODO check for not valid parameters
 */
public class RunMe {
	
	/**
	 * You can put in all parameters. This method starts the solution-finding-process for the tsp.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Citysize must be smaller than 100!
		
		// a city will be represented by a number
		int popSize = 90; // not more than 100! TODO: ajust that
		int genSize = 800;
		float crossoverRate = 0.8F; //tODO doesn't work for < 1
		float mutationRate = 0.1F;
		float elitismRate = 0.05F;
		
		TravelingSalesmanProblem tsp = new TspBerlin(popSize, genSize, crossoverRate, mutationRate, 
															elitismRate);
		
		long time = System.currentTimeMillis();
		tsp.findSolution();
		time = System.currentTimeMillis() - time;
		System.out.println("That took: " +  time + " milliseconds");
		System.out.println(tsp.solution.toString());
		System.out.println(tsp.solution.getFitness());
		System.out.println(tsp.solution.getRealFitness());
		
//		TravelingSalesmanProblem tsp2 = new EasyTsp(7, popSize, genSize);
		
	}

}
