import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BalesOnATractor {

	static int N;
	static long MAXW;

	static ArrayList<Integer>[] aList;

	static long[] w;
	static int[] v;

	static ArrayList<Integer> roots = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		MAXW = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			aList[i] = new ArrayList<>();
		}

		w = new long[N];
		v = new int[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			w[i] = Integer.parseInt(inputData.nextToken());
			v[i] = Integer.parseInt(inputData.nextToken());
			int p = Integer.parseInt(inputData.nextToken()) - 1;
			if (p == -1) {
				roots.add(i);
			} else {
				aList[p].add(i);
			}
		}

		minW = new long[N][N * 20 + 1];
		for (long[] a : minW) {
			Arrays.fill(a, Long.MAX_VALUE / 4);
		}

		long[] fMin = new long[N * 20 + 1];
		Arrays.fill(fMin, Long.MAX_VALUE / 4);
		fMin[0] = 0;

		for (int cR : roots) {
			process(cR);

			for (int i = N * 20; i >= 0; i--) {
				for (int j = N * 20 - i; j >= 0; j--) {
					fMin[i + j] = Math.min(fMin[i + j], fMin[i] + minW[cR][j]);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i <= N * 20; i++) {
			if (fMin[i] <= MAXW) {
				ans = i;
			}
		}
		printer.println(ans);
		printer.close();
	}

	static long[][] minW;

	static void process(int cV) {
		minW[cV][v[cV]] = w[cV];

		for (int aV : aList[cV]) {
			process(aV);

			for (int i = N * 20; i >= 0; i--) {
				for (int j = N * 20 - i; j >= 0; j--) {
					minW[cV][i + j] = Math.min(minW[cV][i + j], minW[cV][i] + minW[aV][j]);
				}
			}
		}
	}
}
