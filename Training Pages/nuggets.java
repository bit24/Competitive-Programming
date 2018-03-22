/*ID: eric.ca1
LANG: JAVA
TASK: nuggets
*/


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class nuggets {

	static int[] packagingSizes;

	static int numSizes;

	static boolean[] isPossible;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("nuggets.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("nuggets.out")));
		numSizes = Integer.parseInt(reader.readLine());

		packagingSizes = new int[numSizes];
		for (int i = 0; i < numSizes; i++) {
			packagingSizes[i] = Integer.parseInt(reader.readLine());
		}

		int gcd = packagingSizes[0];
		for (int i : packagingSizes) {
			gcd = findGCD(gcd, i);
		}

		if (gcd != 1) {
			printer.println(0);
		} else {
			isPossible = new boolean[256];
			isPossible[0] = true;
			
			int numPossible = 1;
			int largestImpossibleNumber = 0;
			
			sizeLoop:
			for (int currentSize = 1; currentSize < 2000000000; currentSize++) {
				
				
				if(numPossible == 256){
					break sizeLoop;
				}
				
				int arrayAccess = currentSize % 256;
				
				for (int packageSize : packagingSizes) {
					if (isPossible[correct(arrayAccess - packageSize)]) {
						
						if(!isPossible[arrayAccess]){
							numPossible++;
						}
						
						isPossible[arrayAccess] = true;
						continue sizeLoop;
					}
				}
				if(isPossible[arrayAccess]){
					numPossible--;
				}
				isPossible[arrayAccess] = false;
				largestImpossibleNumber = currentSize;
			}
			
			printer.println(largestImpossibleNumber);

		}
		printer.close();

	}
	
	public static int correct(int i){
		if(i < 0){
			return i + 256;
		}
		else if(i >= 256){
			return i -256;
		}
		else{
			return i;
		}
	}

	public static int findGCD(int a, int b) {
		if (b == 0) {
			return a;
		}
		return findGCD(b, a % b);
	}

}
