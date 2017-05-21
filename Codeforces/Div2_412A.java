import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//resubmit?
public class Div2_412A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numE = Integer.parseInt(reader.readLine());

		int p = Integer.MAX_VALUE;
		boolean disorder = false;
		for (int i = 0; i < numE; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			if (a != b) {
				System.out.println("rated");
				return;
			} else if (b > p) {
				disorder = true;
			}
			p = b;
		}
		if (disorder) {
			System.out.println("unrated");
		} else {
			System.out.println("maybe");
		}
	}

}
