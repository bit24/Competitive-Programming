import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class birthday {

	static int bA;
	static int bB;

	static boolean[] visited;

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		tLoop:
		while (true) {
			String nLine = reader.readLine();
			if (nLine.equals("0 0")) {
				break;
			}
			StringTokenizer inputData = new StringTokenizer(nLine);
			int nV = Integer.parseInt(inputData.nextToken());
			int nE = Integer.parseInt(inputData.nextToken());
			aList = new ArrayList[nV];
			for (int i = 0; i < nV; i++) {
				aList[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < nE; i++) {
				inputData = new StringTokenizer(reader.readLine());
				int a = Integer.parseInt(inputData.nextToken());
				int b = Integer.parseInt(inputData.nextToken());
				aList[a].add(b);
				aList[b].add(a);
			}

			for (int cV = 0; cV < nV; cV++) {
				for (int aV : aList[cV]) {
					visited = new boolean[nV];
					bA = cV;
					bB = aV;
					dfs(0);
					for (int i = 0; i < nV; i++) {
						if(!visited[i]) {
							printer.println("Yes");
							continue tLoop;
						}
					}
				}
			}
			printer.println("No");
		}
		printer.close();
	}

	static void dfs(int cV) {
		visited[cV] = true;
		for (int aV : aList[cV]) {
			if ((cV == bA && aV == bB) || (cV == bB && aV == bA)) {
				continue;
			}
			if (visited[aV]) {
				continue;
			}
			dfs(aV);
		}
	}

}
