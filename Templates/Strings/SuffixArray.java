import java.util.Arrays;

class SuffixArray {

	int nI;
	int logNI;
	int[][] order;

	void build(String str) {
		int[] value = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			char cChar = str.charAt(i);
			if ('A' <= cChar && cChar <= 'Z') {
				value[i] = cChar - 'A';
			} else if ('a' <= cChar && cChar <= 'z') {
				value[i] = 26 + cChar - 'a';
			} else {
				assert (false);
			}
		}
		build(value);
	}

	void build(int[] items) {
		nI = items.length;
		logNI = 32 - Integer.numberOfLeadingZeros(nI - 1);
		order = new int[logNI + 1][nI];

		Group[] sList = new Group[nI];
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
		int ans = 0;
		for (int exp = logNI, len = 1 << logNI; exp >= 0 && i < nI && j < nI; exp--, len >>= 1) {
			if (order[exp][i] == order[exp][j]) {
				i += len;
				j += len;
				ans += len;
			}
		}
		return ans;
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
