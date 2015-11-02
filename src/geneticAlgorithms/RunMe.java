package geneticAlgorithms;

public class RunMe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Citysize must be smaller than 100!
		
		// a city will be represented by a number
		int popSize = 50;
		int genSize = 100;
		float crossoverRate = 1.0F;
		float mutationRate = 0.0F;
		float elitismRate = 0.1F;
		
		TravelingSalesmanProblem tsp = new TspBerlin(popSize, genSize, crossoverRate, mutationRate, 
															elitismRate);
		tsp.findSolution();
		System.out.println(tsp.solution.toString());
		System.out.println(tsp.solution.getFitness());
//		TravelingSalesmanProblem tsp2 = new EasyTsp(7, popSize, genSize);
		
	}

}
