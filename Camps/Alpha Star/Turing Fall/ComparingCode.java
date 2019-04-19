import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class ComparingCode {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int aLen = Integer.parseInt(inputData.nextToken());
		int bLen = Integer.parseInt(inputData.nextToken());

		int[][] aConv = new int[aLen][3];

		for (int[] cur : aConv) {
			Arrays.fill(cur, 10000);
		}

		HashMap<String, Integer> nMap = new HashMap<>();

		for (int i = 0; i < aLen; i++) {
			inputData = new StringTokenizer(reader.readLine());
			String left = inputData.nextToken();
			inputData.nextToken();
			String r1 = inputData.nextToken();
			inputData.nextToken();
			String r2 = inputData.nextToken();

			Integer val = nMap.get(left);
			if (val != null) {
				if (val < 1_000_000) {
					aConv[i][0] = i - val;
				} else {
					aConv[i][0] = 1_000_000 + i - (val - 1_000_000);
				}
			}
			nMap.put(left, i);

			val = nMap.get(r1);
			if (val != null) {
				if (val < 1_000_000) {
					aConv[i][1] = i - val;
				} else {
					aConv[i][1] = 1_000_000 + i - (val - 1_000_000);
				}
			}
			nMap.put(r1, 1_000_000 + i);

			val = nMap.get(r2);
			if (val != null) {
				if (val < 1_000_000) {
					aConv[i][2] = i - val;
				} else {
					aConv[i][2] = 1_000_000 + i - (val - 1_000_000);
				}
			}
			nMap.put(r2, 1_000_000 + i);
		}

		int[][] bConv = new int[bLen][3];

		for (int[] cur : bConv) {
			Arrays.fill(cur, 10000);
		}

		nMap = new HashMap<>();
		for (int i = 0; i < bLen; i++) {
			inputData = new StringTokenizer(reader.readLine());
			String left = inputData.nextToken();
			inputData.nextToken();
			String r1 = inputData.nextToken();
			inputData.nextToken();
			String r2 = inputData.nextToken();

			Integer val = nMap.get(left);
			if (val != null) {
				if (val < 1_000_000) {
					bConv[i][0] = i - val;
				} else {
					bConv[i][0] = 1_000_000 + i - (val - 1_000_000);
				}
			}
			nMap.put(left, i);

			val = nMap.get(r1);
			if (val != null) {
				if (val < 1_000_000) {
					bConv[i][1] = i - val;
				} else {
					bConv[i][1] = 1_000_000 + i - (val - 1_000_000);
				}
			}
			nMap.put(r1, 1_000_000 + i);

			val = nMap.get(r2);
			if (val != null) {
				if (val < 1_000_000) {
					bConv[i][2] = i - val;
				} else {
					bConv[i][2] = 1_000_000 + i - (val - 1_000_000);
				}
			}
			nMap.put(r2, 1_000_000 + i);
		}

		int ans = 0;
		for (int aS = 0; aS < aLen; aS++) {
			for (int bS = 0; bS < bLen; bS++) {
				for (int i = 0; aS + i < aLen && bS + i < bLen; i++) {
					if (!((aConv[aS + i][0] % 1_000_000 > i && bConv[bS + i][0] % 1_000_000 > i)
							|| aConv[aS + i][0] == bConv[bS + i][0])) {
						break;
					}
					boolean b1 = (aConv[aS + i][1] % 1_000_000 > i && bConv[bS + i][1] % 1_000_000 > i)
							|| aConv[aS + i][1] == bConv[bS + i][1];
					boolean b2 = (aConv[aS + i][2] % 1_000_000 > i && bConv[bS + i][2] % 1_000_000 > i)
							|| aConv[aS + i][2] == bConv[bS + i][2];

					boolean b3 = (aConv[aS + i][1] % 1_000_000 > i && bConv[bS + i][2] % 1_000_000 > i)
							|| aConv[aS + i][1] == bConv[bS + i][2];
					boolean b4 = (aConv[aS + i][2] % 1_000_000 > i && bConv[bS + i][1] % 1_000_000 > i)
							|| aConv[aS + i][2] == bConv[bS + i][1];

					if (!((b1 && b2) || (b3 && b4))) {
						break;
					}

					ans = Math.max(ans, i + 1);
				}
			}
		}
		printer.println(ans);
		printer.close();
	}
}
