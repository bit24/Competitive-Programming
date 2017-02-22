import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LandscapingSolver {

	public static void main(String[] args) throws IOException {
		new LandscapingSolver().execute();
	}

	int[] type;
	int[] loc;

	long A;
	long R;
	long T;

	int numE;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("landscape.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("landscape.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numI = Integer.parseInt(inputData.nextToken());
		A = Integer.parseInt(inputData.nextToken());
		R = Integer.parseInt(inputData.nextToken());
		T = Integer.parseInt(inputData.nextToken());

		type = new int[11 * numI + 1];
		loc = new int[11 * numI + 1];
		for (int i = 1; i <= numI; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int s = Integer.parseInt(inputData.nextToken());
			int e = Integer.parseInt(inputData.nextToken());
			for (; s < e; s++) {
				type[numE + 1] = 1;
				loc[++numE] = i;
			}
			for (; s > e; s--) {
				type[numE + 1] = -1;
				loc[++numE] = i;
			}
		}
		reader.close();

		int[] height = new int[numE + 1];

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] byHeight = new ArrayList[numE * 2];
		for (int i = 0; i < numE * 2; i++) {
			byHeight[i] = new ArrayList<Integer>();
			byHeight[i].add(0);
		}

		height[1] = numE;
		byHeight[numE].add(1);

		for (int i = 2; i <= numE; i++) {
			if (type[i] == type[i - 1]) {
				height[i] = height[i - 1] + type[i];
			} else {
				height[i] = height[i - 1];
			}
			byHeight[height[i]].add(i);
		}

		long tCost = 0;
		for (ArrayList<Integer> hGroup : byHeight) {
			tCost += fBest(hGroup);
		}
		printer.println(tCost);
		printer.close();
	}

	long[] prefix_dp(int[] cLoc) {

		long[] pcCost = new long[cLoc.length];

		for (int i = 2; i < cLoc.length; i++) {
			pcCost[i] = pcCost[i - 2] + T * abs(cLoc[i] - cLoc[i - 1]);
		}

		long[] pCost = new long[cLoc.length];

		int nScan = 1;

		long pJCost = Long.MAX_VALUE / 2;

		for (int i = 2; i < cLoc.length; i += 2) {
			long minJCost = Long.MAX_VALUE / 2;
			if (i - 2 >= 1) {
				minJCost = pJCost + T * abs(cLoc[i - 1] - cLoc[i - 2]);
			}

			while (nScan < i && A + R <= T * abs(cLoc[i] - cLoc[nScan])) {
				long posCost = pCost[nScan - 1] + A + R + pcCost[i - 1] - pcCost[nScan];
				if (posCost < minJCost) {
					minJCost = posCost;
				}
				nScan += 2;
			}
			pJCost = minJCost;

			pCost[i] = Math.min(minJCost, pCost[i - 2] + T * abs(cLoc[i] - cLoc[i - 1]));
		}
		return pCost;
	}

	int abs(int a) {
		return a < 0 ? -a : a;
	}

	void reverse(int[] elements) {
		for (int i = 1; i <= elements.length / 2; i++) {
			int temp = elements[i];
			elements[i] = elements[elements.length - i];
			elements[elements.length - i] = temp;
		}
	}

	int[] toLocs(ArrayList<Integer> hGroup) {
		int[] cLoc = new int[hGroup.size()];
		for (int i = 1; i < hGroup.size(); i++) {
			cLoc[i] = loc[hGroup.get(i)];
		}
		cLoc[0] = Integer.MIN_VALUE / 8;
		return cLoc;
	}

	long fBest(ArrayList<Integer> hGroup) {
		if (hGroup.size() == 1) {
			return 0;
		}

		// actually even-sized because array is one indexed
		if ((hGroup.size() & 1) == 1) {
			return prefix_dp(toLocs(hGroup))[hGroup.size() - 1];
		} else {
			int[] cLocs = toLocs(hGroup);
			long[] pCost = prefix_dp(cLocs);
			reverse(cLocs);
			long[] sCost = prefix_dp(cLocs);

			long mCost = Long.MAX_VALUE;
			for (int alone = 1; alone < hGroup.size(); alone += 2) {
				long posCost = type[hGroup.get(alone)] == 1 ? A : R;
				posCost += pCost[alone - 1];
				posCost += sCost[hGroup.size() - alone - 1];
				if (posCost < mCost) {
					mCost = posCost;
				}
			}
			return mCost;
		}
	}

	long min(long a, long b) {
		return a < b ? a : b;
	}

}
