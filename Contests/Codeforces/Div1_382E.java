import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_382E {

	static int N;
	static int K;
	static int UK;

	static int[][] order;

	static int[] cnt;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());
		UK = N - K;
		order = new int[N][N];

		cnt = new int[N];
		inputData = new StringTokenizer(reader.readLine());

		int sK = 0;
		for (int i = 0; i < K; i++) {
			sK += cnt[i] = Integer.parseInt(inputData.nextToken()) - (N - 1);
		}

		int low = cnt[K - 1];

		if (UK * low < -sK) {
			printer.println("no");
			printer.close();
			return;
		}

		ArrayList<Integer> excess = new ArrayList<Integer>();
		PriorityQueue<Integer> lack = new PriorityQueue<Integer>(ASC_CNT);
		ArrayDeque<Integer> misc = new ArrayDeque<Integer>();

		int i;
		for (i = 0; i < K && cnt[i] >= 0; i++) {
			excess.add(i);
		}

		for (; i < K; i++) {
			lack.add(i);
		}

		for (i = K; i < N; i++) {
			misc.add(i);
		}


		while (true) {
			int g = -1;
			for (i = 0; i < excess.size(); i++) {
				if (g == -1 || cnt[excess.get(i)] > cnt[excess.get(g)]) {
					g = i;
				}
			}

			if (g == -1) {
				break;
			}
			int cE = excess.remove(g);
			if (cnt[cE] == 0) {
				break;
			}

			int cntM = 0;
			while (!misc.isEmpty() && cnt[cE] > 0 & cntM++ < misc.size()) {
				int cM = misc.remove();
				order[cE][cM] = 1;
				order[cM][cE] = -1;
				cnt[cE]--;
				cnt[cM]++;
				misc.add(cM);
			}

			ArrayList<Integer> used = new ArrayList<Integer>();
			while (cnt[cE] > 0 && !lack.isEmpty()) {
				int nL = lack.remove();
				used.add(nL);
				cnt[cE]--;
			}

			ArrayList<Integer> aELater = new ArrayList<>();

			for (int cL : used) {
				order[cE][cL] = 1;
				order[cL][cE] = -1;
				cnt[cL]++;
				if (cnt[cL] < 0) {
					lack.add(cL);
				} else {
					aELater.add(cL);
				}
			}

			if (cnt[cE] > excess.size()) {
				printer.println("no");
				printer.close();
				return;
			}

			// funnel to other excess
			Collections.sort(excess, ASC_CNT);

			int fCnt = 0;
			while (cnt[cE] > 0) {
				int oE = excess.get(fCnt);
				fCnt++;
				cnt[cE]--;
				cnt[oE]++;
				order[cE][oE] = 1;
				order[oE][cE] = -1;
			}

			excess.addAll(aELater);
		}

		ArrayList<Integer> lackA = new ArrayList<>(lack);

		while (true) {
			int l = -1;
			for (i = 0; i < lackA.size(); i++) {
				if (l == -1 || cnt[lackA.get(i)] < cnt[lackA.get(l)]) {
					l = i;
				}
			}

			if (l == -1) {
				break;
			}
			int cL = lackA.remove(l);
			if (cnt[cL] == 0) {
				break;
			}

			while (!misc.isEmpty() && cnt[cL] < 0) {
				int nM = misc.removeLast(); // end is greatest
				order[cL][nM] = -1;
				order[nM][cL] = 1;
				cnt[cL]++;
				cnt[nM]--;
				misc.addFirst(nM);
			}

			if (-cnt[cL] > lackA.size()) {
				printer.println("no");
				printer.close();
				return;
			}

			// funnel to other lack
			Collections.sort(lackA, DSC_CNT);

			int fCnt = 0;
			while (cnt[cL] > 0) {
				int oE = lackA.get(fCnt);
				fCnt++;
				cnt[cL]++;
				cnt[oE]--;
				order[cL][oE] = -1;
				order[oE][cL] = 1;
			}
		}

		for (i = K; i < N; i++) {
			if (cnt[i] < low) {
				printer.println("no");
				printer.close();
				return;
			}
		}

		printer.println("yes");
		for (i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j) {
					printer.print('X');
				} else {
					if (order[i][j] == 1) {
						printer.print('W');
					} else if (order[i][j] == 0) {
						printer.print('D');
					} else {
						printer.print('L');
					}
				}
			}
			printer.println();
		}
		printer.close();
	}

	static Comparator<Integer> ASC_CNT = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			if (cnt[o1] == cnt[o2]) {
				return Integer.compare(o1, o2);
			}
			return Integer.compare(cnt[o1], cnt[o2]);
		}
	};

	static Comparator<Integer> DSC_CNT = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			if (cnt[o1] == cnt[o2]) {
				return Integer.compare(o1, o2);
			}
			return -Integer.compare(cnt[o1], cnt[o2]);
		}
	};

}
