import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
	
	static int numProblems;
	static ArrayList<String> outputData = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		ArrayList<String> out = new ArrayList<String>();
		
		while(reader.hasNext()){
			int[] items = new int[100];
			int numItems = 0;
			int next = reader.nextInt();
			while(next != -999999){
				items[numItems++] = next;
				next = reader.nextInt();
			}
			
			BigInteger s = BigInteger.valueOf(items[0]);
			BigInteger maxValue = s;
			BigInteger minValue = s;
			
			BigInteger best = s;
			for(int cItem = 1; cItem < numItems; cItem++){
				BigInteger bigC = BigInteger.valueOf(items[cItem]);
				BigInteger value1 = bigC.multiply(maxValue);
				BigInteger value2 = bigC.multiply(minValue);
				
				maxValue = value1.max(value2).max(bigC);
				minValue = value1.min(value2).min(bigC);
				
				best = best.max(maxValue);
			}
			out.add(best.toString());
			
		}
		reader.close();
		for(String str : out){
			System.out.println(str);
		}
	}
	

}
