import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 ID: eric.ca1
 LANG: JAVA
 TASK: runround
*/

public class runround {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(
				new FileReader("runround.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("runround.out")));
		String inputString = reader.readLine();
		reader.close();

		int[] inputArray = new int[inputString.length()];
		for (int i = 0; i < inputString.length(); i++) {
			inputArray[i] = Character.getNumericValue(inputString.charAt(i));
		}
		
		int[] number = inputArray;
		mainNumberLoop : while(true){
			number[number.length -1]++;
			for(int i = number.length - 1; i >= 0; i--){
				if(number[i] != 10){
					break;
				}
				if(i == 0){
					number[0] = 0;
					int[] tempArray = new int[number.length+1];
					tempArray[0] = 1;
					for(int j = 0; j < number.length; j++){
						tempArray[j + 1] = number[j];
					}
					number = tempArray;
				}
				else{
					number[i] = 0;
					number[i-1] += 1;
				}
			}
			
			for(int i = 0; i < number.length; i++){
				for(int j = 0; j < i; j++){
					if(number[j] == number[i]){
						continue mainNumberLoop;
					}
				}
			}
			
			if(isRunRound(number)){
				break mainNumberLoop;
			}
			
		}
		
		for(int i : number){
			printer.print(i);
		}
		printer.println();
		printer.close();
	}
	
	public static boolean isRunRound(int[] number) {
		boolean[] visited = new boolean[number.length];
		int currentIndex = 0;
		for (int i = number.length - 1; i >= 0; i--) {
			if(visited[currentIndex] == true){
				return false;
			}
			visited[currentIndex] = true;
			currentIndex = number[currentIndex] % number.length + currentIndex;
			if(currentIndex >= number.length){
				currentIndex -= number.length;
			}
		}

		if (currentIndex != 0) {
			return false;
		}

		return true;
	}

}
