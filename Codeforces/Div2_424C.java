import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_424C {

	static int nD;
	static int nR;

	static Integer[] oDel;
	static Integer[] rem;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nD = Integer.parseInt(inputData.nextToken());
		nR = Integer.parseInt(inputData.nextToken());

		oDel = new Integer[nD];

		inputData = new StringTokenizer(reader.readLine());
		oDel[0] = Integer.parseInt(inputData.nextToken());
		for (int i = 1; i < nD; i++) {
			oDel[i] = oDel[i - 1] + Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		rem = new Integer[nR];
		for (int i = 0; i < nR; i++) {
			rem[i] = Integer.parseInt(inputData.nextToken());
		}

		Arrays.sort(oDel);
		Arrays.sort(rem);

		int pC = 0;
		for (int i = 0; i < nD; i++) {
			while (i + 1 < nD && oDel[i + 1] == oDel[i]) {
				i++;
			}
			if (isPos(rem[0] - oDel[i], i)) {
				pC++;
			}
		}
		System.out.println(pC);
	}

	static boolean isPos(int sV, int fmp) {
		int nM = 1;
		if (nM == nR) {
			return true;
		}

		for (int i = fmp + 1; i < nD; i++) {
			if (sV + oDel[i] == rem[nM]) {
				nM++;
				if (nM == nR) {
					return true;
				}
			}
		}
		return false;
	}

}
