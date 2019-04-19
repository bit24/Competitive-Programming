import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
	
	static int numProblems;
	static ArrayList<String> outputData = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		numProblems = reader.nextInt();
		
		for(int i = 0; i < numProblems; i++){
			int numStops = reader.nextInt();
			int[] best = new int[numStops+1];
			int bestSoFar = Integer.MIN_VALUE;
			int bIndex = 0;
			int bLength = 0;
			int cLength = 0;
			for(int j = 2; j <= numStops; j++){
				int cPath = reader.nextInt();
				if(best[j-1] + cPath >= 0){
					best[j] = cPath + best[j-1];
					cLength++;
				}
				else{
					best[j] = 0;
					cLength = 0;
				}
				if(best[j] > bestSoFar){
					bestSoFar = best[j];
					bIndex = j;
					bLength = cLength;
				}
				else if(best[j] == bestSoFar){
					if(cLength > bLength){
						bIndex = j;
						bLength = cLength;
					}
				}
			}
			
			if(bestSoFar > 0){
				int endI = bIndex;
				int startI = endI - bLength;
				outputData.add("The nicest part of route " + (i + 1) + " is between stops " + startI + " and " + endI);
			}
			else{
				outputData.add("Route " + (i + 1) + " has no nice parts");
			}	
		}
		reader.close();
		
		for(String str: outputData){
			System.out.println(str);
		}
		
	}

}
