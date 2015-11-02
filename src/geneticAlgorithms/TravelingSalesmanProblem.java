package geneticAlgorithms;

public abstract class TravelingSalesmanProblem {
	
	protected int nodes[][];
	protected int dimension;
	protected Individual solution;
	protected Population parentPop;
	protected int genSize;
	
	public TravelingSalesmanProblem(int dimension, int popSize, int genSize, float crossoverRate, 
			float mutationRate, float elitisimRate){
		this.dimension = dimension;
		this.nodes = new int[dimension][2];
		this.genSize = genSize;
		this.parentPop = new Population(popSize, this, crossoverRate, mutationRate, elitisimRate);
		parentPop.initializeIndividualsRandomly(this);
	}
	
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
			genSize++;
		}
	}
	
	public Population generateOffspring(){
		return null;
	}
	
	public int getDimension(){
		return dimension;
	}
	
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
