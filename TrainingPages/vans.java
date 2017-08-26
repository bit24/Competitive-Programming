import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

/*ID: eric.ca1
LANG: JAVA
TASK: vans
*/

public class vans {

	public static void main(String[] args) throws IOException {
		new vans().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("vans.in"));
		length = Integer.parseInt(reader.readLine());
		reader.close();

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("vans.out")));
		printer.println(getAnswer().multiply(new BigInteger("2")).toString());
		printer.close();
	}

	int length;
	int width = 4;

	int none = 0;
	int ncNeutrals = 1;
	int ccNeutrals = 2;

	BigInteger[][][] nextStates;
	BigInteger[][][] currentStates;

	void init() {
		currentStates = new BigInteger[3][4][3];
		nextStates = new BigInteger[3][4][3];
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 4; b++) {
				for (int c = 0; c < 3; c++) {
					currentStates[a][b][c] = BigInteger.ZERO;
					nextStates[a][b][c] = BigInteger.ZERO;
				}
			}
		}
		currentStates[0][3][none] = BigInteger.ONE;
		currentStates[0][3][ccNeutrals] = BigInteger.ONE;
	}

	BigInteger getAnswer() {
		if (length <= 1) {
			return BigInteger.ZERO;
		}
		init();
		for (int i = 1; i < length - 1; i++) {
			for (int plus = 0; plus < 3; plus++) {
				for (int minus = 0; minus < 4; minus++) {
					for (int hasNeutrals = 0; hasNeutrals < 3; hasNeutrals++) {
						pushTransitions(plus, minus, hasNeutrals);
					}
				}
			}

			currentStates = nextStates;
			nextStates = new BigInteger[3][4][3];
			for (int a = 0; a < 3; a++) {
				for (int b = 0; b < 4; b++) {
					for (int c = 0; c < 3; c++) {
						nextStates[a][b][c] = BigInteger.ZERO;
					}
				}
			}
		}
		return computeFinalAnswer();
	}

	BigInteger computeFinalAnswer() {
		BigInteger sum = BigInteger.ZERO;
		sum = sum.add(currentStates[0][3][ncNeutrals]);
		sum = sum.add(currentStates[0][3][none]);
		return sum;
	}

	void pushTransitions(int plus, int minus, int hasNeutrals) {
		BigInteger currentValue = currentStates[plus][minus][hasNeutrals];
		if (currentValue.equals(BigInteger.ZERO)) {
			return;
		}

		boolean[] used = new boolean[4];
		used[plus] = true;
		used[minus] = true;
		int unused1 = 0;
		while (used[unused1]) {
			unused1++;
		}
		int unused2 = 3;
		while (used[unused2]) {
			unused2--;
		}

		assert (plus < minus);
		if (hasNeutrals == ccNeutrals) {
			nextStates[plus][minus][ccNeutrals] = currentValue.add(nextStates[plus][minus][ccNeutrals]);
			assert (plus == 0 && minus == 3);
			nextStates[plus][minus][none] = currentValue.add(nextStates[plus][minus][none]);

		} else if (hasNeutrals == ncNeutrals) {
			// continue current state
			nextStates[plus][minus][ncNeutrals] = currentValue.add(nextStates[plus][minus][ncNeutrals]);

			// use a neutral
			if (Math.abs(plus - unused1) == 1) {
				nextStates[unused2][minus][none] = currentValue.add(nextStates[unused2][minus][none]);
			}
			if (Math.abs(plus - unused2) == 1) {
				nextStates[unused1][minus][none] = currentValue.add(nextStates[unused1][minus][none]);
			}
			if (Math.abs(minus - unused1) == 1) {
				nextStates[plus][unused2][none] = currentValue.add(nextStates[plus][unused2][none]);
			}
			if (Math.abs(minus - unused2) == 1) {
				nextStates[plus][unused1][none] = currentValue.add(nextStates[plus][unused1][none]);
			}
		} else {

			// introduce a neutral
			if (unused2 - unused1 == 1) {
				nextStates[plus][minus][ncNeutrals] = currentValue.add(nextStates[plus][minus][ncNeutrals]);
			}

			// adjacent plus and minus => both ends
			if (minus - plus == 1) {
				nextStates[0][3][none] = currentValue.add(nextStates[0][3][none]);
			}
			// spaced apart => unused ones
			if (minus - plus == 2) {
				nextStates[unused1][unused2][none] = currentValue.add(nextStates[unused1][unused2][none]);
			}
			// opposite corners
			if (minus - plus == 3) {
				nextStates[0][1][none] = currentValue.add(nextStates[0][1][none]);
				nextStates[1][2][none] = currentValue.add(nextStates[1][2][none]);
				nextStates[2][3][none] = currentValue.add(nextStates[2][3][none]);
			}
		}

	}

}
