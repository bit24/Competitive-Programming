import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class StandingOutFromTheHerd {

	public static void main(String[] args) throws IOException {
		new StandingOutFromTheHerd().execute();
	}

	String[] strs;

	int[] mrgd;
	int[] id;
	int[] last;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("standingout.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("standingout.out")));

		int nS = Integer.parseInt(reader.readLine());
		int mLen = 0;
		String[] strs = new String[nS];

		//StringBuilder tStr = new StringBuilder();
		for (int i = 0; i < nS; i++) {
			strs[i] = reader.readLine();
			/*tStr.append(strs[i]);
			tStr.append("?");*/
			mLen += strs[i].length();
		}
		reader.close();
		mLen += nS;

		mrgd = new int[mLen];
		id = new int[mLen];
		last = new int[nS];
		Arrays.fill(id, -1);

		int nxt = 0;
		for (int i = 0; i < nS; i++) {
			for (int j = 0; j < strs[i].length(); j++) {
				id[nxt] = i;
				mrgd[nxt++] = strs[i].charAt(j);
			}
			last[i] = nxt - 1;
			mrgd[nxt++] = '?';
		}

		build(mrgd);

		int[] uCnt = new int[nS];

		// suffixes that start with a question mark aren't real suffixes

		int start;
		for (start = 0; start < mLen && mrgd[sList[start].ind] == '?'; start++);

		int rDif = -1;
		for (int cGI = start; cGI < mLen; cGI++) {
			int cInd = sList[cGI].ind;

			/*System.out.println(tStr.substring(cInd));
			if (cGI > 0) {
				assert (tStr.substring(sList[cGI - 1].ind).compareTo(tStr.substring(cInd)) <= 0);
			}*/

			int cID = id[cInd];
			if (cGI == start || id[sList[cGI - 1].ind] != cID) {
				for (rDif = cGI + 1; rDif < mLen && id[sList[rDif].ind] == cID; rDif++);
			}
			int dup = 0;
			if (cGI != start) {
				dup = Math.max(dup, lcp(sList[cGI - 1].ind, cInd));
			}
			if (rDif != mLen) {
				dup = Math.max(dup, lcp(sList[rDif].ind, cInd));
			}

			uCnt[cID] += last[cID] - (cInd + dup) + 1;
		}
		for (int i = 0; i < nS; i++) {
			printer.println(uCnt[i]);
		}
		printer.close();
	}

	int nI;
	int logNI;
	int[][] order;
	Group[] sList;

	void build(int[] items) {
		nI = items.length;
		logNI = 32 - Integer.numberOfLeadingZeros(nI - 1);
		order = new int[logNI + 1][nI];

		sList = new Group[nI];
		for (int i = 0; i < nI; i++) {
			sList[i] = new Group(items[i], -1, i);
		}
		Arrays.sort(sList);

		order[0][sList[0].ind] = 0;
		for (int i = 1; i < nI; i++) {
			if (sList[i - 1].a == sList[i].a) {
				order[0][sList[i].ind] = order[0][sList[i - 1].ind];
			} else {
				order[0][sList[i].ind] = i;
			}
		}

		for (int exp = 1, pL = 1; exp <= logNI; exp++, pL *= 2) {
			for (int i = 0; i < nI; i++) {
				sList[i] = new Group(order[exp - 1][i], i + pL < nI ? order[exp - 1][i + pL] : -1, i);
			}

			sList = radixSort(sList);

			order[exp][sList[0].ind] = 0;
			for (int i = 1; i < sList.length; i++) {
				if (sList[i - 1].a == sList[i].a && sList[i - 1].b == sList[i].b) {
					order[exp][sList[i].ind] = order[exp][sList[i - 1].ind];
				} else {
					order[exp][sList[i].ind] = i;
				}
			}
		}
	}

	Group[] radixSort(Group[] groups) {
		// add 1 because b can be -1
		int[] count = new int[nI + 1];
		for (Group cG : groups) {
			count[cG.b + 1]++;
		}
		int sum = 0;
		for (int i = 0; i < nI + 1; i++) {
			int temp = count[i];
			count[i] = sum;
			sum += temp;
		}

		Group[] nItems = new Group[nI];
		for (Group cG : groups) {
			nItems[count[cG.b + 1]++] = cG;
		}
		Group[] tempA = groups;
		groups = nItems;
		nItems = tempA;

		count = new int[nI];
		for (Group cG : groups) {
			count[cG.a]++;
		}
		sum = 0;
		for (int i = 0; i < nI; i++) {
			int temp = count[i];
			count[i] = sum;
			sum += temp;
		}

		nItems = new Group[nI];
		for (Group cG : groups) {
			nItems[count[cG.a]++] = cG;
		}
		return nItems;
	}

	int lcp(int i, int j) {
		int rstr = Math.min(last[id[i]] - i + 1, last[id[j]] - j + 1);
		int ans = 0;
		for (int exp = logNI, len = 1 << logNI; exp >= 0 && i < nI && j < nI; exp--, len >>= 1) {
			if (order[exp][i] == order[exp][j]) {
				i += len;
				j += len;
				ans += len;
			}
		}
		return Math.min(ans, rstr);
	}

	// note: "a" < "ab"

	class Group implements Comparable<Group> {
		int a;
		int b;
		int ind;

		Group(int a, int b, int ind) {
			this.a = a;
			this.b = b;
			this.ind = ind;
		}

		public int compareTo(Group o) {
			return (a < o.a) ? -1 : (a == o.a) ? ((b < o.b) ? -1 : (b == o.b) ? 0 : 1) : 1;
		}
	}
}
