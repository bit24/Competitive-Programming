import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_453D {

	static long[] val;

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] eList;

	static int[] color;

	static int[] pV;
	static int[] pE;

	static int cStart;
	static int cEnd;
	static int cEdge;

	static long[] eV;

	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		val = new long[nV];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			val[i] = Integer.parseInt(inputData.nextToken());
		}

		aList = new ArrayList[nV];
		eList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			eList[i] = new ArrayList<>();
		}
		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
			eList[a].add(i);
			eList[b].add(i);
		}

		color = new int[nV];
		Arrays.fill(color, -1);

		pV = new int[nV];
		pE = new int[nV];

		color[0] = 0;
		boolean isBipartite = color(0);

		eV = new long[nE];

		visited = new boolean[nV];
		if (isBipartite) {
			gDetermine(0);
			if (val[0] == 0) {
				printer.println("YES");
				for (int i = 0; i < nE; i++) {
					printer.println(-eV[i]);
				}
			} else {
				printer.println("NO");
			}
		} else {
			gDetermine(cEnd);
			long delta = -val[cEnd] / 2;
			int cV = cEnd;
			while (cV != cStart) {
				eV[pE[cV]] += delta;
				cV = pV[cV];
				delta = -delta;
			}
			eV[cEdge] += delta;
			printer.println("YES");
			for (int i = 0; i < nE; i++) {
				printer.println(-eV[i]);
			}
		}
		printer.close();
	}

	static boolean color(int cV) {
		for (int aI = 0; aI < aList[cV].size(); aI++) {
			int aV = aList[cV].get(aI);
			int aE = eList[cV].get(aI);
			if (color[aV] == -1) {
				color[aV] = color[cV] ^ 1;
				pV[aV] = cV;
				pE[aV] = aE;
				if(!color(aV)) {
					return false;
				}
			} else {
				if (color[aV] == color[cV]) {
					cEnd = cV;
					cStart = aV;
					cEdge = aE;
					return false;
				}
			}
		}
		return true;
	}

	static void gDetermine(int cV) {
		visited[cV] = true;
		for (int aI = 0; aI < aList[cV].size(); aI++) {
			int aV = aList[cV].get(aI);
			int eI = eList[cV].get(aI);
			if (!visited[aV]) {
				gDetermine(aV);
				eV[eI] = -val[aV];
				val[cV] -= val[aV];
				val[aV] = 0;
			}
		}
	}

}
