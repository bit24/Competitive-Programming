import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Board {

	public static void main(String[] args) throws IOException {
		new Board().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String s1 = reader.readLine();
		String s2 = reader.readLine();

		Loc l1 = new Loc();
		for (int i = 0; i < s1.length(); i++) {
			char cur = s1.charAt(i);
			if (cur == '1') {
				l1.m1();
			}
			if (cur == '2') {
				l1.m2();
			}
			if (cur == 'U') {
				l1.mU();
			}
			if (cur == 'L') {
				l1.mL();
			}
			if (cur == 'R') {
				l1.mR();
			}
		}

		Loc l2 = new Loc();
		for (int i = 0; i < s2.length(); i++) {
			char cur = s2.charAt(i);
			if (cur == '1') {
				l2.m1();
			}
			if (cur == '2') {
				l2.m2();
			}
			if (cur == 'U') {
				l2.mU();
			}
			if (cur == 'L') {
				l2.mL();
			}
			if (cur == 'R') {
				l2.mR();
			}
		}

		l1.flush();
		l2.flush();

		for (int i = 0; i < Math.min(l1.ind, l2.ind); i++) {
			if (l1.array[i] < l2.array[i]) {
				break;
			}
			if (l1.array[i] > l2.array[i]) {
				Loc temp = l1;
				l1 = l2;
				l2 = temp;
				break;
			}
		}

		int ans = Integer.MAX_VALUE;
		int hDist = 0;
		for (int cH = 0; cH <= Math.min(l1.ind, l2.ind) && hDist < 300_000; cH++) {
			ans = Math.min(ans, l1.ind + l2.ind - 2 * cH + hDist);
			hDist = hDist * 2 + 1;
			if (l1.array[cH] == 1) {
				hDist--;
			}
			if (l2.array[cH] == 0) {
				hDist--;
			}
		}
		printer.println(ans);
		printer.close();
	}

	class Loc {
		int[] array = new int[100_001];
		int ind;

		void m1() {
			array[ind++] = 0;
		}

		void m2() {
			array[ind++] = 1;
		}

		void mL() {
			array[ind - 1]--;
		}

		void mR() {
			array[ind - 1]++;
		}

		void prop() {
			if (ind >= 2) {
				if (array[ind - 1] < 0) {
					array[ind - 2] += (array[ind - 1] - 1) / 2;
				} else if (array[ind - 1] > 1) {
					array[ind - 2] += array[ind - 1] / 2;
				}
				array[ind - 1] %= 2;
				if (array[ind - 1] == -1) {
					array[ind - 1] = 1;
				}
			}
		}

		void mU() {
			prop();
			array[--ind] = 0;
		}

		void flush() {
			for (int i = ind; i >= 2; i--) {
				if (array[i - 1] < 0) {
					array[i - 2] += (array[i - 1] - 1) / 2;
				} else if (array[i - 1] > 1) {
					array[i - 2] += array[i - 1] / 2;
				}
				array[i - 1] %= 2;
				if (array[i - 1] == -1) {
					array[i - 1] = 1;
				}
			}
		}
	}
}
