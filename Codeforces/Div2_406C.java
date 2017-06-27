import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_406C {

	static final int UND = 0;
	static final int WIN = 1;
	static final int LOSE = 2;

	static int numO;

	static int[][] mSet;
	static int[][] status;
	static int[][] numPos;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		numO = Integer.parseInt(reader.readLine());

		mSet = new int[2][];
		for (int i = 0; i < 2; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int setS = Integer.parseInt(inputData.nextToken());
			mSet[i] = new int[setS];

			for (int j = 0; j < setS; j++) {
				mSet[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		status = new int[2][numO];
		numPos = new int[2][numO];

		Arrays.fill(numPos[0], mSet[0].length);
		Arrays.fill(numPos[1], mSet[1].length);

		setStatus(0, 0, LOSE);
		setStatus(1, 0, LOSE);

		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < numO; j++) {
				if (status[i][j] == WIN) {
					printer.print("Win ");
				} else if (status[i][j] == UND) {
					printer.print("Loop ");
				} else {
					printer.print("Lose ");
				}
			}
			printer.println();
		}
		printer.close();
	}

	static void setStatus(int cPlay, int cState, int cStatus) {
		status[cPlay][cState] = cStatus;
		int oPlay = cPlay ^ 1;

		if (cStatus == WIN) {
			for (int k : mSet[oPlay]) {
				int depS = (cState - k + numO) % numO;
				if(depS == 0){
					continue;
				}
				if (status[oPlay][depS] == UND && --numPos[oPlay][depS] == 0) {
					setStatus(oPlay, depS, LOSE);
				}
			}
		} else {
			for (int k : mSet[oPlay]) {
				int depS = (cState - k + numO) % numO;
				if(depS == 0){
					continue;
				}
				if (status[oPlay][depS] == UND) {
					setStatus(oPlay, depS, WIN);
				}
			}
		}
	}
}
