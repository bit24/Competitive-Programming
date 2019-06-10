import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CowRun {

	public static int nR;
	public static long mod;
	public static long distBnd;
	public static String bString;
	public static long[][] cards;
	static ArrayList<boolean[]> solutions = new ArrayList<boolean[]>();

	public static String currentResult = "";
	public static int finR;
	public static long cDist = 0L;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		nR = Integer.parseInt(inputData.nextToken());
		mod = Long.parseLong(inputData.nextToken());
		distBnd = Long.parseLong(inputData.nextToken());

		cards = new long[nR][8];
		bString = reader.readLine();

		for (int i = 0; i < nR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 8; j++) {
				cards[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}
		reader.close();

		search();
		System.out.println(currentResult);
	}

	public static long getDistance(boolean[] jDec, boolean[] bDec) {
		long tDist = cDist;
		for (int i = 0; i < jDec.length; i++) {
			int fCInd = 0;
			if (jDec[i]) {
				fCInd += 4;
			}
			if (bDec[i]) {
				fCInd += 2;
			}

			int cRound = i + finR;

			tDist += tDist * (cards[cRound][fCInd] % mod);
			tDist += cards[cRound][fCInd + 1];
			tDist = tDist % mod;
		}
		return Math.min(tDist, mod - tDist);
	}

	public static String values(boolean[] items) {
		String stuff = "";
		for (boolean current : items) {
			stuff += (current ? "B" : "T") + " ";
		}
		return stuff;
	}

	public static void search() {
		// exit condition
		if (finR == nR) {
			return;
		}

		int remR = nR - finR;
		int numC = (int) Math.pow(2, remR);

		boolean isPossible = true;
		John:
		for (int i = 0; i < numC / 2; i++) {
			boolean[] jDec = getChoice(i);

			for (int j = 0; j < numC; j++) {
				boolean[] bDec = getChoice(j);

				long eDist = getDistance(jDec, bDec);
				if (eDist > distBnd) {
					isPossible = false;
					break John;
				}
			}
		}
		int topCardIndex = 0;

		if (isPossible) {
			currentResult += "B";
			topCardIndex += 4;
		} else {
			// currentResult == "T"
			currentResult += "T";
		}

		if (bString.substring(finR, finR + 1).equals("B")) {
			topCardIndex += 2;
		}

		cDist += cDist * (cards[finR][topCardIndex] % mod);
		cDist += cards[finR][topCardIndex + 1];
		cDist = cDist % mod;

		finR++;
		search();
	}

	public static boolean[] getChoice(int bSet) {
		int remR = nR - finR;
		boolean[] decisions = new boolean[remR];

		for (int i = remR - 1; i >= 0; i--) {
			if ((bSet & 1) != 1) {
				decisions[i] = true;
			}
			bSet >>= 1;
		}
		return decisions;
	}

}