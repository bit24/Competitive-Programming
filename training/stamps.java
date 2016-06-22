import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
 LANG: JAVA
 TASK: stamps
 */
public class stamps {
	public static void main(String[] args) throws IOException{
		long startTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("stamps.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("stamps.out")));
		String inputString = reader.readLine();
		int midSpace = inputString.indexOf(' ');
		int totalStamps = Integer.parseInt(inputString.substring(0, midSpace).trim());
		int numStamps = Integer.parseInt(inputString.substring(midSpace+1, inputString.length()).trim());
		int[] stampValues = new int[numStamps];
		int stampValuesIterator = 0;
		while(stampValuesIterator < numStamps){
			String[] inputValues = reader.readLine().split(" ");
			for(int i = 0; i < inputValues.length; i++){
				if(inputValues[i].equals("")){
					continue;
				}
				stampValues[stampValuesIterator++] = Integer.parseInt(inputValues[i]);
			}
		}
		reader.close();
		
		short[] stampsUsed = new short[2000005];
		stampsUsed[0] = 0;
		for(int i = 1; i < 2000000; i++){
			stampsUsed[i] = Short.MAX_VALUE;
		}
		
		int currentIndex = 0;
		for(currentIndex = 0; currentIndex < 2000005; currentIndex++){
			short currentStampsUsed = stampsUsed[currentIndex];
			
			if(currentStampsUsed == Integer.MAX_VALUE || currentStampsUsed > totalStamps){
				break;
			}
			if(++currentStampsUsed > totalStamps){
				continue;
			}
			for(int currentStamp = 0; currentStamp < stampValues.length; currentStamp++){
				int currentStampValue = stampValues[currentStamp] + currentIndex;
				if(stampsUsed[currentStampValue] > currentStampsUsed){
					stampsUsed[currentStampValue] = currentStampsUsed;
				}
			}
		}
		printer.println(--currentIndex);
		printer.close();
		System.out.println(System.currentTimeMillis()-startTime);
		System.exit(0);
	}
}
