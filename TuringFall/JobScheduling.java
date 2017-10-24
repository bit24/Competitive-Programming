import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class JobScheduling {

	static int nD;
	static int mD;
	static int[] nReq;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nD = Integer.parseInt(inputData.nextToken());
		mD = Integer.parseInt(inputData.nextToken());
		int tNR = Integer.parseInt(inputData.nextToken());
		nReq = new int[nD + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < tNR; i++) {
			nReq[Integer.parseInt(inputData.nextToken())]++;
		}

		int low = 1;
		int high = tNR;
		while (low != high) {
			int mid = (low + high) / 2;
			if (isPos(mid)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		System.out.println(low);
	}

	static boolean isPos(int nM) {
		int[] cReq = Arrays.copyOf(nReq, nD + 1);
		int lD = 1;
		for (int cD = 1; cD <= nD; cD++) {
			int cM = nM;
			while (cM > 0 && lD <= cD) {
				int dlt = Math.min(cM, cReq[lD]);
				cM -= dlt;
				cReq[lD] -= dlt;
				while (lD <= nD && cReq[lD] == 0) {
					lD++;
				}
			}
			if (cD - lD >= mD) {
				return false;
			}
		}
		return lD == nD + 1;
	}

}
