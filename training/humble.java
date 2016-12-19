import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/*
ID: eric.ca1
LANG: JAVA
TASK: humble
*/

public class humble {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("humble.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("humble.out")));
		String[] inputData = reader.readLine().split(" ");
		int numPrimes = Integer.parseInt(inputData[0]);
		int numberCount = Integer.parseInt(inputData[1]);
		String inputS = reader.readLine();
		inputData = inputS.split(" ");
		
		Long[] primes = new Long[numPrimes];
		for(int i = 0; i < numPrimes; i++){
			primes[i] = Long.parseLong(inputData[i]);
		}
		reader.close();
		
		Arrays.sort(primes);
		
		int[] nextIndex = new int[numPrimes];
		long[] humb = new long[numberCount+1];
		humb[0] = 1;
		for(int currentHumb = 1; currentHumb <= numberCount; currentHumb++){
			long minValue = Long.MAX_VALUE;
			for(int currentPrime = 0; currentPrime < numPrimes; currentPrime++){
				while(nextIndex[currentPrime] < currentHumb && primes[currentPrime]*humb[nextIndex[currentPrime]] <= humb[currentHumb-1]){
					nextIndex[currentPrime]++;
				}
				minValue = Math.min(minValue, primes[currentPrime]*humb[nextIndex[currentPrime]]);
			}
			humb[currentHumb] = minValue;
		}
		printer.println(humb[numberCount]);
		printer.close();
	}
	

}
