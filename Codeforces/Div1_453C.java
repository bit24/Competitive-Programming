import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_453C {

	static ArrayList<Integer>[] aList;

	static int nV, nE;

	static int[] rMst;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());
		aList = new ArrayList[nV];

		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		visited = new boolean[nV];
		processed = new boolean[nV];
		min = new int[nV];
		Arrays.fill(min, -1);
		for (int i = 0; i < nV; i++) {
			if (!visited[i]) {
				dfs(i, -1);
			}
		}

		rMst = new int[nV];
		for (int l = 0, r = 0; l < nV; l++) {
			while (r + 1 < nV && min[r + 1] < l) {
				r++;
			}
			rMst[l] = r;
		}

		long[] mPSum = new long[nV];
		mPSum[0] = rMst[0] + 1;
		for (int i = 1; i < nV; i++) {
			mPSum[i] = mPSum[i - 1] + rMst[i] - i + 1;
		}

		int nQ = Integer.parseInt(reader.readLine());

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int qL = Integer.parseInt(inputData.nextToken()) - 1;
			int qR = Integer.parseInt(inputData.nextToken()) - 1;
			int sep = binSearch(qR);
			long sum = 0;
			if (qL < sep) {
				sum += mPSum[Math.min(sep - 1, qR)] - (qL > 0 ? mPSum[qL - 1] : 0);
			}
			if (sep <= qR) {
				long n = qR - Math.max(sep, qL) + 1;
				sum += n * (n + 1) / 2;
			}
			printer.println(sum);
		}
		printer.close();
	}

	// finds first greater
	static int binSearch(int r) {
		int low = 0;
		int high = nV;

		while (low != high) {
			int mid = (low + high) / 2;
			if (rMst[mid] <= r) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return low;
	}

	static boolean[] visited;
	static boolean[] processed;

	static ArrayList<Integer> stack = new ArrayList<>();

	static int[] min;

	static void dfs(int cV, int pV) {
		visited[cV] = true;
		stack.add(cV);

		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}
			if (!visited[aV]) {
				dfs(aV, cV);
			} else if (!processed[aV]) {
				assert (!processed[cV]);
				int cMin = Integer.MAX_VALUE;
				int cMax = 0;

				for (int i = stack.size() - 1; i >= 0; i--) {
					processed[stack.get(i)] = true;
					cMin = Math.min(cMin, stack.get(i));
					cMax = Math.max(cMax, stack.get(i));
					if (stack.get(i) == aV) {
						break;
					}
				}
				min[cMax] = cMin;
			}
		}
		stack.remove(stack.size() - 1);
	}
}
