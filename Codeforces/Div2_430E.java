import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Div2_430E {

	static ArrayList<Integer> group1 = new ArrayList<>();
	static ArrayList<Integer> group2 = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		nV = Integer.parseInt(reader.readLine()) + 1;
		anc = new int[nV][21];

		for (int i = 0; i <= 20; i++) {
			anc[0][i] = 0;
		}

		depth = new int[nV];

		group1.add(0);

		int D = 0;

		for (int cV = 1; cV < nV; cV++) {
			int pV = Integer.parseInt(reader.readLine()) - 1;
			depth[cV] = depth[pV] + 1;
			anc[cV][0] = pV;

			for (int i = 1; i <= 20; i++) {
				anc[cV][i] = anc[anc[cV][i - 1]][i - 1];
			}

			if (!group1.isEmpty() && dist(group1.get(0), cV) > D) {
				// everything in group1 is D+1 away from cV (the new vertex)
				D++;
				for (int oV : group2) {
					if (dist(cV, oV) == D) {
						group1.add(oV);
					}
				}
				group2.clear();
				group2.add(cV);
			} else if (!group2.isEmpty() && dist(group2.get(0), cV) > D) {
				// everything in group1 is D+1 away from cV (the new vertex)
				D++;
				for (int oV : group1) {
					if (dist(cV, oV) == D) {
						group2.add(oV);
					}
				}
				group1.clear();
				group1.add(cV);
			} else if (!group1.isEmpty() && dist(group1.get(0), cV) == D) {
				group2.add(cV);
			} else if (!group2.isEmpty() && dist(group2.get(0), cV) == D) {
				group1.add(cV);
			}
			printer.println(group1.size() + group2.size());
		}
		printer.close();
	}

	static int nV;
	static ArrayList<Integer>[] chldn;

	static int root;

	static int[][] anc;
	static int[] depth;

	// good for depth of up to 1_048_576 = 2^20

	static int dist(int a, int b) {
		return depth[a] + depth[b] - 2 * depth[fLCA(a, b)];
	}

	static int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int dif = depth[b] - depth[a];

		for (int i = 0; i <= 20; i++) {
			if ((dif & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}

		if (a == b) {
			return a;
		}

		for (int i = 20; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}
}
