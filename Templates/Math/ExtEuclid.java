
public class ExtEuclid {

	// equation: p*a + q*b = gcd(a,b);

	public static void main(String[] args) {
		System.out.println(inv(34, 53));
	}

	static void extEuclid(long a, long b) {
		long dsor = b; // divisor
		long dend = a; // dividend
		long dsorA = 0;
		long dsorB = 1;
		long dendA = 1;
		long dendB = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor;
			long rA = dendA - q * dsorA;
			long rB = dendB - q * dsorB;

			dend = dsor;
			dendA = dsorA;
			dendB = dsorB;

			dsor = r;
			dsorA = rA;
			dsorB = rB;
		}
		// gcd = dend;
		// pRes = dendA;
		// qRes = dendB;
	}

	static long inv(long number, long mod) {
		long dend = number, dendA = 1;

		long dsor = mod, dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor, rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		assert (dend == 1);
		return (dendA % mod + mod) % mod;
	}
}
