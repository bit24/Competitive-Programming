import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;

/*ID: eric.ca1
 LANG: JAVA
 TASK: maze1
 */
public class maze1 {

	public static maze1 helper = new maze1();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("maze1.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("maze1.out")));
		String[] inputArray = reader.readLine().split(" ");
		int width = Integer.parseInt(inputArray[0]);
		int height = Integer.parseInt(inputArray[1]);
		
		node[] allNodes = new node[width*height];
		int allNodesIterator = 0;
		node[] entranceNodes = new node[2];
		int entranceNodesCount = 0;
		node[] previousRow = null;
		node[] currentRow = new node[width];
		
		for (int currentHeight = 0; currentHeight < height; currentHeight++) {
			
			String upperLine = reader.readLine();
			String currentLine = reader.readLine();
			for(int currentWidth = 0; currentWidth < width; currentWidth++){
				
				node newNode = helper. new node();
				currentRow[currentWidth] = newNode;
				allNodes[allNodesIterator++] = newNode; 
				
				if(upperLine.charAt(currentWidth*2+1) == ' '){
					if(currentHeight != 0){
						previousRow[currentWidth].neighbors[3] = newNode;
						newNode.neighbors[1] = previousRow[currentWidth];
					}
					else{
						entranceNodes[entranceNodesCount++] = newNode;
					}
				}
				
				if(currentLine.charAt(currentWidth*2) == ' '){
					if(currentWidth != 0){
						currentRow[currentWidth-1].neighbors[2] = newNode;
						newNode.neighbors[4] = currentRow[currentWidth-1];
					}
					else{
						entranceNodes[entranceNodesCount++] = newNode;
					}
					
				}
				
				if(currentWidth == width - 1 && currentLine.charAt(width*2) == ' '){
					entranceNodes[entranceNodesCount++] = newNode;
				}
				
			}
			previousRow = currentRow;
			currentRow = new node[width];
		}
		String inputString = reader.readLine();
		for(int i = 0; i < inputString.length(); i++){
			if(inputString.charAt(i) == ' '){
				entranceNodes[entranceNodesCount++] = previousRow[(i-1)/2];
			}
		}
		reader.close();
		
		PriorityQueue<node> queue = new PriorityQueue<node>();
		entranceNodes[0].distance = 1;
		entranceNodes[1].distance = 1;
		queue.add(entranceNodes[0]);
		queue.add(entranceNodes[1]);
		
		while(!queue.isEmpty()){
			node currentNode = queue.poll();
			for(node neighborNode : currentNode.neighbors){
				if(neighborNode == null){
					continue;
				}
				if(neighborNode.distance > currentNode.distance+1){
					neighborNode.distance = currentNode.distance+1;
					queue.add(neighborNode);
				}
			}
		}
		
		int maxDistance = 0;
		for(node currentNode : allNodes){
			if(currentNode.distance > maxDistance){
				maxDistance = currentNode.distance;
			}
		}
		
		printer.println(maxDistance);
		printer.close();
		
	}
	
	public class node implements Comparable<node>{
		public node[] neighbors = new node[5];
		int distance = Integer.MAX_VALUE;
		public int compareTo(node otherNode){
			if(distance < otherNode.distance){
				return -1;
			}
			if(distance > otherNode.distance){
				return 1;
			}
			return 0;
		}
	}

}
