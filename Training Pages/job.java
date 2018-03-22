/*ID: eric.ca1
LANG: JAVA
TASK: job
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class job {

	static int numJobs;
	static int numTypeA;
	static int numTypeB;
	static int[] aTimes;
	static int[] bTimes;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("job.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("job.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numJobs = Integer.parseInt(inputData.nextToken());
		numTypeA = Integer.parseInt(inputData.nextToken());
		numTypeB = Integer.parseInt(inputData.nextToken());
		aTimes = new int[numTypeA];
		bTimes = new int[numTypeB];

		for (int i = 0; i < numTypeA; i++) {
			if (!inputData.hasMoreTokens()) {
				inputData = new StringTokenizer(reader.readLine());
			}
			aTimes[i] = Integer.parseInt(inputData.nextToken());
		}

		for (int i = 0; i < numTypeB; i++) {
			if (!inputData.hasMoreTokens()) {
				inputData = new StringTokenizer(reader.readLine());
			}
			bTimes[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();
		
		int[] finishAJobs = new int[numJobs];
		
		int[] finishTimes = new int[numTypeA];
		for(int jobNum = 0; jobNum < numJobs; jobNum++){
			int minimum = Integer.MAX_VALUE/4;
			int index = -1;
			for(int machineNum = 0; machineNum < numTypeA; machineNum++){
				if(finishTimes[machineNum] + aTimes[machineNum] < minimum){
					minimum = finishTimes[machineNum] + aTimes[machineNum];
					index = machineNum;
				}
			}
			finishTimes[index] = minimum;
			finishAJobs[jobNum] = minimum;	
		}
		printer.print(finishAJobs[numJobs-1] + " ");
		
		int[] finishBJobs = new int[numJobs];
		
		finishTimes = new int[numTypeB];
		for(int jobNum = 0; jobNum < numJobs; jobNum++){
			int minimum = Integer.MAX_VALUE/4;
			int index = -1;
			for(int machineNum = 0; machineNum < numTypeB; machineNum++){
				if(finishTimes[machineNum] + bTimes[machineNum] < minimum){
					minimum = finishTimes[machineNum] + bTimes[machineNum];
					index = machineNum;
				}
			}
			finishTimes[index] = minimum;
			finishBJobs[jobNum] = minimum;	
		}
		
		int finalCost = 0;
		
		for(int i = 0; i < numJobs; i++){
			finalCost = Math.max(finalCost, finishAJobs[i] + finishBJobs[numJobs-1-i]);
		}
		printer.println(finalCost);
		printer.close();
	}

}
