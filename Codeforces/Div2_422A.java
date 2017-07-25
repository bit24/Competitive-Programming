import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_422A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long cur = Math.min(Long.parseLong(inputData.nextToken()), Long.parseLong(inputData.nextToken()));
		long prod = 1;
		while (cur > 0) {
			prod *= cur;
			cur--;
		}
		System.out.println(prod);
	}

}
