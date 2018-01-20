import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_443A {

	static final int UNSET = 0;
	static final int SET = 1;
	static final int NORM = 2;
	static final int INVT = 3;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nL = Integer.parseInt(reader.readLine());
		int[] cState = new int[10];
		Arrays.fill(cState, NORM);

		while (nL-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			String op = inputData.nextToken();
			int cNum = Integer.parseInt(inputData.nextToken());

			if (op.equals("&")) {
				for (int i = 0; i < 10; i++) {
					if ((cNum & (1 << i)) == 0) {
						cState[i] = UNSET;
					}
				}
			} else if (op.equals("|")) {
				for (int i = 0; i < 10; i++) {
					if ((cNum & (1 << i)) != 0) {
						cState[i] = SET;
					}
				}
			} else {
				for (int i = 0; i < 10; i++) {
					if ((cNum & (1 << i)) != 0) {
						cState[i] ^= 1;
					}
				}
			}
		}

		printer.println(3);

		int andV = (1 << 10) - 1;
		int orV = 0;
		int xorV = 0;

		for (int i = 0; i < 10; i++) {
			if (cState[i] == UNSET) {
				andV ^= 1 << i;
			} else if (cState[i] == SET) {
				orV ^= 1 << i;
			} else if (cState[i] == INVT) {
				xorV ^= 1 << i;
			}
		}
		printer.println("& " + andV);
		printer.println("| " + orV);
		printer.println("^ " + xorV);
		printer.close();
	}

}
