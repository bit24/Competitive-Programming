import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div2_432C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int nP = Integer.parseInt(reader.readLine());

		long[][] point = new long[nP][5];
		for (int i = 0; i < nP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 5; j++) {
				point[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		ArrayDeque<Integer> good = new ArrayDeque<Integer>();
		for (int i = 0; i < nP; i++) {
			good.add(i);
		}

		while (good.size() >= 3) {
			int pA = good.remove();
			int pB = good.remove();
			int pC = good.remove();

			long[] v1 = vect(point[pA], point[pB]);
			long[] v2 = vect(point[pA], point[pC]);
			double aA = Math.acos(dot(v1, v2) / Math.sqrt(dot(v1, v1) * dot(v2, v2)));
			if (aA >= Math.PI / 2) {
				good.add(pA);
			}

			v1 = vect(point[pB], point[pA]);
			v2 = vect(point[pB], point[pC]);
			double aB = Math.acos(dot(v1, v2) / Math.sqrt(dot(v1, v1) * dot(v2, v2)));
			if (aB >= Math.PI / 2) {
				good.add(pB);
			}

			v1 = vect(point[pC], point[pA]);
			v2 = vect(point[pC], point[pB]);
			double aC = Math.acos(dot(v1, v2) / Math.sqrt(dot(v1, v1) * dot(v2, v2)));
			if (aC >= Math.PI / 2) {
				good.add(pC);
			}
		}

		ArrayList<Integer> ans = new ArrayList<Integer>();

		while (!good.isEmpty()) {
			int pos = good.remove();
			boolean isGood = true;
			tLoop:
			for (int i = 0; i < nP; i++) {
				if (i == pos) {
					continue;
				}
				for (int j = 0; j < nP; j++) {
					if (j == pos || j == i) {
						continue;
					}

					long[] v1 = vect(point[pos], point[i]);
					long[] v2 = vect(point[pos], point[j]);
					double a = Math.acos(dot(v1, v2) / Math.sqrt(dot(v1, v1) * dot(v2, v2)));
					if (a < Math.PI / 2) {
						isGood = false;
						break tLoop;
					}
				}
			}
			if (isGood) {
				ans.add(pos);
			}
		}

		Collections.sort(ans);

		System.out.println(ans.size());
		for (int i : ans) {
			System.out.println(1 + i);
		}
	}

	static long[] vect(long[] p1, long[] p2) {
		long[] ans = new long[5];
		for (int i = 0; i < 5; i++) {
			ans[i] = p2[i] - p1[i];
		}
		return ans;
	}

	static long dot(long[] a, long[] b) {
		long sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += a[i] * b[i];
		}
		return sum;
	}

}
