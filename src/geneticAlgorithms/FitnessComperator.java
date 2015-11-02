package geneticAlgorithms;

import java.util.Comparator;

public class FitnessComperator<E> implements Comparator<Individual>{

	@Override
	public int compare(Individual arg0, Individual arg1) {

		/*
		 * returns:
		 * neg int - arg0 < arg1
		 * 0 - arg0 = arg1
		 * pos int - arg0 > arg1
		 */
	
		
		int fitness0 = arg0.getFitness();
		int fitness1 = arg1.getFitness();
		
		if (fitness0 < fitness1){
			return -1;
		} else if (fitness0 == fitness1) {
			return 0;
		} else {
			return 1;
		}
	}

}
