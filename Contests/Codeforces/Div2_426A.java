import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_426A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		int time = Integer.parseInt(reader.readLine());

		if (time % 2 == 0) {
			System.out.println("undefined");
			return;
		}

		time %= 4;

		StringTokenizer inputData = new StringTokenizer(input);
		char p1 = inputData.nextToken().charAt(0);
		char p2 = inputData.nextToken().charAt(0);

		int v1 = 0;
		int v2 = 0;

		if (p1 == '^') {
			v1 = 0;
		}
		if (p1 == '>') {
			v1 = 1;
		}
		if (p1 == 'v') {
			v1 = 2;
		}
		if (p1 == '<') {
			v1 = 3;
		}

		if (p2 == '^') {
			v2 = 0;
		}
		if (p2 == '>') {
			v2 = 1;
		}
		if (p2 == 'v') {
			v2 = 2;
		}
		if (p2 == '<') {
			v2 = 3;
		}

		if ((v1 + 1) % 4 == v2) {
			if (time == 1) {
				System.out.println("cw");
			}
			if (time == 3) {
				System.out.println("ccw");
			}
		} else {
			if (time == 1) {
				System.out.println("ccw");
			}
			if (time == 3) {
				System.out.println("cw");
			}
		}
	}

}
