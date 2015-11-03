package geneticAlgorithms;

import java.util.Random;

/**
 * This is the runnable class who starts the tsp-solution-finding process.
 * @author Maike Rees, November 2015
 *
 *TODO check for not valid parameters
 */
public class RunMe {
	
	public static final int seed = 500;
	public static final Random random = new Random(seed);
	
	private static int popSize = 100; 
	private static int genSize = 400;
	private static float crossoverRate = 0.8F;
	private static float mutationRate = 0.0F;
	private static float elitismRate = 0.02F;
	
	/**
	 * You can put in all parameters. This method starts the solution-finding-process for the tsp.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Citysize must be smaller than 100! (is that true?)
	
		TravelingSalesmanProblem tsp = new TspBerlin(popSize, genSize, crossoverRate, mutationRate, 
															elitismRate);
		
//		TravelingSalesmanProblem tsp2 = new TspMultipleCities(10, popSize, genSize, crossoverRate, 
																	// mutationRate, elitismRate);
		

				
		
		long time = System.currentTimeMillis();
		tsp.findSolution();
		time = System.currentTimeMillis() - time;
		
		consoleOutput();
		
		System.out.println("It took " + time + " milliseconds");
		System.out.println("The fitness is: " + tsp.solution.getFitness());
		System.out.println("And the solution is: " + tsp.solution.toString());

	}
	
	private static void consoleOutput(){
		System.out.println("I found a solution for the Traveling Salesman Problem: ");
		System.out.println("Parameters: ");
		System.out.println("Seed: " + seed);
		System.out.println("Population Size: " + popSize);
		System.out.println("Generation Span: " + genSize);
		System.out.println("Crossover Rate: " + crossoverRate);
		System.out.println("Mutation Rate: " + mutationRate);
		System.out.println("Elitism Rate: " + elitismRate);
	}

}
