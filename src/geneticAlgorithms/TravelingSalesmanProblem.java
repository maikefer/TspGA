package geneticAlgorithms;

public abstract class TravelingSalesmanProblem {
	
	protected int nodes[][];
	protected int dimension;
	protected int solution[];
	protected Population population;
	
	public TravelingSalesmanProblem(int dimension, int popSize){
		this.dimension = dimension;
		this.nodes = new int[dimension][2];
		this.population = new Population(popSize, this);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Dimension: " + dimension);
		sb.append("\nPopulationSize: " + population.getSize());
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
	
	public void findSolution() {
		
	}
	
	public int getDimension(){
		return dimension;
	}

}
