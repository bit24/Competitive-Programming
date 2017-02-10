import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Tester {

	static int MOD = 241;

	public static void main(String[] args) {
		while (true) {
			Random rng = new Random();
			int numE = 200;
			long[] elements = new long[] { 106, 220, 166, 27, 159, 114, 57, 94, 155, 10, 41, 107, 197, 40, 190, 134, 74,
					112, 240, 85, 63, 175, 132, 214, 8, 64, 4, 44, 203, 199, 209, 193, 198, 131, 232, 163, 138, 208, 16,
					60, 167, 235, 32, 49, 50, 237, 177, 0, 178, 105, 227, 194, 3, 124, 146, 73, 200, 140, 188, 26, 186,
					187, 28, 1, 195, 160, 127, 213, 141, 95, 191, 43, 165, 46, 92, 96, 129, 47, 116, 15, 70, 217, 143,
					212, 180, 23, 38, 223, 76, 137, 182, 207, 111, 135, 25, 79, 69, 185, 77, 192, 31, 101, 225, 82, 215,
					37, 170, 202, 239, 17, 234, 119, 53, 45, 62, 226, 196, 18, 145, 136, 87, 154, 229, 48, 30, 81, 144,
					5, 168, 102, 204, 126, 54, 113, 181, 224, 118, 231, 86, 52, 55, 128, 210, 164, 153, 139, 183, 72,
					68, 122, 100, 65, 36, 33, 11, 59, 176, 97, 35, 205, 151, 148, 173, 133, 108, 236, 67, 171, 104, 58,
					20, 91, 22, 9, 89, 222, 13, 42, 21, 123, 84, 90, 99, 218, 150, 219, 14, 109, 149, 117, 75, 230, 103,
					80, 158, 172, 156, 121, 161, 6 };
			TreeSet<Long> eSet = new TreeSet<Long>();
			ArrayList<Long> shuffleList = new ArrayList<Long>();

			// elements[0] = rng.nextInt(MOD);
			eSet.add(elements[0]);
			shuffleList.add(elements[0]);

			int d = /* rng.nextInt(MOD) */ 75;

			for (int i = 1; i < numE; i++) {
				// elements[i] = (elements[i - 1] + d) % MOD;
				eSet.add(elements[i]);
				shuffleList.add(elements[i]);
			}
			Collections.shuffle(shuffleList);

			for (int i = 0; i < numE; i++) {
				// elements[i] = shuffleList.get(i);
			}

			Div2_395E subject = new Div2_395E();

			subject.MOD = MOD;
			subject.numE = numE;
			subject.elements = elements;
			String output = subject.tExecute();
			System.out.println(Arrays.toString(elements));
			System.out.println(d);
			if (!valid(eSet, output)) {
				return;
			}
		}
	}

	static boolean valid(TreeSet<Long> eSet, String output) {
		StringTokenizer inputData = new StringTokenizer(output);
		int start = Integer.parseInt(inputData.nextToken());
		int diff = Integer.parseInt(inputData.nextToken());

		for (long c = start; !eSet.isEmpty(); c = (c + diff) % MOD) {
			if (eSet.contains(c)) {
				eSet.remove(c);
			} else {
				return false;
			}
		}
		return true;
	}

}
