package geneticAlgorithms;

public class RunMe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// a city will be represented by a number
		int popSize = 50;
		
		TravelingSalesmanProblem tsp = new TspBerlin(popSize);
		
//		Individual indi = new Individual(tsp);
//		System.out.println(indi.getFitness());
//		Individual indi2 = new Individual(tsp);
//		System.out.println(indi2.getFitness());
		
		Population pop = new Population(2, tsp);
		System.out.println(pop.individuals[0].toString());
		System.out.println(pop.individuals[0].isValid());
		System.out.println(pop.individuals[0].getFitness());
		
		System.out.println(pop.individuals[1].toString());
		System.out.println(pop.individuals[1].isValid());
		System.out.println(pop.individuals[1].getFitness());	
		
				
//		System.out.println(tsp.toString());
//
	}

}
