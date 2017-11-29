import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class RoboticCowHerd {

	public static void main(String[] args) throws IOException {
		long sTime = System.currentTimeMillis();
		new RoboticCowHerd().execute();
		System.out.println(System.currentTimeMillis() - sTime);
	}

	ArrayList<int[]> upgd;

	long nR;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("roboherd.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("roboherd.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nL = Integer.parseInt(inputData.nextToken());
		nR = Integer.parseInt(inputData.nextToken());

		long bCost = 0;
		long high = 0;
		upgd = new ArrayList<int[]>();
		for (int i = 0; i < nL; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int cNC = Integer.parseInt(inputData.nextToken());
			int[] cCost = new int[cNC];
			for (int j = 0; j < cNC; j++) {
				cCost[j] = Integer.parseInt(inputData.nextToken());
			}
			Arrays.sort(cCost);

			bCost += cCost[0];
			high += cCost[cCost.length - 1];
			if (cCost.length > 1) {
				int[] cUpgd = new int[cCost.length - 1];
				for (int j = 1; j < cCost.length; j++) {
					cUpgd[j - 1] = cCost[j] - cCost[0];
				}
				upgd.add(cUpgd);
			}
		}
		Collections.sort(upgd, CA);
		nL = upgd.size();

		reader.close();

		long low = 0;
		while (low != high) {
			long mid = (low + high) / 2;
			cCnt = 0;
			cnt(nL - 1, mid);
			if (cCnt >= nR) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		cCnt = 0;
		fExt(nL - 1, low - 1);
		printer.println((bCost + low) * nR - ext);
		printer.close();
	}

	int cCnt;
	long ext;

	void cnt(int cL, long rem) {
		if (cCnt >= nR) {
			return;
		}
		if (cL == -1) {
			cCnt++;
			return;
		}
		cnt(searchPos(rem, cL - 1), rem);
		int[] cUpgd = upgd.get(cL);
		for (int i = 0; i < cUpgd.length; i++) {
			if (cUpgd[i] <= rem) {
				cnt(searchPos(rem - cUpgd[i], cL - 1), rem - cUpgd[i]);
			} else {
				break;
			}
		}
	}

	void fExt(int cL, long rem) {
		if (cL == -1) {
			ext += rem + 1;
			return;
		}
		fExt(searchPos(rem, cL - 1), rem);
		int[] cUpgd = upgd.get(cL);
		for (int i = 0; i < cUpgd.length; i++) {
			if (cUpgd[i] <= rem) {
				fExt(searchPos(rem - cUpgd[i], cL - 1), rem - cUpgd[i]);
			} else {
				break;
			}
		}
	}

	int searchPos(long rem, int high) {
		if (high != -1 && upgd.get(high)[0] <= rem) {
			return high;
		}
		int low = -1;
		while (low != high && high != -1) {
			int mid = (low + high + 1) / 2;
			if (upgd.get(mid)[0] <= rem) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	static Comparator<int[]> CA = new Comparator<int[]>() {
		public int compare(int[] a1, int[] a2) {
			for (int i = 0; i < Math.min(a1.length, a2.length); i++) {
				if (a1[i] < a2[i]) {
					return -1;
				}
				if (a1[i] > a2[i]) {
					return 1;
				}
			}
			if (a1.length != a2.length) {
				return a1.length < a2.length ? -1 : 1;
			}
			return 0;
		}
	};

}
