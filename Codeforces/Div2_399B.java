import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_399B {

	public static void main(String[] args) throws IOException {
		new Div2_399B().execute();
	}

	long tLength;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long init = Long.parseLong(inputData.nextToken());
		long l = Long.parseLong(inputData.nextToken()) - 1;
		long r = Long.parseLong(inputData.nextToken()) - 1;

		tLength = 1;
		long numA = 1;
		for (long curr = init; curr > 1; curr /= 2) {
			tLength += 2 * numA;
			numA *= 2;
		}
		System.out.println(add(tLength, init, 0, tLength - 1, l, r));
	}

	long add(long cLength, long cNum, long cL, long cR, long qL, long qR) {
		if (cNum == 1) {
			return Math.min(cR, qR) - Math.max(cL, qL) + 1;
		}
		if (cNum == 0) {
			return 0;
		}
		assert (cLength % 2 == 1);

		long mid = (cL + cR) / 2;
		long count = 0;
		if (qL <= mid && mid <= qR) {
			count += cNum % 2;
		}

		if (qL < mid) {
			count += add((cLength - 1) / 2, cNum / 2, cL, mid - 1, qL, qR);
		}
		if (qR > mid) {
			count += add((cLength - 1) / 2, cNum / 2, mid + 1, cR, qL, qR);
		}
		return count;
	}

}
