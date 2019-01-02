
class BitSet {
	static final int MMASK = (1 << 6) - 1; // &MMASK == %64

	int nE;
	int lE;
	long[] a;

	BitSet(int size) {
		nE = (size + 63) >> 6;
		lE = size & MMASK;
		a = new long[nE];
	}

	void set(int ind) {
		a[ind >> 6] |= 1L << (ind & MMASK);
	}

	boolean isSet(int ind) {
		return (a[ind >> 6] & (1L << (ind & MMASK))) != 0;
	}

	void shiftL(int k) { // k < 64, can be extended by shifting k/64 first and then k%64
		long cMask = (1 << k) - 1;

		a[nE - 1] <<= k;

		for (int i = nE - 2; i >= 0; i--) {
			a[i + 1] |= (a[i] >>> (64 - k)) & cMask;
			a[i] <<= k;
		}

		a[nE - 1] &= (1 << lE) - 1;
	}

	void ore(BitSet o) {
		for (int i = 0; i < nE; i++) {
			a[i] |= o.a[i];
		}
	}
}