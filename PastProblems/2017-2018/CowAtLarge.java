import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

// centroid decomposition solution based off of krijgertje

public class CowAtLarge {

	static int nV;

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("atlarge.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("atlarge.out")));
		nV = Integer.parseInt(reader.readLine());
		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}
		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}
		reader.close();

		ans = new int[nV];
		leafD = new int[nV];
		plCnt = new int[nV];

		int[] queue = new int[nV];
		int head = 0;
		int tail = 0;

		Arrays.fill(leafD, -1);

		for (int i = 0; i < nV; i++) {
			if (aList[i].size() == 1) {
				leafD[i] = 0;
				queue[tail++] = i;
			}
		}

		while (head < tail) {
			int cV = queue[head++];
			for (int aV : aList[cV]) {
				if (leafD[aV] == -1) {
					leafD[aV] = leafD[cV] + 1;
					queue[tail++] = aV;
				}
			}
		}
		vCnt = new int[nV];
		removed = new boolean[nV];

		decompose(0);

		for (int i = 0; i < nV; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static int[] ans;

	static int[] leafD;

	static int[] plCnt;

	static void mPlCnt(int cV, int pV, int cDep, int res, int delta) {
		int nRes = Math.max(0, Math.min(res, leafD[cV] - cDep));
		for (int cD = nRes; cD < res; cD++) {
			plCnt[cD] += delta;
		}
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				mPlCnt(aV, cV, cDep + 1, nRes, delta);
			}
		}
	}

	static void fPl(int cV, int pV, int cDep, int res) {
		int nRes = Math.min(res, leafD[cV] + cDep);
		if (cDep < nRes) {
			ans[cV] += plCnt[cDep];
		}

		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fPl(aV, cV, cDep + 1, nRes);
			}
		}
	}

	static int[] vCnt;

	static int gSize = 0;

	static boolean[] removed;

	static void decompose(int root) {
		fCount(root, -1);
		gSize = vCnt[root];
		int centroid = fCentroid(root, -1);

		removed[centroid] = true;

		mPlCnt(centroid, -1, 0, gSize, 1);

		ans[centroid] += plCnt[0];
		for (int aV : aList[centroid]) {
			if (!removed[aV]) {
				mPlCnt(aV, centroid, 1, leafD[centroid], -1);
				fPl(aV, centroid, 1, Integer.MAX_VALUE);
				mPlCnt(aV, centroid, 1, leafD[centroid], 1);
			}
		}

		mPlCnt(centroid, -1, 0, gSize, -1);

		for (int aV : aList[centroid]) {
			if (!removed[aV]) {
				decompose(aV);
			}
		}
	}

	static int fCentroid(int cV, int pV) {
		dLoop:
		while (true) {
			for (int aV : aList[cV]) {
				if (!removed[aV] && aV != pV && vCnt[aV] > gSize / 2) {
					pV = cV;
					cV = aV;
					continue dLoop;
				}
			}
			return cV;
		}
	}

	static void fCount(int cV, int pV) {
		vCnt[cV] = 1;
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fCount(aV, cV);
				vCnt[cV] += vCnt[aV];
			}
		}
	}
}