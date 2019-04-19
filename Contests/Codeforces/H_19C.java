import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class H_19C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		int[] pCnt = new int[500_001];
		int[] nCnt = new int[500_001];

		for (int i = 0; i < N; i++) {
			String cur = reader.readLine();
			int sum = 0;

			boolean neg = false;
			for (int j = 0; j < cur.length(); j++) {
				if (cur.charAt(j) == '(') {
					sum++;
				} else {
					sum--;
				}
				if (sum < 0) {
					neg = true;
				}
			}
			if (!neg) {
				pCnt[sum]++;
			}

			sum = 0;
			neg = false;
			for (int j = cur.length() - 1; j >= 0; j--) {
				if (cur.charAt(j) == '(') {
					sum--;
				} else {
					sum++;
				}
				if (sum < 0) {
					neg = true;
				}
			}
			if (!neg) {
				nCnt[sum]++;
			}
		}
		int ans = 0;
		ans += pCnt[0] / 2;
		for (int i = 1; i <= 500_000; i++) {
			ans += Math.min(pCnt[i], nCnt[i]);
		}
		printer.println(ans);
		printer.close();
	}
}
