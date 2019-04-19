import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Edu_23B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numE = Integer.parseInt(reader.readLine());
		int[] min = new int[] { Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE };
		int[] cnt = new int[3];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			int nxt = Integer.parseInt(inputData.nextToken());
			if (nxt < min[0]) {
				min[2] = min[1];
				cnt[2] = cnt[1];
				min[1] = min[0];
				cnt[1] = cnt[0];
				min[0] = nxt;
				cnt[0] = 1;
			} else if (nxt == min[0]) {
				cnt[0]++;
			} else if (nxt < min[1]) {
				min[2] = min[1];
				cnt[2] = cnt[1];
				min[1] = nxt;
				cnt[1] = 1;
			} else if (nxt == min[1]) {
				cnt[1]++;
			} else if (nxt < min[2]) {
				min[2] = nxt;
				cnt[2] = 1;
			} else if (nxt == min[2]) {
				cnt[2]++;
			}
		}

		if (cnt[0] >= 3) {
			System.out.println((long) cnt[0] * (cnt[0] - 1) * (cnt[0] - 2) / 6);
		} else if (cnt[0] + cnt[1] >= 3) {
			if (cnt[0] == 1) {
				System.out.println((long) cnt[0] * cnt[1] * (cnt[1] - 1) / 2);
			} else {
				System.out.println((long) cnt[0] * (cnt[0] - 1) * cnt[1] / 2);
			}
		} else {
			System.out.println((long) cnt[0] * cnt[1] * cnt[2]);
		}
	}

}
