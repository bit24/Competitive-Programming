import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Necklace {

	public static void main(String[] args) throws IOException {
		new Necklace().solve();
	}

	int[] text;
	int[] pat;
	int[][] prog;

	void solve() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("necklace.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("necklace.out")));
		String tString = reader.readLine();
		String pString = reader.readLine();
		reader.close();

		text = new int[tString.length()];
		for (int i = 0; i < tString.length(); i++) {
			text[i] = tString.charAt(i) - 'a';
		}

		pat = new int[pString.length()];
		for (int i = 0; i < pString.length(); i++) {
			pat[i] = pString.charAt(i) - 'a';
		}

		prog = new int[pat.length + 1][26];
		for (int i = 1; i < pat.length; i++) {
			int pInd = prog[i - 1][pat[i - 1]];
			prog[i - 1][pat[i - 1]] = i;
			for (int j = 0; j < 26; j++) {
				prog[i][j] = prog[pInd][j];
			}
		}
		prog[pat.length - 1][pat[pat.length - 1]] = pat.length;

		int[] minCost = new int[text.length];
		Arrays.fill(minCost, Integer.MAX_VALUE / 4);
		minCost[0] = 0;
		for (int currentLength = 0; currentLength < text.length; currentLength++) {
			int[] newCost = new int[minCost.length];
			for (int i = 0; i < newCost.length; i++) {
				newCost[i] = minCost[i] + 1;
			}
			for (int patternProgression = 0; patternProgression < pat.length; patternProgression++) {
				int newProgression = prog[patternProgression][text[currentLength]];
				if (newProgression < pat.length) {
					newCost[newProgression] = Math.min(newCost[newProgression], minCost[patternProgression]);
				}
			}
			minCost = newCost;
		}

		int ans = Integer.MAX_VALUE;
		for (int i : minCost) {
			ans = Math.min(ans, i);
		}
		printer.println(ans);
		printer.close();
	}

}