import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Div2_484E {

	static long w;
	static long h;
	static long sX;
	static long sY;
	static long vX;
	static long vY;

	static long eX;
	static long eY;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		w = Integer.parseInt(inputData.nextToken());
		h = Integer.parseInt(inputData.nextToken());
		sX = Integer.parseInt(inputData.nextToken());
		sY = Integer.parseInt(inputData.nextToken());
		vX = Integer.parseInt(inputData.nextToken());
		vY = Integer.parseInt(inputData.nextToken());

		if (vX == 0 || vY == 0) {
			processParallel();
		} else {
			processSlant();
		}

		if (eX == Long.MIN_VALUE) {
			printer.println(-1);
			printer.close();
			return;
		}

		eX /= w;
		eY /= h;

		eX = (eX % 2 + 2) % 2;
		eY = (eY % 2 + 2) % 2;

		if (eX == 0 && eY == 0) {
			printer.println("0 0");
		} else if (eX == 1 && eY == 0) {
			printer.println(w + " 0");
		} else if (eX == 0 && eY == 1) {
			printer.println("0 " + h);
		} else {
			printer.println(w + " " + h);
		}
		printer.close();
	}

	static void processSlant() {
		long cong1 = (w - sX) * (w + vX) % w;
		long cong2 = (h - sY) * (h + vY) % h;
		long whGCD = gcd(w, h);

		if ((cong1 - cong2) % whGCD != 0) {
			eX = Long.MIN_VALUE;
			eY = Long.MIN_VALUE;
			return;
		}

		extEuclid(w / whGCD, h / whGCD);
		long whLCM = w * h / whGCD;

		BigInteger sol = BigInteger.valueOf(cong1).multiply(BigInteger.valueOf(h)).divide(BigInteger.valueOf(whGCD))
				.multiply(BigInteger.valueOf(qRes)).add(BigInteger.valueOf(cong2).multiply(BigInteger.valueOf(w))
						.divide(BigInteger.valueOf(whGCD)).multiply(BigInteger.valueOf(pRes)))
				.mod(BigInteger.valueOf(whLCM));

		long solL = sol.longValue();

		eX = sX + vX * solL;
		eY = sY + vY * solL;
	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	static void processParallel() {
		if (vX != 0) {
			if (sY % h != 0) {
				eX = Long.MIN_VALUE;
				eY = Long.MIN_VALUE;
				return;
			}
			eX = vX > 0 ? w : 0;
			eY = sY;
		} else {
			if (sX % w != 0) {
				eX = Long.MIN_VALUE;
				eY = Long.MIN_VALUE;
				return;
			}
			eX = sX;
			eY = vY > 0 ? h : 0;
		}
	}

	static long pRes;
	static long qRes;

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
		pRes = dendA;
		qRes = dendB;
	}
}
