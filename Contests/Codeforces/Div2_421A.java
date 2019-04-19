import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_421A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numP = Integer.parseInt(inputData.nextToken());
		int base = Integer.parseInt(inputData.nextToken());
		int max = Integer.parseInt(inputData.nextToken());
		int incr = Integer.parseInt(inputData.nextToken());
		int reread = Integer.parseInt(inputData.nextToken());

		int numD = 0;
		int read = 0;
		while (read < numP) {
			int cRead = base - Math.min(read, reread);
			read += cRead;
			numD++;
			base += incr;
			if (base > max) {
				base = max;
			}
		}
		System.out.println(numD);
	}

}
