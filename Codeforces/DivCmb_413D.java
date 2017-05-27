import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class DivCmb_413D {

	static int tL;
	static int tW;
	static int sL;
	static int sW;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		tL = Integer.parseInt(inputData.nextToken());
		tW = Integer.parseInt(inputData.nextToken());
		sL = Integer.parseInt(inputData.nextToken());
		sW = Integer.parseInt(inputData.nextToken());

		int numO = Integer.parseInt(inputData.nextToken());

		PriorityQueue<Integer> minQ = new PriorityQueue<Integer>();
		inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < numO; i++) {
			minQ.add(Integer.parseInt(inputData.nextToken()));
			if (minQ.size() > 34) {
				minQ.remove();
			}
		}
		ArrayList<Integer> opts = new ArrayList<Integer>(minQ);
		Collections.sort(opts);
		Collections.reverse(opts);

		int[] mWGL = new int[100_001];
		mWGL[sL] = sW;
		if (sL >= tL && sW >= tW) {
			System.out.println(0);
			return;
		}
		mWGL[sW] = sL;
		if (sW >= tL && sL >= tW) {
			System.out.println(0);
			return;
		}

		for (int cnt = 0; cnt < opts.size(); cnt++) {
			long mult = opts.get(cnt);
			for (int oLen = 100_000; oLen >= 1; oLen--) {
				int nLen = (int) Math.min(100_000, oLen * mult);
				mWGL[nLen] = Math.max(mWGL[nLen], mWGL[oLen]);

				if (nLen >= tL && mWGL[nLen] >= tW) {
					System.out.println(cnt + 1);
					return;
				}

				mWGL[oLen] = (int) Math.min(100_000, mWGL[oLen] * mult);

				if (oLen >= tL && mWGL[oLen] >= tW) {
					System.out.println(cnt + 1);
					return;
				}
			}
		}
		System.out.println(-1);
	}

}
