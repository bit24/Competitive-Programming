import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div2_422C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numI = Integer.parseInt(inputData.nextToken());
		int req = Integer.parseInt(inputData.nextToken());

		int[] left = new int[numI];
		int[] right = new int[numI];
		int[] cost = new int[numI];

		for (int i = 0; i < numI; i++) {
			inputData = new StringTokenizer(reader.readLine());
			left[i] = Integer.parseInt(inputData.nextToken());
			right[i] = Integer.parseInt(inputData.nextToken());
			cost[i] = Integer.parseInt(inputData.nextToken());
		}

		Integer[] byLeft = new Integer[numI];
		Integer[] byRight = new Integer[numI];

		for (int i = 0; i < numI; i++) {
			byLeft[i] = i;
			byRight[i] = i;
		}

		Arrays.sort(byLeft, (a, b) -> left[a] < left[b] ? -1 : (left[a] == left[b] ? 0 : 1));
		Arrays.sort(byRight, (a, b) -> right[a] < right[b] ? -1 : (right[a] == right[b] ? 0 : 1));

		TreeMap<Integer, Integer> mCost = new TreeMap<Integer, Integer>();

		int nAdd = 0;

		int fCost = Integer.MAX_VALUE;
		for (int i : byLeft) {
			int cInd = left[i];

			while (right[byRight[nAdd]] < cInd) {
				int nInd = byRight[nAdd++];
				int cDur = right[nInd] - left[nInd] + 1;
				int cCost = cost[nInd];
				Integer prev = mCost.get(cDur);
				if (prev == null || prev > cCost) {
					mCost.put(cDur, cCost);
				}
			}

			Integer supp = mCost.get(req - (right[i] - left[i] + 1));
			if (supp != null && supp + cost[i] < fCost) {
				fCost = supp + cost[i];
			}
		}
		if (fCost == Integer.MAX_VALUE) {
			System.out.println(-1);
		} else {
			System.out.println(fCost);
		}
	}
}
