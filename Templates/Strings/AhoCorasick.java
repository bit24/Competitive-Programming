import java.util.ArrayDeque;
import java.util.Arrays;

//time complexity O(patternSize * alphabetSize + textSize + numMatches)
public class AhoCorasick {

	static int MAX = 1_000_000;
	static int[][] link = new int[26][MAX];
	static int nNode = 1;

	static int[] matI = new int[MAX];
	static int[] pMat = new int[MAX];

	static int[] fail;

	static void add(String pat, int pI) {
		int node = 0;
		for (int i = 0; i < pat.length(); i++) {
			if (link[pat.charAt(i) - 'a'][node] == -1) {
				link[pat.charAt(i) - 'a'][node] = nNode++;
			}
			node = link[pat.charAt(i) - 'a'][node];
		}
		matI[node] = pI;
	}

	static void addAll(String[] pats) {
		for(int[] a : link) {
			Arrays.fill(a, -1);
		}
		Arrays.fill(matI, -1);
		Arrays.fill(pMat, -1);
		Arrays.fill(fail, -1);

		for (int i = 0; i < pats.length; i++) {
			add(pats[i], i);
		}

		ArrayDeque<Integer> queue = new ArrayDeque<>();
		for (int cC = 0; cC < 26; cC++) {
			int nLink = link[cC][0];
			if (nLink != -1) {
				fail[nLink] = 0;
				queue.add(nLink);
			}
		}

		while (!queue.isEmpty()) {
			int cur = queue.remove();
			for (int cC = 0; cC < 26; cC++) {
				int nLink = link[cC][cur];
				if (nLink != -1) {
					queue.add(nLink);
					int suf = fail[cur];
					while (suf != -1 && link[cC][suf] == -1) {
						suf = fail[suf];
					}
					if (suf == -1) {
						fail[nLink] = 0;
					} else {
						fail[nLink] = link[cC][suf];
					}

					if (matI[fail[nLink]] != -1) {
						pMat[nLink] = fail[nLink];
					} else {
						pMat[nLink] = pMat[fail[nLink]];
					}
				}
			}
		}
	}

	static void matchAll(String text, String[] pats) {
		addAll(pats);
		int node = 0;

		for (int i = 0; i < text.length(); i++) {
			int nLink = link[text.charAt(i) - 'a'][node];
			while (nLink == -1) {
				node = fail[node];
				if (node == -1) {
					break;
				}
				nLink = link[text.charAt(i) - 'a'][node];
			}
			if (nLink == -1) {
				node = 0;
			} else {
				node = nLink;
			}

			for (int cM = matI[node] == -1 ? pMat[node] : node; cM != -1; cM = pMat[cM]) {
				// print cM with last character at index i
			}
		}
	}
}
