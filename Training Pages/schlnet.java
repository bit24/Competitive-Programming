import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: schlnet
*/

public class schlnet {
	static int numV;
	static boolean[][] aMatrix;
	static int[] disc;
	static int[] low;
	static int currentTime = 0;
	static Stack<Integer> pStack = new Stack<Integer>();
	static ArrayList<ArrayList<Integer>> SCCs = new ArrayList<ArrayList<Integer>>();
	static ArrayList<Integer> operational = new ArrayList<Integer>();
	static boolean[] isHead;
	static boolean[] isTail;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("schlnet.in"));

		numV = Integer.parseInt(reader.readLine());
		aMatrix = new boolean[numV][numV];
		for (int i = 0; i < numV; i++) {
			StringTokenizer tokens = new StringTokenizer(reader.readLine());
			int nT = Integer.parseInt(tokens.nextToken()) - 1;
			while (nT != -1) {
				aMatrix[i][nT] = true;
				nT = Integer.parseInt(tokens.nextToken()) - 1;
			}
		}
		reader.close();
		
		compressSCCs();
		computeHT();
		calcNPrintAns();
	}
	public static void calcNPrintAns() throws IOException{
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("schlnet.out")));
		int numHeads = 0;
		for(int i : operational){
			if(isHead[i]){
				numHeads++;
			}
		}
		printer.println(numHeads);
		
		if(operational.size() != 1){
			int numTails = 0;
			for(int i : operational){
				if(isTail[i]){
					numTails++;
				}
			}
			printer.println(Math.max(numHeads, numTails));
		}
		else{
			printer.println(0);
		}
		
		printer.close();
	}
	
	public static void computeHT(){
		isHead = new boolean[numV];
		Arrays.fill(isHead, true);
		isTail = new boolean[numV];
		Arrays.fill(isTail, true);
		
		for(int i : operational){
			
			for(int j : operational){
				if(i == j){
					continue;
				}
				if(aMatrix[i][j]){
					isHead[j] = false;
					isTail[i] = false;
				}
			}
		}
	}
	
	public static void compressSCCs(){
		findSCCs();
		for(ArrayList<Integer> SCC : SCCs){
			compress(SCC);
		}
	}

	public static void compress(ArrayList<Integer> SCC) {
		int newDes = SCC.get(0);
		for (int member : SCC) {
			for(int i = 0; i < numV; i++){
				if(aMatrix[i][member]){
					aMatrix[i][member] = false;
					aMatrix[i][newDes] = true;
				}
				if(aMatrix[member][i]){
					aMatrix[member][i] = false;
					aMatrix[newDes][i] = true;
				}
			}
			
		}
		aMatrix[newDes][newDes] = false;
		operational.add(newDes);
	}
	
	public static void findSCCs(){
		disc = new int[numV];
		Arrays.fill(disc, -1);
		low = new int[numV];
		Arrays.fill(low, Integer.MAX_VALUE);
		for(int i = 0; i < numV; i++){
			if(disc[i] == -1){
				dfs(i);
			}
		}
	}

	public static void dfs(int current) {
		disc[current] = low[current] = currentTime++;
		pStack.push(current);

		for (int neighbor = 0; neighbor < numV; neighbor++) {
			if (!aMatrix[current][neighbor]) {
				continue;
			}
			if (disc[neighbor] == -1) {
				dfs(neighbor);
			}
			if (low[neighbor] < low[current]) {
				low[current] = low[neighbor];
			}
		}

		if (disc[current] == low[current]) {
			ArrayList<Integer> SCC = new ArrayList<Integer>();
			while (pStack.peek() != current) {
				low[pStack.peek()] = Integer.MAX_VALUE;
				SCC.add(pStack.pop());
			}
			low[pStack.peek()] = Integer.MAX_VALUE;
			SCC.add(pStack.pop());
			SCCs.add(SCC);
		}
	}
}
