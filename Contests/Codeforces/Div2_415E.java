import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_415E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numQ = Integer.parseInt(reader.readLine());
		while (numQ-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			long t = Long.parseLong(inputData.nextToken());
			long l = Long.parseLong(inputData.nextToken());
			long b = Long.parseLong(inputData.nextToken());
			long r = Long.parseLong(inputData.nextToken());

			T = Long.parseLong(inputData.nextToken());

			// System.out.println(query(r, b));
			// System.out.println(query(l - 1, b));
			// System.out.println(query(r, t - 1));
			// System.out.println(query(l - 1, b - 1));

			long ans = (MOD * 2 + query(r, b) - query(l - 1, b) - query(r, t - 1) + query(l - 1, t - 1)) % MOD;
			printer.println(ans);
			// printer.println();
		}
		printer.close();
	}

	static long T;

	static final long MOD = 1_000_000_007L;

	static long query(long wid, long hei) {
		return query(wid, hei, 0);
	}

	static long query(long wid, long hei, final long off) {
		if (wid < hei) {
			long temp = wid;
			wid = hei;
			hei = temp;
		}
		assert (hei <= wid);
		assert (off >= 0);

		if (off >= T || wid <= 0 || hei <= 0) {
			return 0;
		}

		long blk = Long.highestOneBit(hei);

		long ext = wid % blk;
		long reg = wid - ext;

		long ans;

		// regOff indicates the item at position reg
		long regOff = reg + off;

		if (regOff < T) {
			ans = (sum(1 + off, regOff) * blk % MOD + sum(regOff + 1, min(regOff + blk, T)) * ext) % MOD;
		} else {
			ans = sum(1 + off, T) * blk % MOD;
		}

		assert (0 <= ans && ans < MOD);

		// computation of sub values
		long rem = hei - blk;
		assert (rem < blk);
		if (rem == 0) {
			return ans;
		}

		ext = wid % (blk << 1);
		reg = wid - ext;

		regOff = reg + off;

		if (regOff < T) {
			ans = (ans + sum(1 + off, regOff) * rem) % MOD;
			assert (0 <= ans && ans < MOD);

			if (ext >= blk) {
				long skip = reg + blk;
				if (skip + off < T) {
					ans = (ans + sum(skip + 1 + off, min(skip + blk + off, T)) * rem) % MOD;
				}

				ext -= blk;
				if (ext > 0) {
					ans = (ans + query(ext, rem, reg + off)) % MOD;
				}

			} else if (ext > 0) {
				ans = (ans + query(ext, rem, reg + blk + off)) % MOD;
			}
		} else {
			ans = (ans + sum(1 + off, T) * rem) % MOD;
		}

		assert (0 <= ans && ans < MOD);

		return ans;
	}

	static long sum(long start, long end) {
		if (start > end) {
			return 0;
		}
		return (((end - start + 1L) * (start + end)) >> 1) % MOD;
	}

	static long min(long a, long b) {
		return a < b ? a : b;
	}

}
