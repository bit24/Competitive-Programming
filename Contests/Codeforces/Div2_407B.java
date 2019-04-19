import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_407B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int init = Integer.parseInt(inputData.nextToken());
		int ratio = Integer.parseInt(inputData.nextToken());
		int limit = Integer.parseInt(inputData.nextToken());
		int badCnt = Integer.parseInt(inputData.nextToken());

		TreeSet<Integer> bad = new TreeSet<Integer>();
		inputData = new StringTokenizer(reader.readLine());
		while (badCnt-- > 0) {
			bad.add(Integer.parseInt(inputData.nextToken()));
		}

		if (Math.abs(init) > limit) {
			System.out.println(0);
			return;
		}
		if (init == 0) {
			System.out.println(bad.contains(0) ? 0 : "inf");
			return;
		}
		if (ratio == 0) {
			if (bad.contains(0)) {
				System.out.println(bad.contains(init) ? 0 : 1);
			} else {
				System.out.println("inf");
			}
			return;
		}
		if (ratio == -1) {
			if (!bad.contains(init) || !bad.contains(-init)) {
				System.out.println("inf");
			} else {
				System.out.println(0);
			}
			return;
		}
		if (ratio == 1) {
			System.out.println(bad.contains(init) ? 0 : "inf");
			return;
		}

		int cnt = 0;
		long cur = init;

		while (Math.abs(cur) <= limit) {
			if (!bad.contains((int) cur)) {
				cnt++;
			}
			cur *= ratio;
		}
		System.out.println(cnt);
	}

}
