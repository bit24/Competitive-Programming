import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_432B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long aX = Integer.parseInt(inputData.nextToken());
		long aY = Integer.parseInt(inputData.nextToken());
		long bX = Integer.parseInt(inputData.nextToken());
		long bY = Integer.parseInt(inputData.nextToken());
		long cX = Integer.parseInt(inputData.nextToken());
		long cY = Integer.parseInt(inputData.nextToken());

		if ((aX - bX) * (aX - bX) + (aY - bY) * (aY - bY) != (bX - cX) * (bX - cX) + (bY - cY) * (bY - cY)) {
			System.out.println("No");
			return;
		}
		if (aX == bX) {
			if (bX == cX) {
				System.out.println("No");
				return;
			}
		} else {
			if (bX != cX) {
				long num1 = aY - bY;
				long den1 = aX - bX;
				long gcd1 = gcd(num1, den1);
				num1 /= gcd1;
				den1 /= gcd1;
				if (den1 < 0) {
					num1 = -num1;
					den1 = -den1;
				}

				long num2 = bY - cY;
				long den2 = bX - cX;
				long gcd2 = gcd(num2, den2);
				num2 /= gcd2;
				den2 /= gcd2;
				if (den2 < 0) {
					num2 = -num2;
					den2 = -den2;
				}
				if (num1 == num2 && den1 == den2) {
					System.out.println("No");
					return;
				}
			}
		}
		System.out.println("Yes");
	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

}
