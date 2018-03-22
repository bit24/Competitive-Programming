import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
 LANG: JAVA
 TASK: range
 */
public class range {
	
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("range.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
		int length = Integer.parseInt(reader.readLine());
		boolean[][] data = new boolean[length][length];
		
		for(int i = 0; i < length; i++){
			String inputLine = reader.readLine();
			for(int j = 0; j < length; j++){
				data[i][j] = inputLine.charAt(j) == '1';
			}
		}
		reader.close();
		
		boolean[][] isSquare = new boolean[length][length];
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				isSquare[i][j] = data[i][j];
			}
		}
		
		for(int currentSize = 2; currentSize <= length; currentSize++){
			int currentCount = 0;
			int max = length + 1 - currentSize;
			for(int i = 0; i < max; i++){
				
				for(int j = 0; j < max; j++){
					if(!isSquare[i][j] || !isSquare[i+1][j] || !isSquare[i][j+1] || !isSquare[i+1][j+1]){
						isSquare[i][j] = false;
						continue;
					}
					
					currentCount++;
				}
			}
			if(currentCount != 0){
				printer.println(currentSize + " " + currentCount);
			}
			else{
				break;
			}
			
			
		}
		printer.close();
		System.exit(0);
	}
}
