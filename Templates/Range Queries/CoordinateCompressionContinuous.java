import java.util.ArrayList;
import java.util.Collections;

public class CoordinateCompressionContinuous {

	ArrayList<Integer> inpPos = new ArrayList<>(); // 0-indexed
	int[] pos; // 1-indexed

	void init() {
		if (inpPos.isEmpty()) {
			pos = new int[1];
			return;
		}

		Collections.sort(inpPos);
		pos = process(inpPos);
	}

	int[] process(ArrayList<Integer> inp) {
		int size = 1;
		for (int i = 1; i < inp.size(); i++) {
			if (!inp.get(i).equals(inp.get(i))) {
				size++;
			}
		}

		// 1-indexed
		int[] clean = new int[size + 1];
		int nI = 1;
		clean[nI++] = inp.get(0);
		for (int i = 1; i < inp.size(); i++) {
			if (!inp.get(i).equals(inp.get(i))) {
				clean[nI++] = inp.get(i);
			}
		}
		return clean;
	}

	// returns last element less than or equal
	int lessEqual(int value) {
		int low = 0;
		int high = pos.length - 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (pos[mid] <= value) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	int greaterEqual(int value) {
		int ind = lessEqual(value);
		if (pos[ind] < value) {
			ind++;
		}
		return ind;
	}
}
