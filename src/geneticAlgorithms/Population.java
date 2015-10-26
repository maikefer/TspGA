package geneticAlgorithms;

public class Population {

	/**
	 * size describes how many individuals there are in one population
	 */
	private int size;
	/**
	 * The representation of the individuals
	 */
	private Individual[] individuals;
	/**
	 * cizySize describes how many cities are accused by the TSB
	 */
	private int citySize;
	
	public Population(int size, int citySize){
		this.size = size;
		this.citySize = citySize;
		this.individuals = new Individual[size];
		initializeIndividualsRandomly();
	}
	
	public void initializeIndividualsRandomly(){
		for (int i = 0; i < size; i++) {
			individuals[i] = new Individual(citySize);
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++){
			sb.append("I"+ i + ": " + individuals[i].toString() + "\n");
		}
		return sb.toString();
	}
	
	public void doCrossover(){
		
	}
	
	public void reproduce(){
		
	}
}
