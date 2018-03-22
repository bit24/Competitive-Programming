import java.util.TreeMap;

class MultiSet<T> {
	TreeMap<T, Integer> internal;

	void add(T addend) {
		Integer cnt = internal.get(addend);
		internal.put(addend, cnt == null ? 1 : cnt + 1);
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
}
