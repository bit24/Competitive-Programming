import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: tour
*/

public class tour {

	static int[][] numV;
	static boolean[][] edge;

	static int numVertices;
	static int numEdges;

	static HashMap<String, Integer> index = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		input();
		executeDPRoutine();

		int ans = numV[numVertices - 1][numVertices - 1] - 1;
		if (ans <= 0) {
			ans = 1;
		}
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("tour.out")));
		printer.println(ans);
		printer.close();
	}

	public static void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tour.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());
		edge = new boolean[numVertices][numVertices];

		for (int i = 0; i < numVertices; i++) {
			String str = reader.readLine().trim();
			index.put(str, i);
		}
		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int ind0 = index.get(inputData.nextToken());
			int ind1 = index.get(inputData.nextToken());
			edge[ind0][ind1] = true;
			edge[ind1][ind0] = true;
		}
		reader.close();
	}

	public static void executeDPRoutine() {
		numV = new int[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				numV[i][j] = -1;
			}
		}
		numV[0][0] = 1;
		
		for(int i = 0; i < numVertices; i++){
			if(edge[0][i]){
				numV[0][i] = 2;
				numV[i][0] = 2;
			}
		}

		for (int a = 0; a < numVertices; a++) {
			for (int b = 0; b < numVertices; b++) {
				if (a == b) {
					continue;
				}
				
				if(numV[a][b] < 0){
					continue;
				}

				if (a < b) {
					for (int nextA = a + 1; nextA < numVertices; nextA++) {
						if (edge[a][nextA] && numV[nextA][b] < numV[a][b] + 1) {
							numV[nextA][b] = numV[a][b] + 1;
						}
					}
				} else {
					for (int nextB = b + 1; nextB < numVertices; nextB++) {
						if (edge[b][nextB] && numV[a][nextB] < numV[a][b] + 1) {
							numV[a][nextB] = numV[a][b] + 1;
						}
					}
				}
			}
		}
	}
}
