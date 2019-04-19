import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_417D {

	static ArrayList<Integer>[] after;

	static int[] sPos;
	static int[] last;
	static int timer;

	static int[] numChld;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numV = Integer.parseInt(inputData.nextToken());
		int numI = Integer.parseInt(inputData.nextToken());

		int initCnt = Integer.parseInt(inputData.nextToken());
		int queryCnt = Integer.parseInt(inputData.nextToken());

		int[] lOwner = new int[numI + 1];

		after = new ArrayList[numV + 1];

		for (int i = 1; i <= numV; i++) {
			after[i] = new ArrayList<Integer>();
		}

		boolean[] root = new boolean[numV + 1];
		Arrays.fill(root, true);
		for (int i = 1; i <= initCnt; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int rV = Integer.parseInt(inputData.nextToken());
			int rI = Integer.parseInt(inputData.nextToken());
			if (lOwner[rI] != 0) {
				after[lOwner[rI]].add(rV);
				root[rV] = false;
			}
			lOwner[rI] = rV;
		}

		sPos = new int[numV + 1];
		last = new int[numV + 1];
		numChld = new int[numV + 1];

		for (int i = 1; i <= numV; i++) {
			if (root[i]) {
				dfs(i);
			}
		}

		for (int i = 1; i <= queryCnt; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int rV = Integer.parseInt(inputData.nextToken());
			int rI = Integer.parseInt(inputData.nextToken());
			if (lOwner[rI] == 0 || sPos[lOwner[rI]] < sPos[rV] || last[rV] < sPos[lOwner[rI]]) {
				printer.println(0);
			} else {
				printer.println(numChld[rV]);
			}
		}
		printer.close();
	}

	static void dfs(int cV) {
		sPos[cV] = timer++;

		int cLast = sPos[cV];
		int cChld = 1;
		for (int adj : after[cV]) {
			dfs(adj);
			cLast = last[adj];
			cChld += numChld[adj];
		}
		last[cV] = cLast;
		numChld[cV] = cChld;
	}

}
