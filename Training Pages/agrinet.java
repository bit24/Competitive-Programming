import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*ID: eric.ca1
LANG: JAVA
TASK: agrinet
*/
public class agrinet {
	
	public static int[] reference = new int[0];
	
	public static agrinet helper = new agrinet();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("agrinet.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("agrinet.out")));
		int numFarmsPerRow = Integer.parseInt(reader.readLine());
		int totalNumFarms = (int) Math.pow(numFarmsPerRow, 2);
		int[][] adjacMatrix = new int[numFarmsPerRow][numFarmsPerRow];		
		
		for(int i = 0; i < numFarmsPerRow; i++){
			String[] data = reader.readLine().split(" ");
			int dataIterator = 0;
			for(int j = 0; j < numFarmsPerRow; j++){
				if(data.length <= dataIterator){
					data = reader.readLine().split(" ");
					dataIterator = 0;
				}
				adjacMatrix[i][j] = Integer.parseInt(data[dataIterator++]);
			}
		}
		reader.close();
		
		reference = new int[totalNumFarms];
		Arrays.fill(reference, Integer.MAX_VALUE);
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(totalNumFarms, helper. new aFunction());
		int fiberNeeded = 0;
		reference[0] = 0;
		for(int i = 1; i < adjacMatrix[0].length; i++){
			if(adjacMatrix[0][i] != Integer.MAX_VALUE){
				reference[i] = adjacMatrix[0][i];
				queue.add(i);
			}
		}
		
		while(!queue.isEmpty()){
			int currentNode = queue.poll();
			fiberNeeded += reference[currentNode];
			
			reference[currentNode] = 0;
						
			for(int i = 0; i < adjacMatrix[currentNode].length; i++){
				if(reference[i] > adjacMatrix[currentNode][i]){
					reference[i] = adjacMatrix[currentNode][i];
					queue.remove(Integer.valueOf(i));
					queue.add(i);
				}
			}
		}
		
		printer.println(fiberNeeded);
		printer.close();
	}
	
	class aFunction implements Comparator<Integer>{

		
		public int compare(Integer arg0, Integer arg1) {
			if(reference[arg0] < reference[arg1]){
				return -1;
			}
			if(reference[arg0] > reference[arg1]){
				return 1;
			}
			return 0;
		}
		
	}

}
