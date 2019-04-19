import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Museum {

	public static void main(String[] args) throws IOException {
		new Museum().execute();
	}

	int nV;
	int req;
	int root;

	ArrayList<Edge>[] aList;

	int[] stSize;

	int[][] costR;
	int[][] costNR;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		req = Integer.parseInt(inputData.nextToken());
		root = Integer.parseInt(inputData.nextToken()) - 1;

		aList = new ArrayList[nV];
		stSize = new int[nV];
		costR = new int[nV][];
		costNR = new int[nV][];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Edge>();
		}
		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			aList[a].add(new Edge(b, c));
			aList[b].add(new Edge(a, c));
		}
		reader.close();
		
		evaluate(root, -1);
		printer.println(costNR[root][req]);
		printer.close();
	}

	void evaluate(int cV, int pV) {
		stSize[cV] = 1;
		for (Edge cE : aList[cV]) {
			int aV = cE.v;
			if (aV != pV) {
				evaluate(aV, cV);
				stSize[cV] += stSize[aV];
			}
		}
		int cSize = stSize[cV];

		int[] cCostR = costR[cV] = new int[cSize + 1];
		int[] cCostNR = costNR[cV] = new int[cSize + 1];
		Arrays.fill(cCostR, Integer.MAX_VALUE);
		Arrays.fill(cCostNR, Integer.MAX_VALUE);
		cCostR[0] = cCostR[1] = cCostNR[0] = cCostNR[1] = 0;
		int cMPos = 1;

		for (Edge cE : aList[cV]) {
			int aV = cE.v;
			if (aV == pV) {
				continue;
			}
			int aC = cE.c;

			int[] aCostR = costR[aV];
			int[] aCostNR = costNR[aV];
			int aMPos = aCostR.length - 1;

			for (int i = cMPos; i >= 0; i--) {
				for (int j = 1; j <= aMPos; j++) {
					cCostR[i + j] = Math.min(cCostR[i + j], cCostR[i] + aCostR[j] + 2 * aC);
					cCostNR[i + j] = Math.min(cCostNR[i + j],
							Math.min(cCostNR[i] + aCostR[j] + 2 * aC, cCostR[i] + aCostNR[j] + aC));
				}
			}
			cMPos += aMPos;
		}
	}

	class Edge {
		int v;
		int c;

		Edge(int v, int c) {
			this.v = v;
			this.c = c;
		}
	}

}
