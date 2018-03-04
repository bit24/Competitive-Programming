import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Div1_468B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String str = reader.readLine();
		int len = str.length();

		ArrayList<Integer>[] occur = new ArrayList[26];
		for (int i = 0; i < 26; i++) {
			occur[i] = new ArrayList<>();
		}

		int[] array = new int[len];
		for (int i = 0; i < len; i++) {
			array[i] = str.charAt(i) - 'a';
			occur[array[i]].add(i);
		}

		long sum = 0;

		int[] seen = new int[26];
		lLoop:
		for (int let = 0; let < 26; let++) {
			int max = 0;
			for (int offset = 1; offset < len; offset++) {
				Arrays.fill(seen, 0);
				for (int cO : occur[let]) {
					int cLet = array[(cO + offset) % len];
					seen[cLet]++;
				}
				boolean onlyOne = true;
				for (int i = 0; i < 26; i++) {
					if (seen[i] > 1) {
						onlyOne = false;
					}
				}
				if (onlyOne) {
					sum -= max;
					sum += occur[let].size();
					continue lLoop;
				}

				int oneCnt = 0;
				for (int i = 0; i < 26; i++) {
					if (seen[i] == 1) {
						oneCnt++;
					}
				}

				if(oneCnt > max) {
					sum -= max;
					sum += oneCnt;
					max = oneCnt;
				}
			}
		}

		printer.println((double) sum / len);
		printer.close();
	}
}