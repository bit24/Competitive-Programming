import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class CowRun {
	
	public static int numRounds;
	public static long trackLength;
	public static long furthestDeviation;
	public static String bessieChoiceInput;
	public static long[][] cards;
	static ArrayList<boolean[]> solutions = new ArrayList<boolean[]>();
	
	public static String currentResult = "";
	public static int finishedRounds;
	public static long currentDistanceTraveled = 0L;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		
		numRounds = Integer.parseInt(inputData.nextToken());
		trackLength = Long.parseLong(inputData.nextToken());
		furthestDeviation = Long.parseLong(inputData.nextToken());
		
		cards = new long[numRounds][8];
		bessieChoiceInput = reader.readLine();
		
		for(int i = 0; i < numRounds; i++){
			inputData = new StringTokenizer(reader.readLine());
			for(int j = 0; j < 8; j++){
				cards[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}
		reader.close();
		
		search();
		System.out.println(currentResult);

	}
	
	public static long getDistance(boolean[] johnDecisions, boolean[] bessieDecisions){
		long tempCurrentDistanceTraveled = currentDistanceTraveled;
		for(int i = 0; i < johnDecisions.length; i++){
			int topCardIndex = 0;
			if(johnDecisions[i]){
				topCardIndex += 4;
			}
			if(bessieDecisions[i]){
				topCardIndex += 2;
			}
			
			int currentRound = i + finishedRounds;
			System.out.println("Looking at round " + currentRound + " so far " + tempCurrentDistanceTraveled + " using " + cards[currentRound][topCardIndex]);

			tempCurrentDistanceTraveled += tempCurrentDistanceTraveled *(cards[currentRound][topCardIndex] % trackLength);
			tempCurrentDistanceTraveled += cards[currentRound][topCardIndex+1];
			tempCurrentDistanceTraveled = tempCurrentDistanceTraveled % trackLength;
			
			System.out.println("Looking at round " + currentRound + " so far " + tempCurrentDistanceTraveled + " using " + cards[currentRound][topCardIndex]);

		}
		
		return Math.min(tempCurrentDistanceTraveled, Math.abs(trackLength-tempCurrentDistanceTraveled));
	}
	
	public static String values(boolean[] items){
		String stuff = "";
		for(boolean current : items){
			stuff += (current ?"B":"T") + " ";
		}
		return stuff;
	}
	
	public static void search(){
		//exit condition
		if (finishedRounds == numRounds) {
			return;
		}
		
		int remainingRounds = numRounds-finishedRounds;
		int numChoices = (int) Math.pow(2, remainingRounds);
		
		boolean isPossible = true;
		John: for(int i = 0; i < numChoices/2; i++){
			boolean[] johnDecisions = getChoice(i);			
			System.out.println("John: " + values(johnDecisions));
						
			for(int j = 0; j < numChoices; j++){
				boolean[] bessieDecisions = getChoice(j);
				System.out.println("===" + values(bessieDecisions));
				
				long distanceFromStart = getDistance(johnDecisions, bessieDecisions);
				if(distanceFromStart > furthestDeviation){
					System.out.println("Short circuited");
					isPossible = false;
					break John;
				}
			}
		}
		int topCardIndex = 0;
		
		if(isPossible) {
			currentResult += "B";
			topCardIndex += 4;
		} else {
		//currentResult == "T"
			System.out.println("For Round " + finishedRounds + " Assuming T");
			currentResult += "T";
		}
		System.out.println("$$$$$$$$$$$$ " + bessieChoiceInput + " Selected index is + " + bessieChoiceInput.substring(finishedRounds, finishedRounds + 1));

		if (bessieChoiceInput.substring(finishedRounds, finishedRounds + 1).equals("B")) {
			topCardIndex += 2;
		}
		
		System.out.println("$$$$$$$$$$$$$$$$$$$Selected number is " + cards[finishedRounds][topCardIndex]);
		currentDistanceTraveled += currentDistanceTraveled *(cards[finishedRounds][topCardIndex] % trackLength);
		currentDistanceTraveled += cards[finishedRounds][topCardIndex+1];
		currentDistanceTraveled = currentDistanceTraveled % trackLength;

		finishedRounds++;
		System.out.println("After finishing " + finishedRounds + " currentDistance is " + currentDistanceTraveled);
		search();
	}
	
	public static boolean[] getChoice(int currentInt){
		int remainingRounds = numRounds - finishedRounds;
		boolean[] decisions = new boolean[remainingRounds];
		
		for(int i = remainingRounds - 1; i >= 0; i--){
			if((currentInt & 000000001) != 1){
				decisions[i] = true;
			}
			currentInt = currentInt >> 1;
		}
		return decisions;
	}

}
