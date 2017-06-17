import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_408D {

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] eList;

	static boolean[] marked;

	static int[] depth;

	static int[] clst;
	static int[] cDist;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numM = Integer.parseInt(inputData.nextToken());
		marked = new boolean[numV];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numM; i++) {
			marked[Integer.parseInt(inputData.nextToken()) - 1] = true;
		}

		aList = new ArrayList[numV];
		eList = new ArrayList[numV];

		for (int i = 0; i < numV; i++) {
			aList[i] = new ArrayList<Integer>();
			eList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
			eList[a].add(i);
			eList[b].add(i);
		}
		reader.close();

		depth = new int[numV];
		
		clst = new int[numV];
		cDist = new int[numV];
		dfs1(0, -1);
		dfs2(0, -1);

		boolean[] removed = new boolean[numV];

		for (int i = 0; i < numV; i++) {
			for (int aI = 0; aI < aList[i].size(); aI++) {
				if (clst[i] != clst[aList[i].get(aI)]) {
					removed[eList[i].get(aI)] = true;
				}
			}
		}

		int cnt = 0;
		for (boolean bool : removed) {
			if (bool) {
				cnt++;
			}
		}

		printer.println(cnt);
		for (int i = 0; i < numV; i++) {
			if (removed[i]) {
				printer.print(i + 1 + " ");
			}
		}
		printer.println();
		printer.close();
	}

	// finds clst
	static void dfs1(int cV, int pV) {
		int curClst = marked[cV] ? cV : -1;
		int curDist = 0;
		for (int adj : aList[cV]) {
			if (adj != pV) {
				depth[adj] = depth[cV] + 1;
				dfs1(adj, cV);
				if (clst[adj] != -1 && (curClst == -1 || cDist[adj] + 1 < curDist)) {
					curClst = clst[adj];
					curDist = cDist[adj] + 1;
				}
			}
		}
		clst[cV] = curClst;
		cDist[cV] = curDist;
	}

	static void dfs2(int cV, int pV) {
		if (pV != -1 && (clst[cV] == -1 || 1 + cDist[pV] < cDist[cV])) {
			cDist[cV] = 1 + cDist[pV];
			clst[cV] = clst[pV];
		}

		for (int adj : aList[cV]) {
			if (adj != pV) {
				dfs2(adj, cV);
			}
		}
	}
}
