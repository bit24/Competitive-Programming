import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Edu_46A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nE = Integer.parseInt(reader.readLine());

		int[][] cnt = new int[][] { { 0, 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };

		for (int i = 0; i < nE; i++) {
			String nxt = reader.readLine();
			if (nxt.equals("S")) {
				cnt[0][0]++;
			}
			if (nxt.equals("M")) {
				cnt[0][1]++;
			}
			if (nxt.equals("L")) {
				cnt[0][2]++;
			}
			if (nxt.equals("XS")) {
				cnt[1][0]++;
			}
			if (nxt.equals("XL")) {
				cnt[1][1]++;
			}
			if (nxt.equals("XXS")) {
				cnt[2][0]++;
			}
			if (nxt.equals("XXL")) {
				cnt[2][1]++;
			}
			if (nxt.equals("XXXS")) {
				cnt[3][0]++;
			}
			if (nxt.equals("XXXL")) {
				cnt[3][1]++;
			}
		}

		for (int i = 0; i < nE; i++) {
			String nxt = reader.readLine();
			if (nxt.equals("S")) {
				cnt[0][0]--;
			}
			if (nxt.equals("M")) {
				cnt[0][1]--;
			}
			if (nxt.equals("L")) {
				cnt[0][2]--;
			}
			if (nxt.equals("XS")) {
				cnt[1][0]--;
			}
			if (nxt.equals("XL")) {
				cnt[1][1]--;
			}
			if (nxt.equals("XXS")) {
				cnt[2][0]--;
			}
			if (nxt.equals("XXL")) {
				cnt[2][1]--;
			}
			if (nxt.equals("XXXS")) {
				cnt[3][0]--;
			}
			if (nxt.equals("XXXL")) {
				cnt[3][1]--;
			}
		}

		int ans = 0;
		for (int i = 1; i <= 3; i++) {
			ans += Math.abs(cnt[i][0]);
		}

		int max = 0;
		for (int i = 0; i < 3; i++) {
			max = Math.max(max, Math.abs(cnt[0][i]));
		}
		ans += max;
		printer.println(ans);
		printer.close();
	}
}
