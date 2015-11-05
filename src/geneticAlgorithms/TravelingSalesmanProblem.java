package geneticAlgorithms;

/**
 * This class represents a Traveling-Salesman-Problem (tsp)
 * @author Maike Rees
 *
 */
public abstract class TravelingSalesmanProblem {
	
	/**
	 * The coordinates of the citys.
	 */
	protected int nodes[][];
	/**
	 * The amount of cities accused by the tsp.
	 */
	protected int dimension;
	/**
	 * The solution of the problem
	 */
	protected Individual solution;
	/**
	 * The initial Population to start to find the solution
	 */ 
	protected Population parentPop;
	/**
	 * How many generations will be generated to find a solution
	 */
	protected int genSize;
	
	/**
	 * The Constructor.
	 * @param dimension
	 * @param popSize
	 * @param genSize
	 * @param crossoverRate
	 * @param mutationRate
	 * @param elitisimRate
	 */
	public TravelingSalesmanProblem(int dimension, int popSize, int genSize, float crossoverRate, 
			float mutationRate, float elitisimRate){
		this.dimension = dimension;
		this.nodes = new int[dimension][2];
		this.genSize = genSize;
		this.parentPop = new Population(popSize, this, crossoverRate, mutationRate, elitisimRate);
		parentPop.initializeIndividualsRandomly(this);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Dimension: " + dimension);
		sb.append("\nPopulationSize: " + parentPop.getSize());
		sb.append("\nNodes:");
		
		for (int i = 0; i < dimension; i++){
			sb.append("\nno: " + i + " x: " + nodes[i][0] +  " y: " + nodes[i][1]);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param node the index of the node
	 * @return the x-value of the desired node
	 */
	public int getX(int node){
		return nodes[node][0];
	}
	
	/**
	 * 
	 * @param node the index of the node
	 * @return the y-value of the desired node
	 */
	public int getY(int node){
		return nodes[node][1];
	}
	
	/**
	 * Finds a solution with a genetic algorithm 
	 */
	public void findSolution() {
		// initialize solution 
		solution = initializeSolution();
	
		int generationCounter = 0;
		
		while (generationCounter < genSize + 1) {
			parentPop = parentPop.reproduce(this);
			// save best child as solution
			for (int i = 0; i < parentPop.getSize(); i++) {
				if (parentPop.getIndividual(i).getFitness() < solution.getFitness()){
					solution = parentPop.getIndividual(i);
				}
			}
			System.out.println("Generation Counter: " + generationCounter 
										+ " best Fitness: " + solution.getFitness()
										+ " average Fitness: " + parentPop.getAverageFitness());
			RunMe.printInFile(solution.getFitness());
			generationCounter++;
		}
	}
	
	
	/**
	 * @return the dimension (amount of cities) of the tsp
	 */
	public int getDimension(){
		return dimension;
	}
	
	/**
	 * 
	 * @return the best Individual of the initial Population 
	 */
	public Individual initializeSolution(){
		solution = parentPop.getIndividual(0);
		
		for (int i = 1; i < parentPop.getSize(); i++) {
			if (parentPop.getIndividual(i).getFitness() < solution.getFitness()){
				solution = parentPop.getIndividual(i);
			}
		}
		return solution;
	}
}
