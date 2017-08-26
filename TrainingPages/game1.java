import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
 LANG: JAVA
 TASK: game1
 */
public class game1 {
	
	public static int[] numbers;
	
	public static int[][] data;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("game1.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("game1.out")));
		
		int size = Integer.parseInt(reader.readLine().trim());
		String inputLine = reader.readLine();
		
		numbers = new int[size];
		int numbersIterator = 0;
		
		while(inputLine != null){
			StringTokenizer inputData = new StringTokenizer(inputLine);
			while(inputData.hasMoreTokens()){
				numbers[numbersIterator++] = Integer.parseInt(inputData.nextToken());
			}
			inputLine = reader.readLine();
		}
		reader.close();
		
		data = new int[size + 1][size+1];
		
		boolean isEven = (size % 2 == 0);
		
		for(int currentSize = 1; currentSize <= size; currentSize++){
			for(int startIndex = 0; startIndex + currentSize <= size; startIndex++){
				int endIndex = startIndex + currentSize;
				boolean isOurTurn = false;
				if(currentSize % 2 == 0){
					isOurTurn = true;
				}
				else{
					isOurTurn = false;
				}
				
				if(!isEven){
					isOurTurn = !isOurTurn;
				}
				
				findSolution(startIndex, endIndex, isOurTurn);
			}
		}
		
		int totalDifference = data[0][size];
		
		int total = 0;
		for(int i = 0; i < size; i++){
			total += numbers[i];
		}
		
		int player1Score = (total-totalDifference)/2 + totalDifference;
		int player2Score = (total-totalDifference)/2;
		printer.println(player1Score + " " + player2Score);
		printer.close();
	}
	
	public static void findSolution(int i, int j, boolean ourTurn){
		int returnValue = 0;
		if(j == i + 1){
			if(ourTurn){
				returnValue = numbers[i];
			}
			else{
				returnValue = -numbers[i];
			}
		}
		else if(ourTurn){
			returnValue = Math.max(data[i+1][j] + numbers[i], data[i][j-1] + numbers[j-1]);
		}
		else{
			returnValue = Math.min(data[i+1][j] - numbers[i], data[i][j-1] - numbers[j-1]);
		}
		data[i][j] = returnValue;
		
	}
	
	

}
