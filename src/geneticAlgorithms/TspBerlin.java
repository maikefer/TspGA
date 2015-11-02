package geneticAlgorithms;

public class TspBerlin extends TravelingSalesmanProblem {

	public TspBerlin(int popSize, int genSize){
		super(52, popSize, genSize);
		initialize();
	}
	
	private void initialize(){
		nodes[0][0] = 565;
		nodes[0][1] = 575;
		
		nodes[1][0] = 25;
		nodes[1][1] = 185;
		
		nodes[2][0] = 345;
		nodes[2][1] = 750;
		
		nodes[3][0] = 945;
		nodes[3][1] = 685;
		
		nodes[4][0] = 845;
		nodes[4][1] = 655;
		
		nodes[5][0] = 880;
		nodes[5][1] = 660;
		
		nodes[6][0] = 25;
		nodes[6][1] = 230;
		
		nodes[7][0] = 525;
		nodes[7][1] = 1000;
		
		nodes[8][0] = 580;
		nodes[8][1] = 1175;
		
		nodes[9][0] = 650;
		nodes[9][1] = 1130;
		
		nodes[10][0] = 1605;
		nodes[10][1] = 620;
		
		nodes[11][0] = 1220;
		nodes[11][1] = 580;
		
		nodes[12][0] = 1465;
		nodes[12][1] = 200;
		
		nodes[13][0] = 1530;
		nodes[13][1] = 5;
		
		nodes[14][0] = 845;
		nodes[14][1] = 680;
		
		nodes[15][0] = 725;
		nodes[15][1] = 370;
		
		nodes[16][0] = 145;
		nodes[16][1] = 665;
		
		nodes[17][0] = 415;
		nodes[17][1] = 635;
		
		nodes[18][0] = 510;
		nodes[18][1] = 875;
		
		nodes[19][0] = 560;
		nodes[19][1] = 365;
		
		nodes[20][0] = 300;
		nodes[20][1] = 465;
		
		nodes[21][0] = 520;
		nodes[21][1] = 585;
		
		nodes[22][0] = 480;
		nodes[22][1] = 415;
		
		nodes[23][0] = 835;
		nodes[23][1] = 625;
		
		nodes[24][0] = 975;
		nodes[24][1] = 580;
		
		nodes[25][0] = 1215;
		nodes[25][1] = 245;
		
		nodes[26][0] = 1320;
		nodes[26][1] = 315;
		
		nodes[27][0] = 1250;
		nodes[27][1] = 400;
		
		nodes[28][0] = 660;
		nodes[28][1] = 180;
		
		nodes[29][0] = 410;
		nodes[29][1] = 250;
		
		nodes[30][0] = 420;
		nodes[30][1] = 555;
		
		nodes[31][0] = 575;
		nodes[31][1] = 655;
		
		nodes[32][0] = 1150;
		nodes[32][1] = 1160;
		
		nodes[33][0] = 700;
		nodes[33][1] = 580;
		
		nodes[34][0] = 685;
		nodes[34][1] = 595;
		
		nodes[35][0] = 685;
		nodes[35][1] = 610;
		
		nodes[36][0] = 770;
		nodes[36][1] = 610;
		
		nodes[37][0] = 795;
		nodes[37][1] = 645;
		
		nodes[38][0] = 720;
		nodes[38][1] = 635;
		
		nodes[39][0] = 760;
		nodes[39][1] = 650;
		
		nodes[40][0] = 475;
		nodes[40][1] = 960;
		
		nodes[41][0] = 95;
		nodes[41][1] = 260;
		
		nodes[42][0] = 875;
		nodes[42][1] = 920;
		
		nodes[43][0] = 700;
		nodes[43][1] = 500;
		
		nodes[44][0] = 555;
		nodes[44][1] = 815;
		
		nodes[45][0] = 830;
		nodes[45][1] = 485;
		
		nodes[46][0] = 1170;
		nodes[46][1] = 65;
		
		nodes[47][0] = 830;
		nodes[47][1] = 610;
		
		nodes[48][0] = 605;
		nodes[48][1] = 625;
		
		nodes[49][0] = 595;
		nodes[49][1] = 360;
		
		nodes[50][0] = 1340;
		nodes[50][1] = 725;
		
		nodes[51][0] = 1740;
		nodes[51][1] = 245;
	}
	
}
