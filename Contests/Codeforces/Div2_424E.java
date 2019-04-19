import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div2_424E {

	static int numC;
	static int[] bit;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		numC = Integer.parseInt(reader.readLine());

		ArrayList<Integer>[] indices = new ArrayList[100_001];
		for (int i = 1; i <= 100_000; i++) {
			indices[i] = new ArrayList<Integer>();
		}

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numC; i++) {
			indices[Integer.parseInt(inputData.nextToken())].add(i);
		}

		bit = new int[numC + 1];

		long cnt = 0;

		int pI = 0;
		for (int cV = 1; cV <= 100_000; cV++) {
			if (indices[cV].size() == 0) {
				continue;
			}
			ref = pI;
			Collections.sort(indices[cV], sortOccur);

			for (int cI : indices[cV]) {
				if (pI < cI) {
					cnt += cI - pI - query(pI + 1, cI);
				} else {
					cnt += numC - pI - query(pI + 1, numC);
					cnt += cI - query(cI - 1);
				}
				update(cI, 1);
				pI = cI;
			}
		}
		System.out.println(cnt);
	}

	static int ref;

	static Comparator<Integer> sortOccur = new Comparator<Integer>() {

		public int compare(Integer i1, Integer i2) {
			int d1 = i1 < ref ? numC - ref + i1 : i1 - ref;

			int d2 = i2 < ref ? numC - ref + i2 : i2 - ref;
			return Integer.compare(d1, d2);
		}

	};

	static void update(int i, int d) {
		while (i <= numC) {
			bit[i] += d;
			i += (i & -i);
		}
	}

	static int query(int l, int r) {
		return query(r) - query(l - 1);
	}

	static int query(int i) {
		int s = 0;
		while (i > 0) {
			s += bit[i];
			i -= (i & -i);
		}
		return s;
	}

}
