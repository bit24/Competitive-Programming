import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div2_387D {

	public static void main(String[] args) throws IOException {
		new Div2_387D().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int maxC = Integer.parseInt(inputData.nextToken());

		boolean[] isCold = new boolean[numE];

		int numCold = 0;
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			if (Integer.parseInt(inputData.nextToken()) < 0) {
				isCold[i] = true;
				numCold++;
			}
		}
		if (numCold > maxC) {
			System.out.println(-1);
			return;
		}
		maxC -= numCold;

		int swapCount = 0;
		if (isCold[0]) {
			swapCount++;
		}

		for (int i = 1; i < numE; i++) {
			if (isCold[i - 1] != isCold[i]) {
				swapCount++;
			}
		}

		ArrayList<Integer> switches = new ArrayList<Integer>();

		int count = -1;
		for (int i = 1; i < numE; i++) {
			if (count != -1 && !isCold[i]) {
				count++;
				continue;
			}

			if (isCold[i - 1] && (!isCold[i])) {
				count = 1;
				continue;
			}

			if (count != -1 && isCold[i]) {
				switches.add(count);
				count = -1;
			}
		}

		Collections.sort(switches);

		int min1 = swapCount;
		if (count != -1 && count <= maxC) {
			int cLeft = maxC - count;
			min1--;

			for (int i = 0; i < switches.size(); i++) {
				if (switches.get(i) <= cLeft) {
					cLeft -= switches.get(i);
					min1 -= 2;
				}
			}

		}

		int min2 = swapCount;
		int cLeft = maxC;
		for (int i = 0; i < switches.size(); i++) {
			if (switches.get(i) <= cLeft) {
				cLeft -= switches.get(i);
				min2 -= 2;
			}
		}

		System.out.println(Math.min(min1, min2));
	}

}
