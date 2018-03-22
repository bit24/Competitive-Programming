
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

	// note: only elements coprime to the mod have modular inverses
	// as such, division is only defined if den and mod are coprime
	static int div(int num, int den, int mod) {
		// "a" = den
		// "b" = mod

		long dend = den;
		long dendA = 1;

		long dsor = mod;
		long dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor;
			long rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		long gcd = dend;
		assert (gcd == 1);
		long pRes = dendA;
		return (int) ((num * pRes % mod + mod) % mod);
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
