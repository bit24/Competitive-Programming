import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CowWardrobe {

	static int N;
	static int MAXB;
	static int SREQ;

	public static void main(String[] args) throws IOException {
		new CowWardrobe().main();
	}

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		MAXB = Integer.parseInt(inputData.nextToken());
		SREQ = Integer.parseInt(inputData.nextToken());

		Item[] items = new Item[N];
		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			items[i] = new Item(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()));
		}

		Arrays.sort(items);

		inStackT = new int[SREQ + 1];
		Arrays.fill(inStackT, Integer.MAX_VALUE / 2);
		inStackT[0] = 0;

		int ans = Integer.MAX_VALUE / 2;
		int j = 0;
		for (int i = 0; i < N; i++) {
			while (j < N && query() > MAXB) {
				enqueue(items[j]);
				j++;
			}
			if (query() <= MAXB) {
				ans = Math.min(ans, items[j - 1].fancy - items[i].fancy);
			}
			deque();
		}
		if (ans >= Integer.MAX_VALUE / 2) {
			printer.println(-1);
		} else {
			printer.println(ans);
		}
		printer.close();
	}

	ArrayDeque<Item> inStack = new ArrayDeque<>();

	int[] inStackT;
	ArrayDeque<int[]> outStackT = new ArrayDeque<>();

	void enqueue(Item c) {
		inStack.push(c);

		for (int pS = SREQ; pS >= 0; pS--) {
			int nS = Math.min(SREQ, pS + c.style);
			inStackT[nS] = Math.min(inStackT[nS], inStackT[pS] + c.cost);
		}
	}

	void deque() {
		if (outStackT.isEmpty()) {
			Item curI = inStack.pop();

			int[] curT = new int[SREQ + 1];
			Arrays.fill(curT, Integer.MAX_VALUE / 2);
			curT[0] = 0;

			curT[Math.min(SREQ, curI.style)] = curI.cost;

			outStackT.push(curT);

			while (!inStack.isEmpty()) {
				curI = inStack.pop();

				curT = new int[SREQ + 1];
				Arrays.fill(curT, Integer.MAX_VALUE / 2);
				curT[0] = 0;

				int[] preT = outStackT.peek();

				for (int pS = SREQ; pS >= 0; pS--) {
					int nS = Math.min(SREQ, pS + curI.style);
					curT[nS] = Math.min(curT[nS], preT[pS] + curI.cost);
					curT[nS] = Math.min(curT[nS], preT[nS]);
				}
				outStackT.push(curT);
			}

			Arrays.fill(inStackT, Integer.MAX_VALUE / 2);
			inStackT[0] = 0;
		}
		outStackT.pop();
	}

	int query() {
		int[] table1 = inStackT;
		int[] table2 = outStackT.peek();

		if (table2 == null) {
			return table1[SREQ];
		}

		int ans = Integer.MAX_VALUE / 2;
		int minO = Integer.MAX_VALUE / 2;
		for (int s1 = 0; s1 <= SREQ; s1++) {
			minO = Math.min(minO, table2[SREQ - s1]);
			ans = Math.min(ans, table1[s1] + minO);
		}
		return ans;
	}

	class Item implements Comparable<Item> {
		int cost;
		int style;
		int fancy;

		Item(int cost, int style, int fancy) {
			this.cost = cost;
			this.style = Math.min(SREQ, style);
			this.fancy = fancy;
		}

		public int compareTo(Item o) {
			return Integer.compare(fancy, o.fancy);
		}
	}
}