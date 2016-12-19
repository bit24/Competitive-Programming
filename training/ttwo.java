import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
LANG: JAVA
TASK: ttwo
*/
public class ttwo {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("ttwo.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("ttwo.out")));
		
		String inputLine = null;
		char[][] grid = new char[10][];
		
		int[] cowCoor = null;
		int[] johnCoor = null;
		
		for(int currentRow = 0; currentRow < 10; currentRow++){
			inputLine = reader.readLine();
			grid[currentRow] = new char[10];
			for(int currentColumn = 0; currentColumn < 10; currentColumn++){
				char currentChar = inputLine.charAt(currentColumn);
				if(currentChar == 'C'){
					cowCoor = new int[]{currentRow, currentColumn};
				}
				if(currentChar == 'F'){
					johnCoor = new int[]{currentRow, currentColumn};
				}
				grid[currentRow][currentColumn] = inputLine.charAt(currentColumn);
			}
		}
		
		reader.close();
		
		Integer cowDirection = new Integer(1);
		Integer johnDirection = new Integer(1);
		boolean found = false;
		
		int currentTime = 0; 
		for(currentTime = 1; currentTime <= 160000; currentTime++){
			cowDirection = move(grid, cowCoor, cowDirection);
			johnDirection = move(grid, johnCoor, johnDirection);
			if(cowCoor[0] == johnCoor[0] && cowCoor[1] == johnCoor[1]){
				found = true;
				break;
			}
		}
		if(found){
			printer.println(currentTime);
		}
		else{
			printer.println(0);
		}
		printer.close();
		
		
	}
	
	public static int move(char[][] grid, int[] coordinates, int direction){
		if(direction == 1){
			if(coordinates[0] == 0 || grid[coordinates[0]-1][coordinates[1]] == '*'){
				direction += 1;
			}
			else{
				coordinates[0] -= 1;
			}
		}
		else if(direction == 2){
			if(coordinates[1] == 9 || grid[coordinates[0]][coordinates[1]+1] == '*'){
				direction  += 1;
			}
			else{
				coordinates[1] += 1;
			}
		}
		else if(direction == 3){
			if(coordinates[0] == 9 || grid[coordinates[0] + 1][coordinates[1]] == '*'){
				direction += 1;
			}
			else{
				coordinates[0] += 1;
			}
		}
		else if(direction == 4){
			if(coordinates[1] == 0 || grid[coordinates[0]][coordinates[1]-1] == '*'){
				direction = 1;
			}
			else{
				coordinates[1] -= 1;
			}
		}
		return direction;
	}

}
