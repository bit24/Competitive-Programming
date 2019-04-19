import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_421D {

	static final int INCR = 1;
	static final int DECR = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numI = Integer.parseInt(reader.readLine());
		int[] items = new int[numI + 1];

		int[] numFurth = new int[2 * (numI + 1)];

		long delta = 0;

		int cNumC = 0;
		int cNumF = 0;

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken());
			delta += Math.abs(items[i] - i);

			if (items[i] > i) {
				cNumC++;
				numFurth[items[i] - i + 1]++;
			} else {
				cNumF++;
			}
		}

		long ans = delta;
		int ansS = 0;
		for (int cRot = 1; cRot < numI; cRot++) {
			cNumC -= numFurth[cRot];
			cNumF += numFurth[cRot];
			delta += cNumF - cNumC;

			// items[numI - cRot] is a special case
			delta -= Math.abs(items[numI - cRot + 1] - (numI + 1));
			delta += items[numI - cRot + 1] - 1;

			if (items[numI - cRot + 1] > 1) {
				cNumF--;
				cNumC++;
				numFurth[cRot + items[numI - cRot + 1]]++;
			}
			if (delta < ans) {
				ans = delta;
				ansS = cRot;
			}
		}
		System.out.println(ans + " " + ansS);
	}

}
