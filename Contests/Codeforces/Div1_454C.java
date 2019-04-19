import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_454C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		int[] aList = new int[nV];

		for (int i = 0; i < nV; i++) {
			aList[i] = 1 << i;
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a] |= 1 << b;
			aList[b] |= 1 << a;
		}
		reader.close();

		int nSet = 1 << nV;

		boolean rAll = true;
		for (int i = 0; i < nV; i++) {
			if (aList[i] != nSet - 1) {
				rAll = false;
				break;
			}
		}
		if (rAll) {
			printer.println(0);
			printer.close();
			return;
		}

		int[] reach = new int[nSet];
		Arrays.fill(reach, -1);

		int[] prev = new int[nSet];
		Arrays.fill(prev, -1);

		for (int cV = 0; cV < nV; cV++) {
			int cM = 1 << cV;
			reach[cM] = aList[cV];
			prev[cM] = cV;
		}

		int min = Integer.MAX_VALUE;
		int mSet = -1;

		// cSet of vertices that is valid
		for (int cSet = 1; cSet < nSet; cSet++) {
			if (reach[cSet] == -1) {
				continue;
			}
			int bCnt = Integer.bitCount(cSet);
			if (reach[cSet] == nSet - 1) {
				if (bCnt < min) {
					min = bCnt;
					mSet = cSet;
				}
			}
			int cReach = reach[cSet];
			for (int cV = 0; cV < nV; cV++) {
				int aM = 1 << cV;
				if ((cSet & aM) == 0 && (cReach & (1 << cV)) != 0 && reach[cSet ^ aM] == -1) {
					reach[cSet ^ aM] = cReach | aList[cV];
					prev[cSet ^ aM] = cV;
				}
			}
		}
		printer.println(min);
		for (int cSet = mSet; cSet != 0;) {
			printer.print(prev[cSet] + 1 + " ");
			cSet ^= 1 << prev[cSet];
		}
		printer.println();
		printer.close();
	}

}
