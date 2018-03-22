
class BIT {

	// one indexed as operations on index 0 does not work
	int[] BIT;
	int numE;

	BIT(int[] initalData) {
		BIT = new int[initalData.length];
		numE = initalData.length - 1;
		for (int i = 1; i < initalData.length; i++) {
			update(i, initalData[i]);
		}
	}

	void update(int ind, int delta) {
		while (ind <= numE) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	int query(int ind) {
		int sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}
}
