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
 TASK: lamps
 */
public class lamps {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("lamps.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("lamps.out")));
		int numLamps = Integer.parseInt(reader.readLine());

		int numPresses = Integer.parseInt(reader.readLine());

		String[] inputData = reader.readLine().split(" ");

		int[] someFinalOnLamps = new int[inputData.length - 1];
		for (int i = 0; i < inputData.length; i++) {
			if (inputData[i].equals("-1")) {
				break;
			}
			someFinalOnLamps[i] = Integer.parseInt(inputData[i]);
		}

		inputData = reader.readLine().split(" ");

		int[] someFinalOffLamps = new int[inputData.length - 1];
		for (int i = 0; i < inputData.length; i++) {
			if (inputData[i].equals("-1")) {
				break;
			}
			someFinalOffLamps[i] = Integer.parseInt(inputData[i]);
		}

		reader.close();

		boolean[][] possibilities = new boolean[16][];
		possibilities[0] = new boolean[4];
		possibilities[1] = new boolean[] { true, false, false, false };
		int currentLength = 2;

		for (int currentButton = 1; currentButton < 4; currentButton++) {
			for (int currentIndex = 0; currentIndex < currentLength; currentIndex++) {

				boolean[] newPossibility = Arrays.copyOf(
						possibilities[currentIndex], 4);
				newPossibility[currentButton] = true;

				possibilities[currentLength + currentIndex] = newPossibility;
			}
			currentLength *= 2;
		}

		boolean[][] answerSet = new boolean[possibilities.length][];
		int answerSetIterator = 0;
		for (int currentPosNum = 0; currentPosNum < possibilities.length; currentPosNum++) {
			boolean[] currentPos = possibilities[currentPosNum];
			if (isPossible(numPresses, currentPos)
					&& checkPossible(currentPos, someFinalOnLamps, someFinalOffLamps)) {
				answerSet[answerSetIterator++] = currentPos;
			}
		}

		if (answerSetIterator == 0) {
			printer.println("IMPOSSIBLE");
			printer.close();
			System.exit(0);
		}
		boolean[][] configuration = new boolean[answerSetIterator][];

		for (int i = 0; i < answerSetIterator; i++) {
			boolean[] possibleConfig = new boolean[numLamps];
			Arrays.fill(possibleConfig, true);
			if (answerSet[i][0]) {
				Arrays.fill(possibleConfig, false);
			}
			if (answerSet[i][1]) {
				for (int j = 0; j < possibleConfig.length; j += 2) {
					toggle(possibleConfig, j);
				}
			}
			if (answerSet[i][2]) {
				for (int j = 1; j < possibleConfig.length; j += 2) {
					toggle(possibleConfig, j);
				}
			}
			if (answerSet[i][3]) {
				for (int j = 0; j < possibleConfig.length; j += 3) {
					toggle(possibleConfig, j);
				}
			}
			configuration[i] = possibleConfig;
		}


		boolean[] leastValue = new boolean[numLamps];
		Arrays.fill(leastValue, true);

		boolean[] minValue = new boolean[0];
		
		int currentIndex = 0;
		for (int i = 0; i < configuration.length; i++) {
			for (int k = 0; k < configuration.length; k++) {
				boolean[] j = configuration[k];
				if (isGreater(j, minValue, false) && isGreater(leastValue, j, true)) {
					leastValue = j;
					currentIndex = k;
				}

			}
			print(configuration[currentIndex], printer);
			minValue = leastValue;
			
			leastValue = new boolean[numLamps];
			Arrays.fill(leastValue, true);
		}
		printer.close();

	}
	public static boolean isGreater(boolean[] data1, boolean[] data2, boolean end){
		if(data2.length == 0){
			return true;
		}
		for(int i = 0; i < data1.length; i++){
			if(data1[i] == false && data2[i] == true){
				return false;
			}
			if(data1[i] == true && data2[i] == false){
				return true;
			}
		}
		return end;
	}

	public static void print(boolean[] data, PrintWriter printer) {
		for (boolean b : data) {
			if (b) {
				printer.print(1);
			} else {
				printer.print(0);
			}
		}
		printer.println();
	}

	public static void toggle(boolean[] data, int index) {
		if (data[index]) {
			data[index] = false;
		} else {
			data[index] = true;
		}
	}

	public static boolean checkPossible(boolean[] modifiers, int[] onSet,
			int[] offSet) {
		for (int i = 0; i < onSet.length; i++) {
			int numModifiers = 0;
			if (modifiers[0] == true) {
				numModifiers++;
			}
			if (modifiers[1] == true && (onSet[i] % 2 == 1)) {
				numModifiers++;
			}
			if (modifiers[2] == true && (onSet[i] % 2 == 0)) {
				numModifiers++;
			}
			if (modifiers[3] == true && (onSet[i] % 3 == 1)) {
				numModifiers++;
			}
			if (numModifiers % 2 != 0) {
				return false;
			}
		}
		for (int i = 0; i < offSet.length; i++) {
			int numModifiers = 0;
			if (modifiers[0] == true) {
				numModifiers++;
			}
			if (modifiers[1] == true && (offSet[i] % 2 == 1)) {
				numModifiers++;
			}
			if (modifiers[2] == true && (offSet[i] % 2 == 0)) {
				numModifiers++;
			}
			if (modifiers[3] == true && (offSet[i] % 3 == 1)) {
				numModifiers++;
			}
			if (numModifiers % 2 != 1) {
				return false;
			}
		}
		return true;
	}

	public static boolean isPossible(int numPresses, boolean[] data) {
		int positives = 0;
		for (boolean b : data) {
			if (b) {
				positives++;
			}
		}

		if (positives > numPresses) {
			return false;
		}
		return (numPresses - positives) % 2 == 0;

	}

}
