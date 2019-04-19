import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JacksCandyShop {

	static long sum;

	static ArrayList<Integer>[] chldn;

	static int[] req;

	static PriorityQueue<Integer>[] lists;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("candy.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("candy.out")));
		int nT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int nV = Integer.parseInt(inputData.nextToken());
			int nR = Integer.parseInt(inputData.nextToken());
			long A = Integer.parseInt(inputData.nextToken());
			long B = Integer.parseInt(inputData.nextToken());

			chldn = new ArrayList[nV];
			for (int i = 0; i < nV; i++) {
				chldn[i] = new ArrayList<>();
			}

			for (int i = 1; i < nV; i++) {
				int p = Integer.parseInt(reader.readLine());
				chldn[p].add(i);
			}

			req = new int[nV];
			for (int i = 0; i < nR; i++) {
				int cR = (int) ((A * i + B) % nV);
				req[cR]++;
			}

			sum = 0;
			lists = new PriorityQueue[nV];

			dfs(0);
			printer.println("Case #" + cT + ": " + sum);
		}
		reader.close();
		printer.close();
	}

	static void dfs(int cV) {
		if (chldn[cV].size() == 0) {
			lists[cV] = new PriorityQueue<>(Collections.reverseOrder());
			if (req[cV] >= 1) {
				sum += cV;
			} else {
				lists[cV].add(cV);
			}
			return;
		}

		int mC = -1;
		int mCS = -1;
		for (int aV : chldn[cV]) {
			dfs(aV);
			if (lists[aV].size() > mCS) {
				mCS = lists[aV].size();
				mC = aV;
			}
		}

		PriorityQueue<Integer> pList = lists[mC];

		for (int aV : chldn[cV]) {
			if (aV != mC) {
				pList.addAll(lists[aV]);
			}
		}
		pList.add(cV);

		while (req[cV] > 0 && !pList.isEmpty()) {
			sum += pList.remove();
			req[cV]--;
		}
		lists[cV] = pList;
	}
}
