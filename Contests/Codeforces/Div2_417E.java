import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_417E {

	static int[] aCnt;
	static int[][] tACnt;
	static int[] tCnt;
	static int[] tXor;

	static ArrayList<Integer>[] chldn;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numV = Integer.parseInt(reader.readLine());

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		aCnt = new int[numV + 1];
		for (int i = 1; i <= numV; i++) {
			aCnt[i] = Integer.parseInt(inputData.nextToken());
		}

		chldn = new ArrayList[numV + 1];
		for (int i = 1; i <= numV; i++) {
			chldn[i] = new ArrayList<Integer>();
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 2; i <= numV; i++) {
			chldn[Integer.parseInt(inputData.nextToken())].add(i);
		}

		tACnt = new int[2][10_000_001];
		tCnt = new int[2];
		tXor = new int[2];

		dfs(1);
		long ans = 0;
		if (tXor[0] == 0) {
			ans = tCnt[0] * (tCnt[0] - 1L) / 2 + tCnt[1] * (tCnt[1] - 1L) / 2;
			for (int cACnt = 0; cACnt <= 10_000_000; cACnt++) {
				ans += (long) tACnt[0][cACnt] * tACnt[1][cACnt];
			}
		} else {
			for (int cACnt = 0; cACnt <= 10_000_000; cACnt++) {
				if ((tXor[0] ^ cACnt) <= 10_000_000) {
					ans += (long) tACnt[0][cACnt] * tACnt[1][tXor[0] ^ cACnt];
				}
			}
		}
		System.out.println(ans);
	}

	// returns type of cV
	static int dfs(int cV) {
		int type = 0;
		for (int adj : chldn[cV]) {
			type = dfs(adj) ^ 1;
		}

		tACnt[type][aCnt[cV]]++;
		tCnt[type]++;
		tXor[type] ^= aCnt[cV];
		return type;
	}

}
