import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class RiceHub {

	static int nP;
	static long budget;
	static int[] pos;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nP = Integer.parseInt(inputData.nextToken());
		inputData.nextToken();
		budget = Long.parseLong(inputData.nextToken());
		pos = new int[nP];
		for (int i = 0; i < nP; i++) {
			pos[i] = Integer.parseInt(reader.readLine());
		}

		int low = 0;
		int high = nP;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (pos(mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		printer.println(low);
		printer.close();
	}

	static boolean pos(int cnt) {
		long cCost = 0;
		int medianD = cnt / 2;
		for (int i = 0; i < cnt; i++) {
			cCost += Math.abs(pos[medianD] - pos[i]);
		}

		long mCost = cCost;

		for (int i = 1; i + cnt <= nP; i++) {
			cCost -= pos[i - 1 + medianD] - pos[i - 1];
			long medianS = pos[i + medianD] - pos[i - 1 + medianD];
			cCost += medianD * medianS;
			cCost -= (cnt - (medianD + 1)) * medianS;
			cCost += pos[i + cnt - 1] - pos[i + medianD];
			mCost = Math.min(mCost, cCost);
		}
		return mCost <= budget;
	}
}
