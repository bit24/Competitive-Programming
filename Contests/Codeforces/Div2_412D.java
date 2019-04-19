import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_412D {

	public static void main(String[] args) throws IOException {
		new Div2_412D().execute();
	}

	static final double[] bracket = new double[] { 1, 1.0 / 2, 1.0 / 4, 1.0 / 8, 1.0 / 16, 1.0 / 32, 0 };

	int[] cBracket = new int[5];
	int[] solvedCnt = new int[5];
	int numP;

	final int addInc = 1;
	final int addCor = 2;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		numP = Integer.parseInt(reader.readLine());

		int[] mTime = new int[5];
		int[] tTime = new int[5];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 5; i++) {
			mTime[i] = Integer.parseInt(inputData.nextToken());
			if (mTime[i] != -1) {
				solvedCnt[i]++;
			}
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 5; i++) {
			tTime[i] = Integer.parseInt(inputData.nextToken());
			if (tTime[i] != -1) {
				solvedCnt[i]++;
			}
		}

		for (int i = 2; i < numP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for(int j = 0; j < 5; j++){
				if (!inputData.nextToken().equals("-1")) {
					solvedCnt[j]++;
				}
			}
		}
		reader.close();

		int[] status = new int[5];
		for (int i = 0; i < 5; i++) {
			if (mTime[i] != -1) {
				if (tTime[i] != -1) {
					if (mTime[i] < tTime[i]) {
						status[i] = addInc;
					} else if (mTime[i] > tTime[i]) {
						status[i] = addCor;
					}
				} else {
					status[i] = addInc;
				}
			}
		}

		double tScore = calcScore(tTime);
		double mScore = calcScore(mTime);

		int tExtra = 0;
		while (mScore <= tScore) {
			updBracket();

			int extra = Integer.MAX_VALUE;
			for (int i = 0; i < 5; i++) {
				if (status[i] == addCor) {
					if (cBracket[i] > 1) {
						int pos = (int) Math.ceil(Math.nextUp(
								(numP * bracket[cBracket[i] - 1] - solvedCnt[i]) / (1 - bracket[cBracket[i] - 1])));
						if (pos < extra) {
							extra = pos;
						}
					}
				} else if (status[i] == addInc) {
					if (cBracket[i] <= 5) {
						int pos = (int) Math.ceil(solvedCnt[i] / bracket[cBracket[i]] - numP);
						if (pos < extra) {
							extra = pos;
						}
					}
				}
			}
			if (extra == Integer.MAX_VALUE) {
				printer.println(-1);
				printer.close();
				return;
			}
			tExtra += extra;

			numP += extra;
			for (int i = 0; i < 5; i++) {
				if (status[i] == addCor) {
					solvedCnt[i] += extra;
				}
			}

			mScore = calcScore(mTime);
			tScore = calcScore(tTime);
		}
		printer.println(tExtra);
		printer.close();
	}

	void updBracket() {
		for (int i = 0; i < 5; i++) {
			double fSolved = ((double) solvedCnt[i]) / numP;
			cBracket[i] = 6;
			for (int j = 1; j <= 6; j++) {
				if (fSolved > bracket[j]) {
					cBracket[i] = j;
					break;
				}
			}
		}
	}

	double calcScore(int[] times) {
		double score = 0;
		for (int i = 0; i < 5; i++) {
			if (times[i] != -1) {
				double fSolved = ((double) solvedCnt[i]) / numP;
				int pValue = 3000;
				for (int j = 1; j <= 6; j++) {
					if (fSolved > bracket[j]) {
						pValue = j * 500;
						break;
					}
				}
				score += (250 - times[i]) * pValue / 250.0;
			}
		}
		return score;
	}

}