import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/*
ID: eric.ca1
LANG: JAVA
TASK: contact
*/
public class contact {
	
	public static contact helper = new contact();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("contact.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("contact.out")));
		String[] inputData = reader.readLine().split(" ");
		int minLength = Integer.parseInt(inputData[0]);
		int maxLength = Integer.parseInt(inputData[1]);
		int highestFrequencies = Integer.parseInt(inputData[2]);
		boolean[] bits = new boolean[200000];
		int bitsIterator = 0;
		
		String nextLine = reader.readLine();
		while(nextLine != null){
			for(int i = 0; i < nextLine.length(); i++){
				bits[bitsIterator++] = nextLine.charAt(i) == '1' ? true : false; 
			}
			nextLine = reader.readLine();
		}
		reader.close();
		
		int[] values = new int[80000];
		
		for(int startIndex = 0; startIndex < bitsIterator; startIndex++){
			for(int length = minLength-1; length < maxLength; length++){
				if(startIndex + length >= bitsIterator){
					break;
				}
				values[convert(bits, startIndex, startIndex + length)]++;
			}
		}
		
		int[] frequencies = Arrays.copyOf(values, values.length);
		Arrays.sort(frequencies);
		int startIndex = 0;
		for(startIndex = 0; startIndex < values.length; startIndex++){
			if(frequencies[startIndex] != 0){
				break;
			}
		}
		
		frequencies = Arrays.copyOfRange(frequencies, startIndex, frequencies.length);
		int visited = 0;
		int frequenciesIterator = frequencies.length-1;
		
		for(int i = 1; i <= highestFrequencies; i++){
			if(frequenciesIterator < 0){
				break;
			}
			int currentFrequency = frequencies[frequenciesIterator--];
			while(currentFrequency == visited){
				if(frequenciesIterator < 0){
					break;
				}
				currentFrequency = frequencies[frequenciesIterator--];
			}
			visited = currentFrequency;
			
			printer.println(currentFrequency);
			LinkedList<String> items = new LinkedList<String>();
			for(int j = 0; j < values.length; j++){
				if(values[j] == currentFrequency){
					items.add(convertBack(j));
				}
			}
			Collections.sort(items, helper. new AComparator());
			
			int numPrinted = 1;
			printer.print(items.get(0));
			for(int j = 1; j < items.size(); j++){
				printer.print(" " + items.get(j));
				numPrinted++;
				if(numPrinted == 6 && j+1 < items.size()){
					numPrinted = 1;
					printer.println();
					printer.print(items.get(++j));
				}
			}
			printer.println();
		}
		printer.close();
		
	}
	
	public static int convert(boolean[] masterArray, int startIndex, int endIndex){
		int newInt = 1;
		for(int i = startIndex; i <= endIndex; i++){
			newInt = newInt << 1;
			newInt = newInt | (masterArray[i] ? 1 : 0);
		}
		return newInt;
	}
	
	public static String convertBack(int converted){
		String output = Integer.toBinaryString(converted);
		return output.substring(1);
	}
	
	class AComparator implements Comparator<String>{

		public int compare(String arg0, String arg1) {
			if(arg0.length() > arg1.length()){
				return 1;
			}
			if(arg0.length() < arg1.length()){
				return -1;
			}
			if(Integer.parseInt(arg0, 2) < Integer.parseInt(arg1, 2)){
				return -1;
			}
			if(Integer.parseInt(arg0, 2) > Integer.parseInt(arg1, 2)){
				return 1;
			}
			return 0;
		}
		
	}

}
