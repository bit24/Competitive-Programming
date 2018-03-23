import java.util.ArrayList;
import java.util.Collections;

class CompressedBIT {
	private long[] BIT;
	private int[] pos;

	// inpPos may contain duplicates, is zero indexed
	CompressedBIT(ArrayList<Integer> inpPos) {
		if (inpPos.isEmpty()) {
			BIT = new long[1];
			pos = new int[1];
			return;
		}

		Collections.sort(inpPos);
		pos = process(inpPos);
		BIT = new long[pos.length];
	}

	void update(int ind, long delta) {
		updateBIT(lessEqual(ind), delta);
	}

	long query(int l, int r) {
		int cRI = lessEqual(r);
		int cLE = lessEqual(l);
		if (pos[cLE] == l) {
			cLE--;
		}
		return queryBIT(cRI) - queryBIT(cLE);
	}

	private int[] process(ArrayList<Integer> inp) {
		if (inp.isEmpty()) {
			return new int[0];
		}
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
	private int lessEqual(int value) {
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

	private void updateBIT(int ind, long delta) {
		while (ind < BIT.length) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	private long queryBIT(int ind) {
		long sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}
}