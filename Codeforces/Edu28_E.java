import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu28_E {

	static long[] sup;

	static int[] sConv;
	static int[] rev;

	static ArrayList<Integer>[] rList;

	static PrintWriter printer;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());
		sup = new long[nV];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			sup[i] = Long.parseLong(inputData.nextToken());
		}
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			sup[i] -= Long.parseLong(inputData.nextToken());
		}

		sConv = new int[nV];
		rev = new int[nV];

		ArrayList<int[]> trans = new ArrayList<>();

		for (int i = 1; i < nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int type = Integer.parseInt(inputData.nextToken()) - 1;
			int amt = Integer.parseInt(inputData.nextToken());
			sConv[i] = type;
			trans.add(new int[] { type, amt, i });
		}

		cInd = new int[nV];
		for (int i = 0; i < nV; i++) {
			cInd[i] = i;
		}
		lVisit = new int[nV];
		Arrays.fill(lVisit, -1);

		int cN = 0;
		for (int i = 0; i < nV; i++) {
			if (lVisit[i] == -1) {
				cPath.clear();
				search(i, cN++);
			}
		}

		for (int i = 0; i < nV; i++) {
			sConv[i] = sConv[cInd[i]];
		}

		rList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			rList[i] = new ArrayList<Integer>();
		}

		for (int[] cTran : trans) {
			int indM = cInd[cTran[0]];
			int indS = cInd[cTran[2]];
			if (indM != indS) {
				rev[indS] = cTran[1];
				rList[indM].add(indS);
			}
		}

		visited = new boolean[nV];
		for (int i = 0; i < nV; i++) {
			if (!visited[i]) {
				dfs(i);
			}
		}

		for (int i = 0; i < nV; i++) {
			if (sup[i] < 0) {
				printer.println("NO");
				printer.close();
				return;
			}
		}
		printer.println("YES");
		printer.close();
	}

	static boolean[] visited;

	static void dfs(int cV) {
		visited[cV] = true;
		for (int pV : rList[cV]) {
			if (!visited[pV]) {
				dfs(pV);
			}
		}

		if (sConv[cV] != -1) {
			if (sup[cV] >= 0) {
				sup[sConv[cV]] += sup[cV];
			} else {
				try {
					long val = Math.multiplyExact(rev[cV], sup[cV]);
					sup[sConv[cV]] = Math.addExact(sup[sConv[cV]], val);
				} catch (ArithmeticException e) {
					printer.println("NO");
					printer.close();
					System.exit(0);
				}
			}
			sup[cV] = 0;
		}
	}

	static int[] cInd;

	static int[] lVisit;
	static ArrayDeque<Integer> cPath = new ArrayDeque<>();

	static void search(int cV, int cN) {
		lVisit[cV] = cN;
		cPath.add(cV);

		int aV = sConv[cV];

		if (lVisit[aV] == cN) {
			while (cPath.getLast() != aV) {
				cInd[cPath.removeLast()] = cV;
			}
			cInd[aV] = cV;
			sConv[cV] = -1;
			return;
		}
		if (lVisit[aV] == -1) {
			search(aV, cN);
		}
	}
}
