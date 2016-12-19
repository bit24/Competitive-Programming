import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/*
 ID: eric.ca1
 LANG: JAVA
 TASK: zerosum
 */
public class zerosum {
	

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("zerosum.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));
		int length = Integer.parseInt(reader.readLine());
		reader.close();
		
		
		int[] items = new int[length];
		for(int i = 0; i < length; i++){
			items[i] = i+1;
		}

		
		byte[][] possibilities = generatePossibilities(length-1);
		
		ArrayList<byte[]> solutionSet = new ArrayList<byte[]>();
		
		for(byte[] currentArray : possibilities){
			if(checkPossibility(currentArray, items)){
				solutionSet.add(currentArray);
			}
		}
		
		Collections.sort(solutionSet, new zerosum().new comparer());
		
		for(byte[] currentArray : solutionSet){
			printer.print(1);
			for(int i = 0; i < currentArray.length; i++){
				printer.print(exchange(currentArray[i]));
				printer.print(i+2);
			}
			printer.println();
		}
		printer.close();
	}
	
	class comparer implements Comparator<byte[]>{
		public int compare(byte[] first, byte[] second){
			for(int i = 0; i < first.length; i++){
				if(first[i] > second[i]){
					return 1;
				}
				if(second[i] > first[i]){
					return -1;
				}
			}
			return 0;
		}
	}
	
	public static int compare(int[] first, int[] second){
		for(int i = 0; i < first.length; i++){
			if(first[i] > second[i]){
				return -1;
			}
			if(second[i] > first[i]){
				return 1;
			}
		}
		return 0;
	}
	
	public static String exchange(byte i){
		if(i == 0){
			return " ";
		}
		if(i == 1){
			return "+";
		}
		if(i == 2){
			return "-";
		}
		return null;
	}

	public static byte[][] generatePossibilities(int length) {
		byte[][] possibilities = new byte[(int) Math.pow(3, length)][];
		int numPossibilities = (int) Math.pow(3, length);
		possibilities[0] = new byte[length];
		int currentLength = 0;
		poss: for (int i = 1; i < numPossibilities; i++) {
			byte[] data = Arrays.copyOf(possibilities[i - 1],
					possibilities[i - 1].length);
			possibilities[i] = data;
			data[0] += 1;
			for (int currentIndex = 0; true; currentIndex++) {
				if (currentIndex > currentLength) {
					currentLength++;
				}

				if (data[currentIndex] > 2) {
					if (currentIndex == length - 1) {
						break poss;
					}
					data[currentIndex] = 0;
					data[currentIndex + 1] += 1;
				} else {
					break;
				}
			}
		}
		return possibilities;
	}

	public static boolean checkPossibility(byte[] possibility, int[] stuff) {
		int length = possibility.length;
		int value = stuff[0];
		int possibilityIndex = 0;

		while (length > possibilityIndex && possibility[possibilityIndex] == 0) {
			value = value * 10 + stuff[++possibilityIndex];
		}
		
		while (possibilityIndex < length) {

			if (possibility[possibilityIndex] == 1) {
				int subValue = stuff[possibilityIndex + 1];

				possibilityIndex += 1;
				
				
				while (length > possibilityIndex && possibility[possibilityIndex] == 0) {
					subValue = subValue * 10 + stuff[++possibilityIndex];
				}
				value += subValue;
			}

			else if (possibility[possibilityIndex] == 2) {
				int subValue = stuff[possibilityIndex + 1];

				possibilityIndex += 1;
				
				while (length > possibilityIndex && possibility[possibilityIndex] == 0) {
					subValue = subValue * 10 + stuff[++possibilityIndex];
				}
				value -= subValue;
			}
		}
		if(value == 0){
			return true;
		}
		else{
			return false;
		}
	}
}
