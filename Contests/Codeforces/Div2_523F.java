import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class Div2_523F {

	static int N;
	static int K;
	static int H;

	static BufferedReader reader;
	static PrintWriter printer;

	static Random rng = new Random();

	public static void main(String[] args) throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());

		int val = 1 + N * (K - 1);

		while (val > 1) {
			val /= K;
			H++;
		}

		int a;
		int b;
		lLoop:
		while (true) {
			a = 1 + rng.nextInt(N);
			b = 1 + rng.nextInt(N);
			cData(a, b);
			if (between.size() == 2 * H - 1) {
				break lLoop;
			}
		}

		int want = H - 1;

		while (a != b) {
			ArrayList<Integer> pCopy = new ArrayList<>(between);
			int pivot = between.get(rng.nextInt(between.size()));
			cData(a, pivot);

			if (between.size() - 1 == want) {
				printer.println("! " + pivot);
				printer.close();
				return;
			}

			if (between.size() <= want) {
				want -= (between.size() - 1);
				a = pivot;
				between = exclude(pCopy, between);
			} else {
				b = pivot;
			}
		}
		printer.println("! " + a);
		printer.close();
	}

	static ArrayList<Integer> exclude(ArrayList<Integer> set, ArrayList<Integer> rmv) {
		Collections.sort(set);
		Collections.sort(rmv);

		ArrayList<Integer> ret = new ArrayList<>();

		int j = 0;
		for (int i = 0; i < set.size(); i++) {
			while (j < rmv.size() && rmv.get(j) < set.get(i)) {
				j++;
			}

			if (j >= rmv.size() || !rmv.get(j).equals(set.get(i))) {
				ret.add(set.get(i));
			}
		}
		return ret;
	}

	static ArrayList<Integer> between = new ArrayList<>();

	static void cData(int a, int b) throws IOException {
		between.clear();

		for (int i = 1; i <= N; i++) {
			printer.println("? " + a + " " + i + " " + b);
		}
		printer.flush();

		for (int i = 1; i <= N; i++) {
			if (reader.readLine().equals("Yes")) {
				between.add(i);
			}
		}
	}
}
