import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
ID: eric.ca1
LANG: JAVA
TASK: nocows
*/

public class nocows {
	
	public static nocows helper = new nocows();

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		BufferedReader reader = new BufferedReader(new FileReader("nocows.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("nocows.out")));
		
		String[] inputData = reader.readLine().split(" ");
		
		int numNodes = Integer.parseInt(inputData[0]);
		if(numNodes % 2 == 0){
			reader.close();
			printer.println(0);
			printer.close();
			System.exit(0);
		}
		
			numNodes = (numNodes - 1)/2;
		int height = Integer.parseInt(inputData[1]) - 1;
		reader.close();
		
		long[][] table = new long[height+1][numNodes+1];
		
		
		for(long[] currentArray : table){
			currentArray[0] = 1;
		}
		
		int times = 0;

		for(int currentRow = 1; currentRow < table.length; currentRow++){
			for(int currentColumn = 1; currentColumn < table[0].length; currentColumn++){
				long possibilityCount = 0;
				for(int i = 0; i < currentColumn; i++){
					times++;
					possibilityCount += table[currentRow-1][i]*table[currentRow-1][currentColumn-i-1];
				}
				table[currentRow][currentColumn] = possibilityCount % 9901;
			}
		}
		
		if(table[height][numNodes] < table[height-1][numNodes]){
			printer.println((table[height][numNodes] - table[height-1][numNodes]) + 9901);

		}
		else{
			printer.println((table[height][numNodes] - table[height-1][numNodes]) % 9901);
		}
		System.out.println(times);
		printer.close();
		System.out.println(System.currentTimeMillis()-startTime);
		
	}

}
