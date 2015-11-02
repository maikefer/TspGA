package geneticAlgorithms;

public class RunMe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Citysize must be smaller than 100!
		
		// a city will be represented by a number
		int popSize = 90; // not more than 100! TODO: ajust that
		int genSize = 800;
		float crossoverRate = 0.8F; //tODO doesn't work for < 1
		float mutationRate = 0.1F;
		float elitismRate = 0.1F;
		
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
