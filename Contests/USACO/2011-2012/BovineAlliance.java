import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BovineAlliance {
	
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("alliance.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("alliance.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numVertices = Integer.parseInt(inputData.nextToken());

		int numEdges = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a).add(b);
			aList.get(b).add(a);
		}
		reader.close();

		long count = 1;
		visited = new boolean[numVertices];
		for (int i = 0; i < numVertices; i++) {
			if (!visited[i]) {
				vCnt = 0;
				eCnt = 0;
				cntVE(i);
				eCnt /= 2;
				if (vCnt == 1) {
					continue;
				}
				if (eCnt == vCnt - 1) {
					count *= vCnt;
				}
				else if (eCnt == vCnt) {
					count *= 2;
				}

				count %= 1_000_000_007;
			}
		}
		printer.println(count);
		printer.close();
	}

	static int eCnt = 0;
	static int vCnt;

	static void cntVE(int v) {
		visited[v] = true;

		vCnt++;
		eCnt += aList.get(v).size();

		for (int adj : aList.get(v)) {
			if (!visited[adj]) {
				cntVE(adj);
			}
		}
	}

}