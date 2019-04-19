import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div2_411E {

	static ArrayList<Integer>[] aList;

	static int[] color;

	static int[][] markings;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numM = Integer.parseInt(inputData.nextToken());

		markings = new int[numV + 1][];
		for (int i = 1; i <= numV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int cNumM = Integer.parseInt(inputData.nextToken());
			markings[i] = new int[cNumM + 1];
			for (int j = 1; j <= cNumM; j++) {
				markings[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		aList = new ArrayList[numV + 1];
		for (int i = 1; i <= numV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i < numV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
		}
		reader.close();

		color = new int[numM + 1];
		visited = new boolean[numV + 1];

		dfs(1);

		for (int i = 1; i <= numM; i++) {
			if (color[i] == 0) {
				color[i] = 1;
			}
		}

		int mMark = 0;
		for (int i = 1; i <= numM; i++) {
			if (color[i] > mMark) {
				mMark = color[i];
			}
		}
		printer.println(mMark);
		for (int i = 1; i <= numM; i++) {
			printer.print(color[i] + " ");
		}
		printer.println();
		printer.close();
	}

	static boolean[] visited;

	static void dfs(int cV) {
		visited[cV] = true;

		ArrayList<Integer> adjC = new ArrayList<Integer>();
		for (int cMarkI = 1; cMarkI < markings[cV].length; cMarkI++) {
			int cMark = markings[cV][cMarkI];
			if (color[cMark] != 0) {
				adjC.add(color[cMark]);
			}
		}
		Collections.sort(adjC);

		int nxtF = 1;
		int nxtC = 0;

		for (int cMarkI = 1; cMarkI < markings[cV].length; cMarkI++) {
			int cMark = markings[cV][cMarkI];
			if (color[cMark] != 0) {
				continue;
			}

			while (nxtC < adjC.size() && nxtF == adjC.get(nxtC)) {
				nxtF++;
				nxtC++;
			}

			color[cMark] = nxtF++;
		}

		for (int adj : aList[cV]) {
			if (!visited[adj]) {
				dfs(adj);
			}
		}
	}

}
