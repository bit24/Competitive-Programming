import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedWriter;

class Main {
	
	static int r;
	static double v;
	static double e;
	static double f;
	static int[] distance;
	static double b;
	
	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		while(reader.hasNext()){
			int numStops = reader.nextInt();
			if(numStops == 0){
				break;
			}
			distance = new int[numStops+1];
			distance[0] = 0;
			for(int i = 1; i <= numStops; i++){
				distance[i] = reader.nextInt();
			}
			
			b = reader.nextDouble();
			r = reader.nextInt();
			v = reader.nextDouble();
			e = reader.nextDouble();
			f = reader.nextDouble();
			
			double[] dp = new double[numStops+1];
			Arrays.fill(dp, Double.POSITIVE_INFINITY);
			dp[0] = 0.0;

			for(int cStop = 1; cStop <= numStops; cStop++){
				double best = Double.POSITIVE_INFINITY;
				for(int previous = 0; previous < cStop; previous++){
					best = Math.min(best, dp[previous] + getTime(previous, cStop));
				}
				dp[cStop] = best;
			}
			DecimalFormat f = new DecimalFormat("0.0000");
			printer.println(f.format(dp[numStops]));
		}
		reader.close();
		printer.close();
		
	}
	
	static double getTime(int sStop, int eStop){
		int d = distance[eStop] - distance[sStop];
		double sum = 0.0;
		for(int x = 0; x < d; x++){
			sum += formula(x);
		}
		if(sStop == 0){
			return sum;
		}
		else{
			return sum + b;
		}
	}
	
	static double formula(int x){
		if(x >= r){
			return 1/(v-e*(double)(x-r));
		}
		else{
			return 1/(v-f*((double)r-x));
		}
	}
	
	
}
