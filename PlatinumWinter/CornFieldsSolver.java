import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CornFieldsSolver {

	static int numR;
	static int numC;

	static int[] current;
	static int[] next;

	static int[] bad;

	static ArrayList<Integer> validStates;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numR = Integer.parseInt(inputData.nextToken());
		numC = Integer.parseInt(inputData.nextToken());

		bad = new int[numR];
		for (int i = 0; i < numR; i++) {
			inputData = new StringTokenizer(reader.readLine());

			int bitSet = 0;
			for (int j = 0; j < numC; j++) {
				if (inputData.nextToken().charAt(0) == '0') {
					bitSet |= (1 << j);
				}
			}
			bad[i] = bitSet;
		}
		reader.close();

		validStates = new ArrayList<Integer>();

		for (int state = 0; state < (1 << numC); state++) {
			if ((state & (state << 1)) == 0) {
				validStates.add(state);
			}
		}

		current = new int[(1 << numC)];
		next = new int[(1 << numC)];

		for (int sState : validStates) {
			if ((sState & bad[0]) == 0) {
				current[sState] = 1;
			}
		}

		for (int cRow = 0; cRow + 1 < numR; cRow++) {
			for (int cState : validStates) {
				push(cRow, cState);
			}
			int[] temp = current;
			current = next;
			next = temp;
			Arrays.fill(next, 0);
		}

		int ans = 0;
		for (int eState : validStates) {
			ans += current[eState];
			ans %= 100_000_000;
		}
		System.out.println(ans);
	}

	static void push(int cRow, int cState) {
		for (int nState : validStates) {
			if ((cState & nState) != 0 || ((nState & bad[cRow + 1]) != 0)) {
				continue;
			}
			next[nState] += current[cState];
			next[nState] %= 100_000_000;
		}
	}

}
