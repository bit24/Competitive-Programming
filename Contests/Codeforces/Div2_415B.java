import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_415B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nD = Integer.parseInt(inputData.nextToken());
		int nB = Integer.parseInt(inputData.nextToken());

		int[] nI = new int[nD];
		int[] nC = new int[nD];

		long ans = 0;

		for (int i = 0; i < nD; i++) {
			inputData = new StringTokenizer(reader.readLine());
			nI[i] = Integer.parseInt(inputData.nextToken());
			nC[i] = Integer.parseInt(inputData.nextToken());
			ans += Math.min(nI[i], nC[i]);
		}

		PriorityQueue<Integer> boosted = new PriorityQueue<Integer>();

		for (int i = 0; i < nD; i++) {
			boosted.add(min0(nC[i] - nI[i], nI[i]));
			if (boosted.size() > nB) {
				boosted.remove();
			}
		}

		for (int bV : boosted) {
			ans += bV;
		}
		System.out.println(ans);
	}

	static int min0(int a, int b) {
		return Math.max(0, Math.min(a, b));
	}

}
