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
public class Runner {
	
	private static final int seed = 500;
	static final Random randomGenerator = new Random(seed);

	private static PrintWriter writer;

	public static void main(String[] args) {

//		TravelingSalesmanProblem tsp2 = new TspMultipleCities(7, popSize, genSize, crossoverRate, 
//				mutationRate, elitismRate);
		
		
		for (int i = 1; i < 6; i++) {
			PrintWriter printWriter = newWriter(i);
			writer = printWriter;
			solveTspBerlin();
			printWriter.close();
		}
	}

	static void printInFile(String word) {
		writer.println(word);
	}

	private static void solveTspBerlin(){

		int popSize = 150;
		int genSize = 1000;
		float crossoverRate = 1.0F;
		float mutationRate = 0.1F;
		float elitismRate = 0.09F;
		TravelingSalesmanProblem tsp = new TspBerlin(popSize, genSize, crossoverRate, mutationRate,
				elitismRate);
		
		long time = System.currentTimeMillis();
		tsp.findSolution();
		time = System.currentTimeMillis() - time;

		System.out.println("I found a solution for the Traveling Salesman Problem: ");
		System.out.println("Parameters: ");
		System.out.println("Seed: " + seed);
		System.out.println("Population Size: " + popSize);
		System.out.println("Generation Span: " + genSize);
		System.out.println("Crossover Rate: " + crossoverRate);
		System.out.println("Mutation Rate: " + mutationRate);
		System.out.println("Elitism Rate: " + elitismRate);

		System.out.println("It took " + time + " milliseconds");
		System.out.println("The fitness is: " + tsp.solution.getFitness());
		System.out.println("And the solution is: " + tsp.solution.toString());
		
	}
	
	private static PrintWriter newWriter(int num){
		String fileName = "tsp";
		fileName += num;
		fileName += ".txt";
		PrintWriter printWriter = null;

		try {
			printWriter = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return printWriter;
	}

}
