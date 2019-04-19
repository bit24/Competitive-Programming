import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class VK15_FC {

	static int nV;

	static long[] w;

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] cList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());

		w = new long[nV];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			w[i] = Integer.parseInt(inputData.nextToken());
		}

		aList = new ArrayList[nV];
		cList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			cList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
			cList[a].add(c);
			cList[b].add(c);
		}

		vCount = new int[nV];
		removed = new boolean[nV];

		decompose(0);

		double sumGrad = 0;
		double lCost = fCost(last, -1, 0);

		int altV = -1;
		double aCost = 0;

		for (int aV : aList[last]) {
			sumGrad += fGrad(aV, last, 1);
		}

		for (int aV : aList[last]) {
			if (2 * fGrad(aV, last, 1) >= sumGrad) {
				altV = aV;
				aCost = fCost(aV, -1, 0);
			}
		}

		if (altV == -1 || lCost <= aCost) {
			printer.println(last + 1 + " " + lCost);
		} else {
			printer.println(altV + 1 + " " + aCost);
		}

		printer.close();
	}

	static double fCost(int cV, int pV, int len) {
		double sum = w[cV] * Math.pow(len, 1.5);

		for (int aI = 0; aI < aList[cV].size(); aI++) {
			int aV = aList[cV].get(aI);
			int cC = cList[cV].get(aI);
			if (aV != pV) {
				sum += fCost(aV, cV, len + cC);
			}
		}
		return sum;
	}

	static int[] vCount;

	static int gSize = 0;

	static boolean[] removed;

	// calculates the sum of derivatives/(3/2)
	static double fGrad(int cV, int pV, int len) {
		double sum = w[cV] * Math.sqrt(len);

		for (int aI = 0; aI < aList[cV].size(); aI++) {
			int aV = aList[cV].get(aI);
			int cC = cList[cV].get(aI);
			if (aV != pV) {
				sum += fGrad(aV, cV, len + cC);
			}
		}
		return sum;
	}

	static int last = -1;

	// whenever something has no par use -1

	// prerequisite: initialize vCount and par
	// the root can be any arbitrary vertex within that "group"
	static void decompose(int root) {
		fCount(root, -1);
		gSize = vCount[root];
		int centroid = fCentroid(root, -1);

		removed[centroid] = true;

		double sumGrad = 0;

		for (int aV : aList[centroid]) {
			sumGrad += fGrad(aV, centroid, 1);
		}

		for (int aV : aList[centroid]) {
			if (!removed[aV]) { // if removed it has been considered before and either is case second last or just worse
				if (2 * fGrad(aV, centroid, 1) >= sumGrad) {
					decompose(aV);
				}
			}
		}
		if (last == -1) {
			last = centroid;
		}
	}

	// prerequisite: fDegree has to be run on the "group" and gSize has to be set to vCount of root
	// returns centroid
	static int fCentroid(int cV, int pV) {
		dLoop:
		while (true) {
			for (int aV : aList[cV]) {
				if (!removed[aV] && aV != pV && vCount[aV] > gSize / 2) {
					pV = cV;
					cV = aV;
					continue dLoop;
				}
			}
			return cV;
		}
	}

	static void fCount(int cV, int pV) {
		vCount[cV] = 1;
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fCount(aV, cV);
				vCount[cV] += vCount[aV];
			}
		}
	}

}
