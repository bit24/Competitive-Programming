import java.util.ArrayList;
import java.util.Arrays;

// runtime of E*max match
// E*V worse case

class MaxBipartiteMatch {
	int[] rMatch;
	int[] lMatch;

	boolean[] assigned;

	ArrayList<ArrayList<Integer>> aList;

	public boolean isPossible(int cV) {
		for (int adj : aList.get(cV)) {
			if (!assigned[adj]) {
				assigned[adj] = true;

				if (lMatch[adj] == -1 || isPossible(lMatch[adj])) {
					rMatch[cV] = adj;
					lMatch[adj] = cV;
					return true;
				}
			}
		}
		return false;
	}

	public int compute(ArrayList<ArrayList<Integer>> aList) {
		rMatch = new int[aList.size()];
		lMatch = new int[aList.size()];
		this.aList = aList;

		Arrays.fill(rMatch, -1);
		Arrays.fill(lMatch, -1);

		int count = 0;
		for (int i = 0; i < aList.size(); i++) {
			assigned = new boolean[aList.size()];
			if (isPossible(i)) {
				count++;
			}
		}
		return count;
	}

}
