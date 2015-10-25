package geneticAlgorithms;

public class RunMe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// a city will be represented by a number
		
		Individual indiv;
		
		for (int i = 0; i < 100; i++){
			indiv = new Individual(9);
			System.out.println(i + ": " + indiv.toString());
		}

	}

}
