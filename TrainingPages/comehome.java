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
 TASK: comehome
 */
public class comehome {
	
	public static int[] distance;
	
	public static comehome helper = new comehome();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("comehome.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("comehome.out")));
		int numPaths = Integer.parseInt(reader.readLine());
		int[][] edges = new int[53][];
		for(int i = 0; i < edges.length; i++){
			int[] currentRow = new int[53];
			edges[i] = currentRow;
			for(int j = 0; j < currentRow.length; j++){
				currentRow[j] = Integer.MAX_VALUE;
			}
		}
		
		for(int i = 1; i <= numPaths; i++){
			String[] data = reader.readLine().split(" ");
			edges[exchange(data[0])][exchange(data[1])] =  Math.min(edges[exchange(data[0])][exchange(data[1])], Integer.parseInt(data[2]));
			edges[exchange(data[1])][exchange(data[0])] =  Math.min(edges[exchange(data[1])][exchange(data[0])], Integer.parseInt(data[2]));
		}
		reader.close();
		
		
		distance = new int[53];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[52] = 0;
		
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(52, helper. new aFunction());
		queue.add(52);
		
		int distanceLength = 0;
		char pasture = 'A';
		
		while(!queue.isEmpty()){
			int currentNode = queue.poll();
			if(isUpperCase(currentNode)){
				distanceLength = distance[currentNode];
				pasture = toChar(currentNode);
				break;
			}
			for(int i = 1; i < edges.length; i++){
				if(edges[currentNode][i] != Integer.MAX_VALUE){
					if(edges[currentNode][i] + distance[currentNode] < distance[i]){
						distance[i] = edges[currentNode][i] + distance[currentNode];
						queue.add(i);
					}
				}
			}
		}
		
		printer.println(pasture + " " + distanceLength);
		printer.close();
		
	}
	
	public static int exchange(String characterS){
		char character = characterS.charAt(0);
		if(Character.isLowerCase(character)){
			return character-96;
		}
		else{
			return character-38;
		}
	}
	
	public static boolean isUpperCase(int i){
		if(i > 26 && i < 52){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static char toChar(int i){
		return (char) (i+38);
	}
	
	public class aFunction implements Comparator<Integer>{

		public int compare(Integer arg0, Integer arg1) {
			return Double.compare(distance[arg0], distance[arg1]);
		}
		 
	}

}
