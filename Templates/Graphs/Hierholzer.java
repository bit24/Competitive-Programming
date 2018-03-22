import java.util.ArrayList;
import java.util.Collection;
import java.util.ArrayDeque;
import java.util.TreeMap;

class Hierholzer {

	static int nE;

	static int nV;

	static ArrayList<Integer>[] aList;
	static MultiSet<Integer>[] mList;

	void fTour() {
		int sV = 0;
		for (int cV = 0; cV < nV; cV++) {
			if (aList[cV].size() % 2 != 0) {
				sV = cV;
				break;
			}
		}

		mList = new MultiSet[nV];
		for (int i = 0; i < nV; i++) {
			mList[i] = new MultiSet<>(aList[i]);
		}

		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		ArrayDeque<Integer> path = new ArrayDeque<Integer>();

		stack.push(sV);

		while (!stack.isEmpty()) {
			int cV = stack.pop();
			if (mList[cV].isEmpty()) {
				path.add(cV);
			} else {
				stack.push(cV);

				int aV = mList[cV].remove();
				mList[aV].remove(cV);

				stack.push(aV);
			}
		}
	}

	class MultiSet<T> {
		TreeMap<T, Integer> internal;

		void add(T addend) {
			Integer cnt = internal.get(addend);
			internal.put(addend, cnt == null ? 1 : cnt + 1);
		}

		boolean isEmpty() {
			return internal.isEmpty();
		}

		void remove(T subtrahend) {
			Integer cnt = internal.get(subtrahend);
			if (cnt != null) {
				if (cnt == 1) {
					internal.remove(subtrahend);
				} else {
					internal.put(subtrahend, cnt - 1);
				}
			}
		}
		
		T remove() {
			T fKey = internal.firstKey();
			remove(fKey);
			return fKey;
		}

		MultiSet(Collection<T> inp) {
			for (T cItem : inp) {
				add(cItem);
			}
		}
	}
}import java.util.ArrayList;
import java.util.Collection;
import java.util.ArrayDeque;
import java.util.TreeMap;

class Hierholzer {

	static int nE;

	static int nV;

	static ArrayList<Integer>[] aList;
	static MultiSet<Integer>[] mList;

	void fTour() {
		int sV = 0;
		for (int cV = 0; cV < nV; cV++) {
			if (aList[cV].size() % 2 != 0) {
				sV = cV;
				break;
			}
		}

		mList = new MultiSet[nV];
		for (int i = 0; i < nV; i++) {
			mList[i] = new MultiSet<>(aList[i]);
		}

		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		ArrayDeque<Integer> path = new ArrayDeque<Integer>();

		stack.push(sV);

		while (!stack.isEmpty()) {
			int cV = stack.pop();
			if (mList[cV].isEmpty()) {
				path.add(cV);
			} else {
				stack.push(cV);

				int aV = mList[cV].remove();
				mList[aV].remove(cV);

				stack.push(aV);
			}
		}
	}

	class MultiSet<T> {
		TreeMap<T, Integer> internal;

		void add(T addend) {
			Integer cnt = internal.get(addend);
			internal.put(addend, cnt == null ? 1 : cnt + 1);
		}

		boolean isEmpty() {
			return internal.isEmpty();
		}

		void remove(T subtrahend) {
			Integer cnt = internal.get(subtrahend);
			if (cnt != null) {
				if (cnt == 1) {
					internal.remove(subtrahend);
				} else {
					internal.put(subtrahend, cnt - 1);
				}
			}
		}
		
		T remove() {
			T fKey = internal.firstKey();
			remove(fKey);
			return fKey;
		}

		MultiSet(Collection<T> inp) {
			for (T cItem : inp) {
				add(cItem);
			}
		}
	}
}
