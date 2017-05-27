import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class DivCmb_413B {

	static int[] price;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numE = Integer.parseInt(reader.readLine());

		price = new int[numE];
		int[][] color = new int[numE][2];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			price[i] = Integer.parseInt(inputData.nextToken());
		}
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			color[i][0] = Integer.parseInt(inputData.nextToken()) - 1;
		}
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			color[i][1] = Integer.parseInt(inputData.nextToken()) - 1;
		}

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] cSrtd = new ArrayList[3];

		for (int i = 0; i < 3; i++) {
			cSrtd[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numE; i++) {
			for (int j = 0; j < 2; j++) {
				cSrtd[color[i][j]].add(i);
			}
		}

		for (int i = 0; i < 3; i++) {
			Collections.sort(cSrtd[i], byPrice);
		}

		int[] nInd = new int[3];

		int numB = Integer.parseInt(reader.readLine());

		inputData = new StringTokenizer(reader.readLine());

		boolean[] used = new boolean[numE];

		for (int i = 0; i < numB; i++) {
			int cC = Integer.parseInt(inputData.nextToken()) - 1;

			while (nInd[cC] < cSrtd[cC].size() && used[cSrtd[cC].get(nInd[cC])]) {
				nInd[cC]++;
			}
			if (nInd[cC] < cSrtd[cC].size()) {
				used[cSrtd[cC].get(nInd[cC])] = true;
				printer.print(price[cSrtd[cC].get(nInd[cC]++)] + " ");
			} else {
				printer.print("-1 ");
			}
		}
		printer.println();
		printer.close();
	}

	static Comparator<Integer> byPrice = new Comparator<Integer>() {
		public int compare(Integer int1, Integer int2) {
			return Integer.compare(price[int1], price[int2]);
		}
	};
}
