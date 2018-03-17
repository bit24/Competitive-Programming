
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_315E {

	public static void main(String[] args) throws IOException {
		new Div1_315E().execute();
	}

	int len;
	int[] vals = new int[100_002];

	int[] nGap = new int[100_002];

	int nA;
	int[] avail = new int[100_001];

	int nAND;
	int[] aND = new int[100_002];

	int[] map = new int[100_001];

	boolean[] used = new boolean[100_001];

	// least end
	int[] lEnd = new int[100_003];
	int[] lEPI = new int[100_003];

	int[] mLen = new int[100_002];
	int[] mLPI = new int[100_002];

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		len = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= len; i++) {
			vals[i] = Integer.parseInt(inputData.nextToken());
		}

		nGap[100_000] = 100_001;
		for (int i = len - 1; i >= 0; i--) {
			if (vals[i + 1] == -1) {
				nGap[i] = i + 1;
			} else {
				nGap[i] = nGap[i + 1];
			}
		}

		nA = Integer.parseInt(reader.readLine());
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nA; i++) {
			avail[i] = Integer.parseInt(inputData.nextToken());
		}
		Arrays.sort(avail, 1, nA + 1);

		Arrays.fill(aND, Integer.MAX_VALUE);
		aND[0] = 0;

		aND[1] = avail[1];
		nAND = 1;
		map[1] = 1;
		for (int i = 2; i <= nA; i++) {
			if (avail[i] != avail[i - 1]) {
				aND[++nAND] = avail[i];
				map[nAND] = i;
			}
		}

		vals[0] = 0;
		vals[len + 1] = Integer.MAX_VALUE - 5;

		Arrays.fill(lEnd, Integer.MAX_VALUE);
		lEnd[0] = 0;
		lEnd[1] = 0;

		mLen[0] = 1;

		for (int i = 1; i <= len + 1; i++) {
			if (vals[i] != -1) {
				int cLesserI = fLesser(lEnd, 100_002, vals[i]);
				mLen[i] = cLesserI + 1;
				mLPI[i] = lEPI[cLesserI];

				if (vals[i] < lEnd[cLesserI + 1]) {
					lEnd[cLesserI + 1] = vals[i];
					lEPI[cLesserI + 1] = i;
				}
			} else {
				int cLesserI = 100_002;
				for (int eI = nAND; eI >= 1; eI--) {
					while (lEnd[cLesserI] >= aND[eI]) {
						cLesserI--;
					}
					ass(lEnd[cLesserI] < aND[eI] && lEnd[cLesserI + 1] >= aND[eI]);
					if (aND[eI] < lEnd[cLesserI + 1]) {
						lEnd[cLesserI + 1] = aND[eI];
						lEPI[cLesserI + 1] = -1;
					}
				}
			}
		}

		for (int i = len + 1; i > 0;) {
			if (mLPI[i] != -1) {
				i = mLPI[i];
			} else {
				int gCnt = 0;
				pLoop:
				for (int pI = i - 1; pI >= 0; pI--) {
					if (vals[pI] != -1) {
						if (vals[pI] < vals[i]) {
							int cLesserI = fLesser(aND, nAND, vals[i]);
							int cGreaterI = fGreater(aND, nAND + 1, vals[pI]);
							int nB = cLesserI - cGreaterI + 1;
							if (mLen[pI] + Math.min(gCnt, nB) + 1 == mLen[i]) {

								int cG = nGap[pI];
								for (int cGI = 0; cGI < Math.min(gCnt, nB); cGI++) {
									vals[cG] = aND[cGreaterI + cGI];
									used[map[cGreaterI + cGI]] = true;
									cG = nGap[cG];
								}

								i = pI;
								break pLoop;
							}
						}
					} else {
						gCnt++;
					}
				}
			}
		}

		int nU = 1;
		for (int i = 1; i <= len; i++) {
			if (vals[i] == -1) {
				while (used[nU]) {
					nU++;
				}
				vals[i] = avail[nU++];
			}
		}

		for (int i = 1; i <= len; i++) {
			printer.print(vals[i] + " ");
		}
		printer.println();
		printer.close();
	}

	int fLesser(int[] array, int high, int inp) {
		int low = -1;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (array[mid] < inp) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		ass(array[low] < inp && array[low + 1] >= inp);
		return low;
	}

	int fGreater(int[] array, int high, int inp) {
		int low = 0;
		while (low != high) {
			int mid = (low + high) >> 1;
			if (array[mid] > inp) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		ass(array[low] > inp && array[low - 1] <= inp);
		return low;
	}

	void ass(boolean inp) { // because assert() isn't always enabled
		if (!inp) {
			throw new RuntimeException();
		}
	}
}
