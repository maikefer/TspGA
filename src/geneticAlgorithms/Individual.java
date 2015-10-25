package geneticAlgorithms;

public class Individual {
	
	private char[] representation;
	private int length;
	
	public Individual(int citySize){
		this.length = citySize;
		this.initializeRandomly();
	}
	
	public int getFitness(){
		return 0;
	}
	
	public void initializeRandomly(){
		
	}
	
	public void mutate(){
		
	}

}
