import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AIM_5F {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int cGuess = 1;
		long min = 1;

		while (true) {
			// printer.println(f(cGuess, min, true));
			f(cGuess, min, true);

			int distinct = 1;
			for (int i = 1; i < guesses.length; i++) {
				if (guesses[i] > guesses[i - 1] && guesses[i] <= 10004205361450474L) {
					distinct++;
				}
			}

			StringBuilder str = new StringBuilder();
			printer.println(distinct);
			for (int i = 0; i < guesses.length; i++) {
				if (i == 0 || guesses[i] > guesses[i - 1] && guesses[i] <= 10004205361450474L) {
					str.append(guesses[i]);
					str.append(' ');
				}
			}
			printer.println(str.toString());
			printer.flush();

			int resp = Integer.parseInt(reader.readLine());
			if (resp < 0) {
				return;
			}
			if (resp != 0) {
				min = guesses[resp - 1] + 1;
			}
			cGuess++;
		}
	}

	static long[] guesses;

	static long f(int g, long l, boolean store) {
		if (l >= 10004205361450474L) {
			return 10004205361450475L;
		}

		int nGuesses = (int) Math.min(l, 10_000);

		if (g == 5) {
			if (store) {
				guesses = new long[nGuesses];
				for (int i = 0; i < nGuesses; i++) {
					guesses[i] = l + i;
				}
			}
			return Math.min(10004205361450475L, l + nGuesses);
		}
		if (store) {
			guesses = new long[nGuesses];

			long cur = guesses[0] = f(g + 1, l, false);

			for (int i = 1; i < nGuesses; i++) {
				cur = guesses[i] = f(g + 1, cur + 1, false);
			}

			return f(g + 1, cur + 1, false);
		} else if (g == 3) {
			long cur = f(g + 1, l, false);

			for (int i = 1; i < nGuesses; i++) {
				if (cur + 1 >= 10_000) {
					cur += (nGuesses - i + 1) * 10_001L * 10_001;
					return Math.min(10004205361450475L, cur);
				}
				cur = f(g + 1, cur + 1, false);
			}
			return f(g + 1, cur + 1, false);

		} else if (g == 4) {
			long cur = f(g + 1, l, false);

			for (int i = 1; i < nGuesses; i++) {
				if (cur + 1 >= 10_000) {
					cur += (nGuesses - i + 1) * 10_001L;
					return Math.min(10004205361450475L, cur);
				}
				cur = f(g + 1, cur + 1, false);
			}

			return f(g + 1, cur + 1, false);
		} else {
			long cur = f(g + 1, l, false);

			for (int i = 1; i < nGuesses; i++) {
				cur = f(g + 1, cur + 1, false);
			}

			return f(g + 1, cur + 1, false);
		}
	}
}