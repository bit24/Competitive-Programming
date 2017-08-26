import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/*ID: eric.ca1
 LANG: JAVA
 TASK: msquare
 */
public class msquare {

	public static msquare helper = new msquare();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("msquare.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
		String[] unFilteredInput = reader.readLine().split(" ");
		byte[] goalArray = new byte[8];
		int goalArrayIterator = 0;

		for (String currentString : unFilteredInput) {
			if (!currentString.equals("")) {
				goalArray[goalArrayIterator++] = Byte.parseByte(currentString);
			}
		}
		reader.close();		
		
		byte[] startNodeArray = new byte[8];
		for (byte i = 0; i < 8; i++) {
			startNodeArray[i] = (byte) (i+1);
		}
		
		specByteArrayData startNode = helper. new specByteArrayData();
		startNode.intValue = 0;
		
		HashMap<specByteArray, specByteArrayData> visited = new HashMap<specByteArray, specByteArrayData>();
		
		
		visited.put(helper. new specByteArray(startNodeArray), startNode);
		
		
		LinkedList<specByteArray> queue = new LinkedList<specByteArray>();
		queue.add(helper. new specByteArray(startNodeArray));
				
		ArrayList<specByteArray> stuff = new ArrayList<specByteArray>();
		
		while(true){
			specByteArray currentNode = queue.removeFirst();
			stuff.add(currentNode);
			if(Arrays.equals(currentNode.value, goalArray)){
				System.out.println(visited.containsKey(currentNode));
				break;
			}
			
			specByteArrayData currentNodeData = visited.get(currentNode);
			int currentNodeCost = currentNodeData.intValue;
			
			for(char currentSwap : swaps){
				specByteArray neighbor = swap(currentNode, currentSwap);
				
				if(!visited.containsKey(neighbor)){
					specByteArrayData neighborData = helper. new specByteArrayData();
					neighborData.intValue = currentNodeCost+1;
					neighborData.previous = currentNodeData;
					neighborData.swap = currentSwap;
					visited.put(neighbor, neighborData);
					queue.add(neighbor);
				}
				else if(visited.get(neighbor).intValue == currentNodeCost+1){
					specByteArrayData neighborData = visited.get(neighbor);
					if(isBetterPath(neighborData.previous, currentNodeData)){
						neighborData.previous = neighborData;
						neighborData.swap = currentSwap;
					}
				}
				
			}
		}
		
		LinkedList<Character> path = new LinkedList<Character>();
		System.out.println(visited.containsKey(goalArray));
		for(specByteArrayData currentData = visited.get(helper. new specByteArray(goalArray)); currentData.previous != null; currentData = currentData.previous){
			path.addFirst(currentData.swap);
		}
		
		printer.println(path.size());
		Iterator<Character> pathIterator = path.iterator();
		int currentCharacterCount = 0;
		while(pathIterator.hasNext()){
			if(currentCharacterCount == 60){
				printer.println();
			}
			printer.print(pathIterator.next());
		}
		printer.println();
		printer.close();
		
	}
	
	public static boolean isBetterPath(specByteArrayData currentPath, specByteArrayData otherPath){
		LinkedList<Character> path1 = new LinkedList<Character>();
		for(specByteArrayData currentData = currentPath; currentData.previous != null; currentData = currentData.previous){
			path1.addFirst(currentData.swap);
		}
		LinkedList<Character> path2 = new LinkedList<Character>();
		for(specByteArrayData currentData = otherPath; currentData.previous != null; currentData = currentData.previous){
			path2.addFirst(currentData.swap);
		}
		
		for(int currentIndex = 0; currentIndex < path1.size(); currentIndex++){
			int comparisonResult = compareCharacters(path1.get(currentIndex), path2.get(currentIndex));
			if(comparisonResult < 0){
				return true;
			}
			if(comparisonResult > 0){
				return false;
			}
		}
		return false;
	}
	
	public static int compareCharacters(char character1, char character2){
		return -new Character(character1).compareTo(character2);
	}
	
	public class specByteArray{
		public byte[] value;
		
		public specByteArray(byte[] value){
			this.value = value;
		}
		
		public specByteArray(){
			value = new byte[8];
		}
		
		public int hashCode(){
			return Arrays.hashCode(value);
		}
		
		public boolean equals(Object other){
			return Arrays.equals(value, ((specByteArray) other).value);
		}
		
	}
	
	public class specByteArrayData{
		public int intValue = Integer.MAX_VALUE;
		specByteArrayData previous = null;
		public char swap = 'N';
	}
	

	public static char[] swaps = new char[] { 'A', 'B', 'C' };

	public static specByteArray swap(specByteArray startArray, char swap) {
		byte[] startSequence = startArray.value;
		byte[] returnSequence = new byte[8];
		if (swap == 'A') {
			returnSequence[0] = startSequence[7];
			returnSequence[1] = startSequence[6];
			returnSequence[2] = startSequence[5];
			returnSequence[3] = startSequence[4];
			returnSequence[4] = startSequence[3];
			returnSequence[5] = startSequence[2];
			returnSequence[6] = startSequence[1];
			returnSequence[7] = startSequence[0];
		}
		else
		if (swap == 'B') {
			returnSequence[0] = startSequence[3];
			returnSequence[1] = startSequence[0];
			returnSequence[2] = startSequence[1];
			returnSequence[3] = startSequence[2];
			returnSequence[4] = startSequence[5];
			returnSequence[5] = startSequence[6];
			returnSequence[6] = startSequence[7];
			returnSequence[7] = startSequence[4];
		}
		else
		if (swap == 'C') {
			returnSequence = Arrays.copyOf(startSequence, 8);
			returnSequence[1] = startSequence[6];
			returnSequence[2] = startSequence[1];
			returnSequence[5] = startSequence[2];
			returnSequence[6] = startSequence[5];
		}
		else{
			throw new RuntimeException();
		}
		
		return helper. new specByteArray(returnSequence);
	}


}
