import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div1_232C {

	public static void main(String[] args) throws IOException {
		new Div1_232C().execute();
	}

	long MOD = 1000000007L;

	int numV;
	int numQ;

	ArrayList<Integer>[] children;
	int[] depth;
	int[] start;
	int[] end;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		numV = Integer.parseInt(reader.readLine());
		children = new ArrayList[numV + 1];

		for (int i = 1; i <= numV; i++) {
			children[i] = new ArrayList<Integer>();
		}

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 2; i <= numV; i++) {
			children[Integer.parseInt(inputData.nextToken())].add(i);
		}

		depth = new int[numV + 1];
		start = new int[numV + 1];
		end = new int[numV + 1];
		dfs(1);

		numQ = Integer.parseInt(reader.readLine());

		pSum1 = new long[numV + 1];
		pSum2 = new long[numV + 1];

		while (numQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());

			if (inputData.nextToken().charAt(0) == '1') {
				int uV = Integer.parseInt(inputData.nextToken());
				long base = Long.parseLong(inputData.nextToken()) % MOD;
				long decr = Long.parseLong(inputData.nextToken()) % MOD;
				updateRange(pSum1, start[uV], end[uV], (MOD + base + decr * depth[uV]) % MOD);
				updateRange(pSum2, start[uV], end[uV], decr);
			} else {
				int qV = Integer.parseInt(inputData.nextToken());
				printer.println(
						(MOD + (queryPre(pSum1, start[qV]) - depth[qV] * queryPre(pSum2, start[qV])) % MOD) % MOD);
			}
		}
		reader.close();
		printer.close();
	}

	int nI = 1;

	void dfs(int cV) {
		start[cV] = nI++;
		for (int child : children[cV]) {
			depth[child] = depth[cV] + 1;
			dfs(child);
		}
		end[cV] = nI - 1;
	}

	// one indexed as operations on index 0 does not work
	long[] pSum1;
	long[] pSum2;

	void updateRange(long[] elements, int left, int right, long delta) {
		updateInd(elements, left, delta);
		if (right + 1 <= numV) {
			updateInd(elements, right + 1, -delta);
		}
	}

	void updateInd(long[] elements, int ind, long delta) {
		while (ind <= numV) {
			elements[ind] = (elements[ind] + delta) % MOD;
			ind += (ind & -ind);
		}
	}

	long queryPre(long[] elements, int ind) {
		long sum = 0;
		while (ind > 0) {
			sum = (sum + elements[ind]) % MOD;
			ind -= (ind & -ind);
		}
		return sum;
	}

}
