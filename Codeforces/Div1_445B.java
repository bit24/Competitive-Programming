import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Div1_445B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nS = Integer.parseInt(reader.readLine());
		String[] strs = new String[nS];
		for (int i = 0; i < nS; i++) {
			strs[i] = reader.readLine();
		}

		int[] nx = new int[26];
		Arrays.fill(nx, -1);

		boolean[] used = new boolean[26];
		for (int i = 0; i < nS; i++) {
			String cStr = strs[i];
			used[cStr.charAt(0) - 'a'] = true;
			for (int j = 1; j < cStr.length(); j++) {
				int bef = cStr.charAt(j - 1) - 'a';
				int cur = cStr.charAt(j) - 'a';

				if (nx[bef] != -1 && nx[bef] != cur) {
					printer.println("NO");
					printer.close();
					return;
				}
				nx[bef] = cur;

				used[cur] = true;
			}
		}

		int[] deg = new int[26];
		for (int i = 0; i < 26; i++) {
			if (nx[i] != -1) {
				if (++deg[nx[i]] > 1) {
					printer.println("NO");
					printer.close();
					return;
				}
			}
		}

		ArrayList<String> chains = new ArrayList<String>();
		for (int i = 0; i < 26; i++) {
			if (used[i] && deg[i] == 0) {
				StringBuilder cChain = new StringBuilder();
				for (int j = i; j != -1; j = nx[j]) {
					cChain.append((char) ('a' + j));
				}
				chains.add(cChain.toString());
			}
		}
		
		Collections.sort(chains);

		StringBuilder ans = new StringBuilder();
		for (String cStr : chains) {
			ans.append(cStr);
		}

		int nU = 0;
		for (boolean cB : used) {
			if (cB) {
				nU++;
			}
		}

		if (ans.length() != nU) {
			printer.println("NO");
			printer.close();
			return;
		}
		printer.println(ans);
		printer.close();
	}

}
