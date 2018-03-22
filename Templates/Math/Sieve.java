import java.util.ArrayList;
import java.util.Arrays;

public class Sieve {

	public static void main(String[] args) {
		long sTime = System.currentTimeMillis();
		mobius();
		System.out.println(System.currentTimeMillis() - sTime);
	}

	static void linear() {
		boolean[] prime = new boolean[10_000_000];
		Arrays.fill(prime, true);
		prime[0] = prime[1] = false;

		ArrayList<Integer> primes = new ArrayList<Integer>();
		for (int i = 2; i < 10_000_000; i++) {
			if (prime[i]) {
				primes.add(i);
			}
			for (int cP : primes) {
				int prod = i * cP;
				if (prod < 10_000_000) {
					prime[prod] = false;
				} else {
					break;
				}
				if (i % cP == 0) {
					break;
				}
			}
		}
	}

	static void eratosthenes() {
		boolean[] prime = new boolean[10_000_000];
		Arrays.fill(prime, true);
		prime[0] = prime[1] = false;

		for (int i = 2; i < 10_000_000; i++) {
			if (prime[i]) {
				for (int m = 2 * i; m < 10_000_000; m += i) {
					prime[m] = false;
				}
			}
		}
	}

	static void phi() {
		boolean[] prime = new boolean[10_000_000];
		Arrays.fill(prime, true);
		prime[0] = prime[1] = false;
		int[] phi = new int[10_000_000];
		for (int i = 1; i < 10_000_000; i++) {
			phi[i] = i;
		}

		for (int i = 2; i < 10_000_000; i++) {
			if (prime[i]) {
				for (int m = 2 * i; m < 10_000_000; m += i) {
					prime[m] = false;
					phi[m] -= phi[m] / i;
				}
			}
		}
	}

	static void mobius() {
		boolean[] prime = new boolean[10_000_000];
		Arrays.fill(prime, true);
		prime[0] = prime[1] = false;
		int[] mobius = new int[10_000_000];
		Arrays.fill(mobius, 1);

		for (int i = 2; i < 10_000_000; i++) {
			if (prime[i]) {
				mobius[i] = -1;
				for (int m = 2 * i; m < 10_000_000; m += i) {
					prime[m] = false;
					mobius[m] = -mobius[m];
				}
				for (long sM = (long) i * i; sM < 10_000_000; sM += (long) i * i) {
					mobius[(int) sM] = 0;
				}
			}
		}
	}

}
