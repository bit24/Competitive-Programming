import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_409D {

	static long[] x;
	static long[] y;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numP = Integer.parseInt(reader.readLine());

		x = new long[numP];
		y = new long[numP];
		for (int i = 0; i < numP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
		}

		double ans = Double.POSITIVE_INFINITY;

		for (int i = 0; i < numP; i++) {
			ans = Math.min(ans, req((i - 1 + numP) % numP, i, (i + 1) % numP));
		}
		System.out.println(ans);
	}

	static double req(int p1, int p2, int p3) {
		long a = y[p3] - y[p1];
		long b = x[p1] - x[p3];
		long c = -(x[p1] * a + y[p1] * b);
		double dist = Math.abs(a * x[p2] + b * y[p2] + c) / Math.sqrt(a * a + b * b);
		return dist / 2;
	}

}
