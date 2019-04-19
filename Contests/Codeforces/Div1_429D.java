import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.SplittableRandom;
import java.util.StringTokenizer;

public class Div1_429D {

	public static void main(String[] args) throws IOException {
		new Div1_429D().execute();
	}

	SplittableRandom rng = new SplittableRandom();

	final int SQRT = 548;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nI = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());
		int[] items = new int[nI];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken());
		}
		Query[] queries = new Query[nQ];
		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			queries[i] = new Query(Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()), i);
		}

		Arrays.sort(queries);
		int[] ans = new int[nQ];

		int[] cnt = new int[nI + 1];

		int cL = 0;
		int cR = -1;

		for (Query cQ : queries) {
			while (cL < cQ.l) {
				cnt[items[cL++]]--;
			}
			while (cQ.l < cL) {
				cnt[items[--cL]]++;
			}
			while (cR < cQ.r) {
				cnt[items[++cR]]++;
			}
			while (cQ.r < cR) {
				cnt[items[cR--]]--;
			}

			int min = Integer.MAX_VALUE;
			for (int i = 0; i < 80; i++) {
				int tI = cL + rng.nextInt(cR - cL + 1);
				if (items[tI] < min && cQ.k * cnt[items[tI]] > cR - cL + 1) {
					min = items[tI];
				}
			}
			ans[cQ.i] = min == Integer.MAX_VALUE ? -1 : min;
		}
		for (int i = 0; i < nQ; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	class Query implements Comparable<Query> {
		int l;
		int r;
		int buck;
		int k;
		int i;

		Query(int l, int r, int k, int i) {
			this.l = l;
			this.r = r;
			buck = l / SQRT;
			this.k = k;
			this.i = i;
		}

		public int compareTo(Query o) {
			int res = Integer.compare(buck, o.buck);
			return res != 0 ? res : Integer.compare(r, o.r);
		}
	}
}