package geneticAlgorithms;

public class RunMe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// a city will be represented by a number
		int popSize = 50;
		int genSize = 10;
		
		TravelingSalesmanProblem tsp = new TspBerlin(popSize, genSize);
		TravelingSalesmanProblem tsp2 = new EasyTsp(7, popSize, genSize);
		
		Individual parent1 = new Individual(tsp2);
		Individual parent2 = new Individual(tsp2);
		
		Individual children[] = parent1.getChildrenUOX(parent2);
		
		System.out.println(parent1.toString() + " parent 1");
		System.out.println(parent2.toString() + " parent 2");
		System.out.println(children[0].toString() + " child 1");
		System.out.println(children[1].toString() + " child 2");
		
//		Individual indi = new Individual(tsp);
//		System.out.println(indi.getFitness());
//		Individual indi2 = new Individual(tsp);
//		System.out.println(indi2.getFitness());
		
//		Population pop = new Population(2, tsp);
//		System.out.println(pop.individuals[0].toString());
//		System.out.println(pop.individuals[0].isValid());
//		System.out.println(pop.individuals[0].getFitness());
//		
//		System.out.println(pop.individuals[1].toString());
//		System.out.println(pop.individuals[1].isValid());
//		System.out.println(pop.individuals[1].getFitness());	
		
				
//		System.out.println(tsp.toString());
//
	}

}
