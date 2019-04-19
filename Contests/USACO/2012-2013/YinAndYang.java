import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class YinAndYang {

	static int numV;

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] eVal;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("yinyang.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("yinyang.out")));

		numV = offset = Integer.parseInt(reader.readLine());
		aList = new ArrayList[numV];
		eVal = new ArrayList[numV];
		for (int i = 0; i < numV; i++) {
			aList[i] = new ArrayList<Integer>();
			eVal[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < numV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = inputData.nextToken().charAt(0) == '1' ? 1 : -1;
			aList[a].add(b);
			eVal[a].add(c);

			aList[b].add(a);
			eVal[b].add(c);
		}
		reader.close();

		vCount = new int[numV];
		blocked = new boolean[numV];

		wrCount = new int[numV * 2];
		nrCount = new int[numV * 2];

		ENC = new int[numV * 2];

		decompOp(0);
		printer.println(ans);
		printer.close();
	}

	static int gSize;
	static int[] vCount;
	static boolean[] blocked;

	static int f_vCount(int cV, int p) {
		int sum = 1;
		for (int adj : aList[cV]) {
			if (adj != p && !blocked[adj]) {
				sum += f_vCount(adj, cV);
			}
		}
		return vCount[cV] = sum;
	}

	static int fCentroid(int cV, int p) {
		dLoop:
		while (true) {
			for (int adj : aList[cV]) {
				if (adj != p && !blocked[adj] && vCount[adj] > gSize / 2) {
					p = cV;
					cV = adj;
					continue dLoop;
				}
			}
			return cV;
		}
	}

	// number of vertices with a possible rest stop (rest stop at centroid is valid)
	static int[] wrCount;

	// number of vertices without a possible rest stop (vertex at centroid is invalid)
	static int[] nrCount;

	static int[] ENC;

	static int offset;

	static void modStats(int cV, int p, int cD, int delta) {
		if (ENC[cD + offset] > 0) {
			wrCount[cD + offset] += delta;
		} else {
			nrCount[cD + offset] += delta;
		}

		ENC[cD + offset]++;
		for (int i = 0; i < aList[cV].size(); i++) {
			int adj = aList[cV].get(i);
			if (adj != p && !blocked[adj]) {
				modStats(adj, cV, cD + eVal[cV].get(i), delta);
			}
		}
		ENC[cD + offset]--;
	}

	static long ans = 0;

	static void countEnd(int cV, int p, int cD, boolean split) {
		if (split && cD == 0) {
			ans++;
		}
		if (cD == 0) {
			split = true;
		}
		for (int i = 0; i < aList[cV].size(); i++) {
			int adj = aList[cV].get(i);
			if (adj != p && !blocked[adj]) {
				countEnd(adj, cV, cD + eVal[cV].get(i), split);
			}
		}
	}

	static void updAns(int cV, int p, int cD) {

		ans += wrCount[-cD + offset];

		if (ENC[cD + offset] > 0) {
			ans += nrCount[-cD + offset];
		}

		ENC[cD + offset]++;
		for (int i = 0; i < aList[cV].size(); i++) {
			int adj = aList[cV].get(i);
			if (adj != p && !blocked[adj]) {
				updAns(adj, cV, cD + eVal[cV].get(i));
			}
		}
		ENC[cD + offset]--;
	}

	static void decompOp(int member) {
		gSize = f_vCount(member, -1);
		int centroid = fCentroid(member, -1);

		for (int i = 0; i < aList[centroid].size(); i++) {
			int adj = aList[centroid].get(i);
			if (!blocked[adj]) {
				countEnd(adj, centroid, eVal[centroid].get(i), false);
			}
		}

		modStats(centroid, -1, 0, 1);

		nrCount[offset]--;

		ENC[offset]++;
		for (int i = 0; i < aList[centroid].size(); i++) {
			int adj = aList[centroid].get(i);
			if (!blocked[adj]) {
				modStats(adj, centroid, eVal[centroid].get(i), -1);
				updAns(adj, centroid, eVal[centroid].get(i));
			}
		}
		ENC[offset]--;

		blocked[centroid] = true;
		for (int adj : aList[centroid]) {
			if (!blocked[adj]) {
				decompOp(adj);
			}
		}
	}
}