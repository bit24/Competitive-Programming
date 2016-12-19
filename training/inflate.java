import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
ID: eric.ca1
LANG: JAVA
TASK: inflate
*/

public class inflate {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("inflate.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("inflate.out")));
		String currentString = reader.readLine().trim();
		int space = currentString.indexOf(" ");
		
		
		int minutes = Integer.parseInt(currentString.substring(0, space).trim());
		int numClasses = Integer.parseInt(currentString.substring(space+1, currentString.length()).trim());
		
		int[] contestPoints = new int[numClasses+1];
		int[] contestMinutes = new int[numClasses+1];
		
		for(int i = 1; i <= numClasses; i++){
			currentString = reader.readLine().trim();
			space = currentString.indexOf(" ");
			contestPoints[i] = Integer.parseInt(currentString.substring(0, space).trim());
			contestMinutes[i] = Integer.parseInt(currentString.substring(space+1, currentString.length()).trim());
		}
		reader.close();
		
		
		int[] dataArray = new int[minutes+1];
				
		for(int time = 1; time <= minutes; time++){
			int bestValue = dataArray[time-1];
			for(int currentClass = 1; currentClass <= numClasses; currentClass++){
				if(time-contestMinutes[currentClass] >= 0){
					bestValue = Math.max(bestValue, dataArray[time-contestMinutes[currentClass]] + contestPoints[currentClass]);
				}
			}
			dataArray[time] = bestValue;
		}
		printer.println(dataArray[minutes]);
		printer.close();
		
		
	}

}
