import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_470C {

	public static void main(String[] args) throws IOException {
		Arrays.fill(nxt[0], -1);
		Arrays.fill(nxt[1], -1);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());
		int[] mes = new int[len];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < len; i++) {
			mes[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < len; i++) {
			int cKey = Integer.parseInt(inputData.nextToken());
			add(cKey);
		}

		for (int i = 0; i < len; i++) {
			int res = search(mes[i]);
			printer.print(res + " ");
		}
		printer.println();
		printer.close();
	}

	static int[] cnt = new int[40 * 300_000];
	static int[][] nxt = new int[2][40 * 300_000];
	static int nN = 1;

	static int search(int key) {
		int nI = 0;
		cnt[0]--;
		int res = 0;
		for (int i = 29; i >= 0; i--) {
			int kV = (key >> i) & 1;
			if (nxt[kV][nI] != -1 && cnt[nxt[kV][nI]] != 0) {
				nI = nxt[kV][nI];
			} else {
				nI = nxt[kV ^ 1][nI];
				res += 1 << i;
			}
			cnt[nI]--;
		}
		return res;
	}

	static void add(int key) {
		int nI = 0;
		cnt[0]++;
		for (int i = 29; i >= 0; i--) {
			int kV = (key >> i) & 1;
			if (nxt[kV][nI] == -1) {
				nxt[kV][nI] = nN++;
			}
			nI = nxt[kV][nI];
			cnt[nI]++;
		}
	}
}
