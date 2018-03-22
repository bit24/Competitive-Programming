import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


/*ID: eric.ca1
LANG: JAVA
TASK: concom
*/

public class concom {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("concom.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(
				new FileWriter("concom.out")));
		reader.readLine();
		int[][] ownershipPercentage = new int[101][];
		for(int i = 0; i < ownershipPercentage.length; i++){
			ownershipPercentage[i] = new int[101];
		}
		String inputData = reader.readLine();
		while(inputData != null){
			String[] data = inputData.split(" ");
			ownershipPercentage[Integer.parseInt(data[0])][Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
			inputData = reader.readLine();
		}
		reader.close();
		
		ArrayList<int[]> solutionSet = new ArrayList<int[]>();
		
		for(int currentCompany = 1; currentCompany <= 100; currentCompany++){
			boolean[] visited = new boolean[101];
			
			LinkedList<Integer> checkingQueue = new LinkedList<Integer>();
			int[] currentOwnershipAmounts = Arrays.copyOf(ownershipPercentage[currentCompany], 101);
			
			for(int ownedCompany = 1; ownedCompany <= 100; ownedCompany++){
				if(currentOwnershipAmounts[ownedCompany] > 50 && !visited[ownedCompany]){
					checkingQueue.add(ownedCompany);
					visited[ownedCompany] = true;
				}
			}
			
			while(!checkingQueue.isEmpty()){
				int ownedCompany = checkingQueue.removeFirst();
				solutionSet.add(new int[]{currentCompany, ownedCompany});
				int[] ownedCompAmounts = ownershipPercentage[ownedCompany];
				
				for(int subOwnedCompany = 1; subOwnedCompany <= 100; subOwnedCompany++){
					currentOwnershipAmounts[subOwnedCompany] += ownedCompAmounts[subOwnedCompany];
					if(currentOwnershipAmounts[subOwnedCompany] > 50 && !visited[subOwnedCompany]){
						checkingQueue.add(subOwnedCompany);
						visited[subOwnedCompany] = true;
					}
				}
			}
			
		}
		
		Collections.sort(solutionSet, new concom().new comparer());
		for(int[] currentOutput : solutionSet){
			if(currentOutput[0] == currentOutput[1]){
				continue;
			}
			printer.println(currentOutput[0] + " " + currentOutput[1]);
		}
		printer.close();
	}
	
	class comparer implements Comparator<int[]>{
		public int compare(int[] array1, int[] array2){
			for(int i = 0; i < 2; i++){
				if(array1[i] < array2[i]){
					return -1;
				}
				else if(array1[i] > array2[i]){
					return 1;
				}
			}
			return -1;
		}
	}

}
