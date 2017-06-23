import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_407E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int tar = Integer.parseInt(inputData.nextToken());
		int numInp = Integer.parseInt(inputData.nextToken());
		boolean[] hasT = new boolean[1001];
		int numT = 0;

		boolean hLower = false, hUpper = false;

		inputData = new StringTokenizer(reader.readLine());
		while (numInp-- != 0) {
			int nxt = Integer.parseInt(inputData.nextToken());
			if (!hasT[nxt]) {
				hasT[nxt] = true;
				numT++;
			}

			hLower |= nxt <= tar;
			hUpper |= nxt >= tar;
		}

		int[] trans = new int[numT];
		for (int i = 0; i <= 1000; i++) {
			if (hasT[i]) {
				trans[--numT] = i - tar;
			}
		}

		if (!(hLower && hUpper)) {
			System.out.println(-1);
			return;
		}

		// -1000 to 1000
		int[] dist = new int[2001];
		Arrays.fill(dist, -1);
		dist[0 + 1000] = 0;

		ArrayDeque<Integer> bfsQ = new ArrayDeque<Integer>();
		bfsQ.add(0);

		while (true) {
			int cur = bfsQ.remove();
			int cDist = dist[cur + 1000];

			for (int cT : trans) {
				int nxt = cur + cT;
				if (-1000 <= nxt && nxt <= 1000) {
					if (nxt == 0) {
						System.out.println(cDist + 1);
						return;
					}
					if (dist[nxt + 1000] == -1) {
						dist[nxt + 1000] = dist[cur + 1000] + 1;
						bfsQ.add(nxt);
					}
				}
			}
		}
	}

}
