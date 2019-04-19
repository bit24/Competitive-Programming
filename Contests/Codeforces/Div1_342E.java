import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_342E {

	public static void main(String[] args) throws IOException {
		new Div1_342E().execute();
	}

	static long[] sLoc = new long[100_000];
	static long[] lTime = new long[100_000];
	static long[] dTraveled = new long[100_000];
	static long[] rate = new long[100_000];

	static int[] nxt = new int[100_000];
	static int[] prev = new int[100_000];
	static boolean[] removed = new boolean[100_000];

	static long len;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nF = Integer.parseInt(inputData.nextToken());
		if (nF == 1) {
			printer.println(1);
			printer.println(1);
			printer.close();
			return;
		}

		len = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < nF; i++) {
			inputData = new StringTokenizer(reader.readLine());
			sLoc[i] = Integer.parseInt(inputData.nextToken()) - 1;
			rate[i] = Integer.parseInt(inputData.nextToken());
		}

		Integer[] indices = new Integer[nF];
		for (int i = 0; i < nF; i++) {
			indices[i] = i;
		}
		Arrays.sort(indices, compSLoc);

		for (int i = 0; i < nF - 1; i++) {
			nxt[indices[i]] = indices[i + 1];
		}
		nxt[indices[nF - 1]] = indices[0];

		for (int i = 0; i < nF; i++) {
			prev[nxt[i]] = i;
		}

		TreeSet<Inter> inters = new TreeSet<Inter>();

		for (int i = 0; i < nF; i++) {
			Inter cInter = cInter(i, nxt[i]);
			if (cInter != null) {
				inters.add(cInter);
			}
		}

		while (!inters.isEmpty()) {
			Inter cInter = inters.first();
			ass(inters.remove(cInter));
			int cInd = cInter.lInd;
			ass(nxt[cInd] == cInter.rInd);

			Inter tRInter = cInter(nxt[cInd], nxt[nxt[cInd]]);
			if (tRInter != null) {
				ass(inters.remove(tRInter));
			}

			removed[cInter.rInd] = true;
			nxt[cInd] = nxt[nxt[cInd]];
			prev[nxt[cInd]] = cInd;

			int rDelta = 1;

			while (nxt[cInd] != cInd) {
				Inter aInter = cInter(cInd, nxt[cInd]);
				if (aInter == null) {
					break;
				}
				ass(aInter.time >= cInter.time);
				if (aInter.time == cInter.time) {
					rDelta++;
					tRInter = cInter(nxt[cInd], nxt[nxt[cInd]]);
					if (tRInter != null) {
						ass(inters.remove(tRInter));
					}
					removed[nxt[cInd]] = true;
					nxt[cInd] = nxt[nxt[cInd]];
					prev[nxt[cInd]] = cInd;
				} else {
					break;
				}
			}

			if (nxt[cInd] == cInd) {
				break;
			}
			ass(prev[cInd] != cInd);

			tRInter = cInter(prev[cInd], cInd);
			if (tRInter != null) {
				ass(inters.remove(tRInter));
			}

			dTraveled[cInd] += (cInter.time - lTime[cInd]) * rate[cInd];
			rate[cInd] = Math.max(0, rate[cInd] - rDelta);
			lTime[cInd] = cInter.time;

			Inter nInter = cInter(prev[cInd], cInd);
			if (nInter != null) {
				inters.add(nInter);
			}

			nInter = cInter(cInd, nxt[cInd]);
			if (nInter != null) {
				inters.add(nInter);
			}
		}

		int nR = 0;
		for (int i = 0; i < nF; i++) {
			if (!removed[i]) {
				nR++;
			}
		}
		printer.println(nR);

		for (int i = 0; i < nF; i++) {
			if (!removed[i]) {
				printer.print(i + 1 + " ");
			}
		}
		printer.println();
		printer.close();
	}

	Comparator<Integer> compSLoc = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return Long.compare(sLoc[o1], sLoc[o2]);
		}
	};

	Inter cInter(int lInd, int rInd) {
		long sDist = sLoc[rInd] - sLoc[lInd];
		if (sDist < 0) {
			sDist += len;
		}

		long lDTraveled = dTraveled[lInd];
		long rDTraveled = dTraveled[rInd];
		long cTime;

		if (lTime[lInd] < lTime[rInd]) {
			lDTraveled += (lTime[rInd] - lTime[lInd]) * rate[lInd];
			cTime = lTime[rInd];
		} else {
			rDTraveled += (lTime[lInd] - lTime[rInd]) * rate[rInd];
			cTime = lTime[lInd];
		}

		long diffTraveled = lDTraveled - rDTraveled;
		if (diffTraveled >= sDist) {
			return new Inter(cTime, lInd, rInd);
		}

		sDist -= diffTraveled;

		long cRate = rate[lInd] - rate[rInd];

		if (lInd < rInd) {
			sDist -= rate[lInd];
			if (sDist <= 0) {
				return new Inter(cTime + 1, lInd, rInd);
			}
			if (cRate <= 0) {
				return null;
			}
			return new Inter(cTime + 1 + (sDist + cRate - 1) / cRate, lInd, rInd);
		} else {
			if (cRate <= 0) {
				return null;
			}
			return new Inter(cTime + (sDist + cRate - 1) / cRate, lInd, rInd);
		}
	}

	class Inter implements Comparable<Inter> {
		long time;

		int lInd;
		int rInd;

		public int compareTo(Inter o) {
			if (time != o.time) {
				return time < o.time ? -1 : 1;
			}
			if (lInd != o.lInd) {
				return lInd < o.lInd ? -1 : 1;
			}
			return Integer.compare(rInd, o.rInd);
		}

		Inter(long time, int lInd, int rInd) {
			this.time = time;
			this.lInd = lInd;
			this.rInd = rInd;
		}
	}

	void ass(boolean inp) {
		if (!inp) {
			throw new RuntimeException();
		}
	}
}