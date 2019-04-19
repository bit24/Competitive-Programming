import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_419A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String nLine = reader.readLine();
		int a = Integer.parseInt(nLine.substring(0, 2));
		int b = Integer.parseInt(nLine.substring(3, 5));

		int mCnt = 0;
		while (true) {

			int tarInt = 10 * (a % 10) + (a / 10);

			if (tarInt < 60 && tarInt >= b) {
				System.out.println(tarInt - b + mCnt);
				return;
			}
			mCnt += 60 - b;
			b = 0;
			a++;
			if (a >= 24) {
				a -= 24;
			}
		}

	}

}
