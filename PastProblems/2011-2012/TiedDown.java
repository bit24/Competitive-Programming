import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TiedDown {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tied.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("tied.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nPl = Integer.parseInt(inputData.nextToken());
		int nPt = Integer.parseInt(inputData.nextToken()) + 1;

		int[] pl = new int[nPl];

		inputData = new StringTokenizer(reader.readLine());

		int plX = Integer.parseInt(inputData.nextToken());
		pl[0] = Integer.parseInt(inputData.nextToken());

		for (int i = 1; i < nPl; i++) {
			inputData = new StringTokenizer(reader.readLine());
			inputData.nextToken();
			pl[i] = Integer.parseInt(inputData.nextToken());
		}

		Arrays.sort(pl);

		int[] ptX = new int[nPt];
		int[] ptY = new int[nPt];

		for (int i = 0; i < nPt; i++) {
			inputData = new StringTokenizer(reader.readLine());
			ptX[i] = Integer.parseInt(inputData.nextToken());
			ptY[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		ArrayList<Double> crossY = new ArrayList<Double>();

		int interI = 0;
		while (interI < nPt && ptX[interI] == plX) {
			interI++;
		}
		for (; interI < nPt - 1;) {
			if ((ptX[interI] - plX) * (ptX[interI + 1] - plX) < 0) {
				crossY.add(ptY[interI] + (ptY[interI + 1] - ptY[interI]) * (plX - ptX[interI])
						/ (double) (ptX[interI + 1] - ptX[interI]));
				interI++;
			} else if (ptX[interI + 1] == plX) {
				int bef = interI;
				interI++;
				while (interI + 1 < nPt && ptX[interI] == plX) {
					interI++;
				}
				if (interI != nPt && (ptX[interI] - plX) * (ptX[bef] - plX) < 0) {
					crossY.add((double) ptY[bef + 1]);
				}
			} else {
				interI++;
			}
		}

		int lRmv = nPl;
		for (int bSet = 1; bSet < (1 << nPl); bSet++) {
			int nS = Integer.bitCount(bSet);
			int[] sPl = new int[nS + 1];
			int cPos = 0;
			for (int i = 0; i < nPl; i++) {
				if ((bSet & (1 << i)) != 0) {
					sPl[cPos++] = pl[i];
				}
			}
			sPl[cPos++] = Integer.MAX_VALUE;

			ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
			for (double cCross : crossY) {
				for (int j = 0; j <= nS; j++) {
					if (cCross < sPl[j]) {
						if (!stack.isEmpty() && stack.peekLast() == j) {
							stack.removeLast();
						} else {
							stack.add(j);
						}
						break;
					}
				}
			}
			if (stack.isEmpty()) {
				lRmv = Math.min(lRmv, nPl - nS);
			}
		}
		printer.println(lRmv);
		printer.close();
	}

}
