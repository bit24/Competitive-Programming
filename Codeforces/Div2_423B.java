import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_423B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nR = Integer.parseInt(inputData.nextToken());
		int nC = Integer.parseInt(inputData.nextToken());

		int lM = nC;
		int rM = -1;
		int uM = nR;
		int bM = -1;

		int nB = 0;

		for (int i = 0; i < nR; i++) {
			String nLine = reader.readLine();
			for (int j = 0; j < nC; j++) {
				if (nLine.charAt(j) == 'B') {
					if (j < lM) {
						lM = j;
					}
					if (rM < j) {
						rM = j;
					}
					if (i < uM) {
						uM = i;
					}
					if (bM < i) {
						bM = i;
					}
					nB++;
				}
			}
		}
		if (nB == 0) {
			System.out.println(1);
			return;
		}

		int sR = Math.max(rM - lM + 1, bM - uM + 1);

		if (sR > nR || sR > nC) {
			System.out.println(-1);
			return;
		}
		System.out.println(sR * sR - nB);
	}

}
