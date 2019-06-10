import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CowCoupons {

	class Pair implements Comparable<Pair> {
		int c;
		int i;

		Pair(int a, int b) {
			this.c = a;
			this.i = b;
		}

		public int compareTo(Pair o) {
			return Integer.compare(c, o.c);
		}
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("coupons.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("coupons.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nS = Integer.parseInt(inputData.nextToken());
		int nR = Integer.parseInt(inputData.nextToken());
		long cB = Long.parseLong(inputData.nextToken());

		int[] bC = new int[nS];
		int[] aC = new int[nS];

		Pair[] bQ = new Pair[nS];
		Pair[] aQ = new Pair[nS];

		for (int i = 0; i < nS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			bC[i] = Integer.parseInt(inputData.nextToken());
			aC[i] = Integer.parseInt(inputData.nextToken());
			bQ[i] = new Pair(bC[i], i);
			aQ[i] = new Pair(aC[i], i);
		}
		reader.close();
		Arrays.sort(bQ);
		Arrays.sort(aQ);

		PriorityQueue<Integer> tC = new PriorityQueue<Integer>();
		for (int i = 0; i < nR; i++) {
			tC.add(0);
		}

		boolean[] used = new boolean[nS];

		int bI = 0, aI = 0;

		int cnt = 0;
		long tCost = 0;
		while (true) {
			while (bI < bQ.length && used[bQ[bI].i]) {
				bI++;
			}
			while (aI < aQ.length && used[aQ[aI].i]) {
				aI++;
			}

			if (aI == nS && bI == nS) {
				break;
			}

			if (bI != nS && (aI == nS || bQ[bI].c < aQ[aI].c + tC.peek())) {
				tCost += bQ[bI].c;
				used[bQ[bI].i] = true;
				bI++;
			} else {
				tCost += aQ[aI].c + tC.peek();
				tC.remove();
				tC.add(bC[aQ[aI].i] - aC[aQ[aI].i]);
				used[aQ[aI].i] = true;
				aI++;
			}
			if (tCost > cB) {
				break;
			}
			cnt++;
		}
		printer.println(cnt);
		printer.close();
	}

	public static void main(String[] args) throws IOException {
		new CowCoupons().execute();
	}

}
