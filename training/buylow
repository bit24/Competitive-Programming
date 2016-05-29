import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: buylow
*/

public class buylow {
	
	static long[] elements;
	static int numElements;
	
	static long[] longestLength;
	static BigInteger[] numSequences;

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("buylow.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("buylow.out")));
		numElements = Integer.parseInt(reader.readLine());
		
		elements = new long[numElements];
		longestLength = new long[numElements];
		numSequences = new BigInteger[numElements];
		
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		
		for(int i = 0; i < numElements; i++){
			if(!inputData.hasMoreTokens()){
				inputData = new StringTokenizer(reader.readLine());
			}
			elements[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();
		
		
		for(int currentIndex = 0; currentIndex < numElements; currentIndex++){
			long currentLongestLength = 0;
			BigInteger currentNumSequences = BigInteger.ZERO;
			long maxBound = Integer.MAX_VALUE;
			
			for(int previousIndex = currentIndex-1; previousIndex >= 0; previousIndex--){
				
				if(!(elements[previousIndex] > elements[currentIndex])){
					continue;
				}
				
				if(!(elements[previousIndex] < maxBound)){
					continue;
				}
				
				if(currentLongestLength < longestLength[previousIndex]){
					currentLongestLength = longestLength[previousIndex];
					currentNumSequences = numSequences[previousIndex];
					maxBound = elements[previousIndex];
				}
				else if(currentLongestLength == longestLength[previousIndex]){
					currentNumSequences = currentNumSequences.add(numSequences[previousIndex]);
					maxBound = elements[previousIndex];
				}
			}
			if(currentNumSequences.equals(BigInteger.ZERO)){
				currentNumSequences = BigInteger.ONE;
			}
			longestLength[currentIndex] = currentLongestLength+1;
			numSequences[currentIndex] = currentNumSequences;
			
		}
		
		long maxBound = Integer.MAX_VALUE;
		long currentLongestSequence = 0;
		BigInteger currentNumSequences = BigInteger.ZERO;
		for(int currentIndex = numElements-1; currentIndex >= 0; currentIndex--){
			if(elements[currentIndex] >= maxBound){
				continue;
			}
			
			if(currentLongestSequence < longestLength[currentIndex]){
				currentLongestSequence = longestLength[currentIndex];
				currentNumSequences = numSequences[currentIndex];
				maxBound = elements[currentIndex];
			}
			else if(currentLongestSequence == longestLength[currentIndex]){
				currentNumSequences = currentNumSequences.add(numSequences[currentIndex]);
				maxBound = elements[currentIndex];
			}
		}
		printer.print(currentLongestSequence + " ");
		printer.println(currentNumSequences.toString());
		printer.close();
	}

}
