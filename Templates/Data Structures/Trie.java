import java.util.Arrays;

public class Trie {

	static final int ABTSIZE = 26;
	static final int MAXELEMENTS = 100_000;
	static final int MAXLENGTH = 10;

	static int[][] nxt = new int[ABTSIZE][MAXLENGTH * MAXELEMENTS];
	static int[] cnt = new int[MAXLENGTH * MAXELEMENTS];

	static int nF = 1;

	public static void main(String[] args) {
		for (int[] cArray : nxt) {
			Arrays.fill(cArray, -1);
		}
	}

	static void add(int[] key) {
		int cN = 0;
		for (int cL : key) {
			if (nxt[cL][cN] == -1) {
				nxt[cL][cN] = nF++;
			}
			cN = nxt[cL][cN];
			cnt[cN]++;
		}
	}

	static void delete(int[] key) {
		int cN = 0;
		for (int cL : key) {
			cN = nxt[cL][cN];
			cnt[cN]--;
		}
	}

	static boolean contains(int[] key) {
		int cN = 0;
		for (int cL : key) {
			if (nxt[cL][cN] == -1) {
				return false;
			}
			cN = nxt[cL][cN];
			if (cnt[cN] == 0) {
				return false;
			}
		}
		return true;
	}
}
