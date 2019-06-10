import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class CowGymnasts {

	static final long MOD = 1_000_000_007;
	static long N;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("gymnasts.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("gymnasts.out")));
		N = Long.parseLong(reader.readLine());
		reader.close();

		pFact(N);
		iFact(0, 1);
		Collections.sort(facts);
		facts.remove(facts.size() - 1);

		long ans = 0;
		for (long cGCD : facts) {
			long comp = N / cGCD;
			ans = (ans + exp(cGCD) * phi(comp)) % MOD;
		}
		ans = (ans + MOD - N % MOD + 2) % MOD;
		printer.println(ans);
		printer.close();
	}

	static int nF;
	static long[] pF = new long[11];
	static long[] pFC = new long[11];

	static void pFact(long inp) {
		for (long c = 2; c * c <= inp; c++) {
			if (inp % c == 0) {
				pF[nF] = c;
				while (inp % c == 0) {
					pFC[nF]++;
					inp /= c;
				}
				nF++;
			}
		}

		if (inp != 1) {
			pF[nF] = inp;
			pFC[nF] = 1;
			nF++;
		}
	}

	static ArrayList<Long> facts = new ArrayList<>();

	static void iFact(int i, long cur) {
		if (i == nF) {
			facts.add(cur);
			return;
		}
		long cPow = 1;
		for (int j = 0; j <= pFC[i]; j++) {
			iFact(i + 1, cur * cPow);
			cPow *= pF[i];
		}
	}

	static long phi(long inp) {
		assert (N % inp == 0);

		long cur = 1;
		for (int i = 0; i < nF; i++) {
			if (inp % pF[i] == 0) {
				inp /= pF[i];
				cur *= pF[i] - 1;
				while (inp % pF[i] == 0) {
					inp /= pF[i];
					cur *= pF[i];
				}
			}
		}
		return cur;
	}

	static long exp(long exp) {
		long base = 2;
		long cur = 1;
		while (exp > 0) {
			if ((exp & 1) == 1) {
				cur = cur * base % MOD;
			}
			exp >>= 1;
			base = base * base % MOD;
		}
		return cur;
	}
}
