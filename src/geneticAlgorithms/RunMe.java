package geneticAlgorithms;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * This is the runnable class who starts the tsp-solution-finding process.
 * @author Maike Rees, November 2015
 *
 *TODO check for not valid parameters
 */
public class RunMe {
	
	public static final int seed = 500;
	public static final Random randomGenerator = new Random(seed);
	public static PrintWriter writer;
	
	private static int popSize = 150; 
	private static int genSize = 1000;
	private static float crossoverRate = 1.0F;
	private static float mutationRate = 0.1F;
	private static float elitismRate = 0.09F;
	
	private static TravelingSalesmanProblem tsp;
	
	/**
	 * You can put in all parameters. This method starts the solution-finding-process for the tsp.
	 * @param args
	 */
	public static void main(String[] args) {

//		TravelingSalesmanProblem tsp2 = new TspMultipleCities(7, popSize, genSize, crossoverRate, 
//				mutationRate, elitismRate);
		
		
		for (int i = 1; i < 6; i++) {
			newWriter(i);
			solve();
			writer.close();
		}
	}
	
	/**
	 * This method contains the orders for the console Output after the tsp is solved
	 */
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

	/**
	 * Prints the transfered String in the current file
	 * @param word
	 */
	public static void printInFile(String word) {
		writer.println(word);
	}
	
	/**
	 * Solves the TspBerlin
	 */
	public static void solve(){

		tsp = new TspBerlin(popSize, genSize, crossoverRate, mutationRate, 
															elitismRate);
		
		long time = System.currentTimeMillis();
		tsp.findSolution();
		time = System.currentTimeMillis() - time;
		
		consoleOutput();
		
		System.out.println("It took " + time + " milliseconds");
		System.out.println("The fitness is: " + tsp.solution.getFitness());
		System.out.println("And the solution is: " + tsp.solution.toString());
		
	}
	
	/**
	 * Creates a new PrintWriter with a customized name for the file
	 * @param num
	 */
	public static void newWriter(int num){
		String fileName = "tsp";
		fileName += num;
		fileName += ".txt";
		
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
