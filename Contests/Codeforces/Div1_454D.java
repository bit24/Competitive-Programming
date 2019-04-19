import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_454D {

	static long[] items;
	static long[] mods;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nI = Integer.parseInt(inputData.nextToken());
		long bMod = Integer.parseInt(inputData.nextToken());

		items = new long[nI];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken());
		}

		mods = new long[50];

		mods[0] = bMod;

		for (int i = 1; mods[i - 1] != 1; i++) {
			mods[i] = phi(mods[i - 1]);
		}

		int nQ = Integer.parseInt(reader.readLine());

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken()) - 1;
			int r = Integer.parseInt(inputData.nextToken()) - 1;
			printer.println(calc(l, r, 0) % bMod);
		}
		printer.close();
	}

	static long calc(int l, int r, int mI) {
		if (l == r + 1) {
			return 1;
		}
		if (mods[mI] == 1) {
			return items[l];
		}
		return exp(items[l], calc(l + 1, r, mI + 1), mods[mI]);
	}

	static long phi(long num) {
		long ans = 1;
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0) {
				while (num % i == 0) {
					num /= i;
					ans *= i;
				}
				ans = ans / i * (i - 1);
			}
		}
		if (num != 1) {
			ans *= (num - 1);
		}
		return ans;
	}

	static long exp(long base, long pow, long mod) {
		long ans = 1;
		while (pow > 0) {
			if ((pow & 1) != 0) {
				ans = ans * base;
				if (ans >= mod) {
					ans = ans % mod + mod;
				}
			}
			base = base * base;
			if (base >= mod) {
				base = base % mod + mod;
			}
			pow >>= 1;
		}
		return ans;
	}

}
