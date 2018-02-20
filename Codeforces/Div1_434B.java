import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeMap;

public class Div1_434B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nC = Integer.parseInt(reader.readLine());

		HashMap<Long, Integer> own = new HashMap<Long, Integer>();

		long[] pow = new long[10];
		pow[0] = 1;
		for (int i = 1; i < 10; i++) {
			pow[i] = pow[i - 1] * 10;
		}

		int[] nums = new int[nC];
		for (int i = 0; i < nC; i++) {
			nums[i] = Integer.parseInt(reader.readLine());
			for (int j = 0; j < 9; j++) {
				for (int k = j; k < 9; k++) {
					long cSub = (nums[i] % pow[9 - j] + pow[9 - j]) / pow[8 - k];
					Integer cOwn = own.get(cSub);
					if (cOwn == null) {
						own.put(cSub, i);
					} else if (cOwn != i) {
						own.put(cSub, -1);
					}
				}
			}
		}

		for (int i = 0; i < nC; i++) {
			int min = Integer.MAX_VALUE;
			int jInd = -1;
			int kInd = -1;
			for (int j = 0; j < 9; j++) {
				for (int k = Math.min(8, j + min - 1); k >= j; k--) {
					long cSub = (nums[i] % pow[9 - j] + pow[9 - j]) / pow[8 - k];
					if (own.get(cSub) != -1 && k - j + 1 < min) {
						min = k - j + 1;
						jInd = j;
						kInd = k;
					}
				}
			}
			printer.println(Integer.toString(nums[i]).substring(jInd, kInd + 1));
		}
		printer.close();
	}

}
