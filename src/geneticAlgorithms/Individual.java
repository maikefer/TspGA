package geneticAlgorithms;

import java.math.*;
import java.util.Random;

public class Individual {
	
	private char[] cities;
	private int length;
	
	public Individual(int citySize){
		this.length = citySize;
		this.cities = new char[10];
		this.initializeRandomly();
	}
	
	public int getFitness(){
		// always every city only one time inside
		// fitness = distance traveled the smaller the better
		return 0;
	}
	
	public void initializeRandomly(){
		int city = 0;
		Random generator = new Random();
		
		for (int i = 0; i < length; i++) {
			city = Math.abs(generator.nextInt()  % (length));
			
			// '\u0000' is the default value for chars --> char hasn't been initialized
			if (cities[city] == '\u0000') {
				cities[city] = Integer.toString(i).charAt(0);
			}
		}
	}
	
	public void mutate(){
		
	}
	
	@Override
	public String toString(){
		return new String(cities);
	}

}
