import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_384B {

	public static void main(String[] args) throws IOException {
		new Div2_384B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long n = Long.parseLong(inputData.nextToken());
		long numE = 1;
		for (int i = 1; i < n; i++) {
			numE *= 2;
			numE++;
		}
		long numD = Long.parseLong(inputData.nextToken()) - 1;

		long low = 0;
		long high = numE - 1;

		long number = n;
		while (low != high) {
			long mid = (low + high) / 2;
			if (numD == mid) {
				System.out.println(number);
				return;
			}
			if (numD < mid) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
			number--;
		}
		System.out.println(number);
	}

}
