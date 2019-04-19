import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_424D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numP = Integer.parseInt(inputData.nextToken());
		int numK = Integer.parseInt(inputData.nextToken());
		int dest = Integer.parseInt(inputData.nextToken());

		Integer[] people = new Integer[numP];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numP; i++) {
			people[i] = Integer.parseInt(inputData.nextToken());
		}

		Integer[] keys = new Integer[numK];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numK; i++) {
			keys[i] = Integer.parseInt(inputData.nextToken());
		}

		Arrays.sort(people);
		Arrays.sort(keys);

		int mCost = Integer.MAX_VALUE;
		for (int i = 0; i + numP <= numK; i++) {
			int cMCost = 0;
			for (int j = 0; j < numP; j++) {
				int cCost = Math.abs(people[j] - keys[i + j]) + Math.abs(keys[i + j] - dest);
				if (cCost > cMCost) {
					cMCost = cCost;
				}
			}
			if (cMCost < mCost) {
				mCost = cMCost;
			}
		}
		System.out.println(mCost);
	}

}
