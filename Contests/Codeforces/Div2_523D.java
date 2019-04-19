import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Div2_523D {

	static int N;
	static long X;
	static long Y;

	static final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		X = Integer.parseInt(inputData.nextToken());
		Y = Integer.parseInt(inputData.nextToken());

		TreeSet<Integer> times = new TreeSet<>();

		int[] l = new int[N];
		int[] r = new int[N];
		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			l[i] = Integer.parseInt(inputData.nextToken());
			r[i] = Integer.parseInt(inputData.nextToken()) + 1;
			times.add(l[i]);
			times.add(r[i]);
		}

		TreeMap<Integer, Integer> map = new TreeMap<>();
		ArrayList<Integer> rMap = new ArrayList<>(times);
		int nI = 0;
		for (int t : times) {
			map.put(t, nI++);
		}

		for (int i = 0; i < N; i++) {
			l[i] = map.get(l[i]);
			r[i] = map.get(r[i]);
		}

		int[] delta = new int[nI];
		for (int i = 0; i < N; i++) {
			delta[l[i]]++;
			delta[r[i]]--;
		}

		ArrayList<Integer> lUsed = new ArrayList<>();

		int cur = 0;
		long ans = 0;
		for (int i = 0; i < nI; i++) {
			if (delta[i] > 0) {
				int nAdd = delta[i];
				cur += nAdd;
				for (int j = 1; j <= nAdd; j++) {
					if (!lUsed.isEmpty()) {
						int last = lUsed.get(lUsed.size() - 1);
						long eCost = (rMap.get(i) - rMap.get(last)) * Y;
						if (eCost < X - Y) {
							ans = (ans + eCost) % MOD;
							lUsed.remove(lUsed.size() - 1);
						} else {
							ans = (ans + X - Y) % MOD;
						}
					} else {
						ans = (ans + X - Y) % MOD;
					}
				}
			} else if (delta[i] < 0) {
				int nRem = -delta[i];
				cur -= nRem;
				for (int j = 1; j <= nRem; j++) {
					lUsed.add(i);
				}
			}
			if (i + 1 < nI) {
				ans = (ans + (cur * Y % MOD) * (rMap.get(i + 1) - rMap.get(i)) % MOD) % MOD;
			}
		}
		printer.println(ans);
		printer.close();
	}
}
