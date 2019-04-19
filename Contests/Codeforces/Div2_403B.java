import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_403B {

	public static void main(String[] args) throws IOException {
		new Div2_403B().execute();
	}

	int[] xVal;
	int[] speed;

	int numE;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		numE = Integer.parseInt(reader.readLine());

		xVal = new int[numE];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			xVal[i] = Integer.parseInt(inputData.nextToken());
		}

		speed = new int[numE];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			speed[i] = Integer.parseInt(inputData.nextToken());
		}
		System.out.printf("%.6f", binSearch());
	}

	boolean isPossible(double time) {
		double left = xVal[0] - speed[0] * time;
		double right = xVal[0] + speed[0] * time;

		for (int i = 1; i < numE; i++) {
			double nLeft = xVal[i] - speed[i] * time;
			double nRight = xVal[i] + speed[i] * time;
			if (nLeft > left) {
				left = nLeft;
			}
			if (nRight < right) {
				right = nRight;
			}
			if (right - left < 0.0000008) {
				return false;
			}
		}

		return right - left > 0.0000008;
	}

	double binSearch() {
		double low = 0;
		double high = 1_000_000_001;
		while (high - low >= 0.0000008) {
			double mid = (low + high) / 2;
			if (isPossible(mid)) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return low;
	}

}
