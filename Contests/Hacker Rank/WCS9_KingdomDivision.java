import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WCS9_KingdomDivision {

	public static void main(String[] args) throws IOException {
		new WCS9_KingdomDivision().execute();
	}

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numV = Integer.parseInt(reader.readLine());
		for (int i = 0; i <= numV; i++) {
			aList.add(new ArrayList<Integer>());
			children.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList.get(a).add(b);
			aList.get(b).add(a);
		}
		reader.close();

		dfsC(1, new boolean[numV + 1]);

		tCount = new long[2][numV + 1];

		dfsA(1);

		long ans = tCount[UNSPONSORED][1] * 2;

		ans = (ans + MOD) % MOD;
		printer.println(ans);
		printer.close();
	}

	long MOD = 1_000_000_007;

	long[][] tCount = new long[2][];
	// 2 states
	// parent sponsored
	// unsponsored

	int SPONSORED = 0;
	int UNSPONSORED = 1;

	void dfsA(int cV) {
		for (int child : children.get(cV)) {
			dfsA(child);
		}

		// case 1: sponsored children can be anything as long as they sponsor themselves

		long cCount = 1;
		for (int child : children.get(cV)) {
			cCount = (cCount * (tCount[0][child] + tCount[1][child])) % MOD;
		}
		tCount[SPONSORED][cV] = cCount;

		// case 2: at least 1 child has to have the same state
		long unsatisfied = 1;
		long satisfied = 0;

		for (int child : children.get(cV)) {
			satisfied = (satisfied * (tCount[0][child] + tCount[1][child])) % MOD;
			satisfied = (satisfied + (unsatisfied * tCount[SPONSORED][child])) % MOD;
			unsatisfied = (unsatisfied * tCount[UNSPONSORED][child]) % MOD;
		}
		tCount[UNSPONSORED][cV] = satisfied;
	}

	void dfsC(int i, boolean[] visited) {
		visited[i] = true;
		for (int neighbor : aList.get(i)) {
			if (!visited[neighbor]) {
				children.get(i).add(neighbor);
				dfsC(neighbor, visited);
			}
		}
	}

}
