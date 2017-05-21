import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_412B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int tar = Integer.parseInt(inputData.nextToken());
		int cur = Integer.parseInt(inputData.nextToken());
		int req = Integer.parseInt(inputData.nextToken());

		int sEval;
		if (req > cur) {
			int req50 = (req - cur + 49) / 50;
			sEval = cur + req50 * 50;
		} else {
			int sub50 = (cur - req) / 50;
			sEval = cur - sub50 * 50;
		}

		int reqScr = 0;
		for (int i = sEval; true; i += 50) {
			if (reached(i, tar)) {
				reqScr = i;
				break;
			}
		}

		if (reqScr <= cur) {
			System.out.println(0);
		} else {
			System.out.println((reqScr - cur + 99) / 100);
		}
	}

	static boolean reached(int inp, int tar) {
		int i = (inp / 50) % 475;
		for (int j = 0; j < 25; j++) {
			i = (i * 96 + 42) % 475;
			if (tar == 26 + i) {
				return true;
			}
		}
		return false;
	}

}
