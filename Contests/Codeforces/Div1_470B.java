import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_470B {

	static int nD;
	static long[] red;
	static long[] rPSum;

	static long[] dArray;
	static long[] lOver;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nD = Integer.parseInt(reader.readLine());
		long[] start = new long[nD];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nD; i++) {
			start[i] = Integer.parseInt(inputData.nextToken());
		}

		red = new long[nD];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nD; i++) {
			red[i] = Integer.parseInt(inputData.nextToken());
		}

		rPSum = new long[nD + 1];
		rPSum[0] = red[0];
		for (int i = 1; i < nD; i++) {
			rPSum[i] = rPSum[i - 1] + red[i];
		}
		rPSum[nD] = rPSum[nD - 1] + 1_000_000_000;

		long sPSum = 0;

		dArray = new long[nD + 1];
		lOver = new long[nD + 1];

		for (int i = 0; i < nD; i++) {
			if(start[i] != 0) {
				int fDay = binSearch(sPSum + start[i]);
				dArray[i]++;
				dArray[fDay]--;
				lOver[fDay] += fDay != 0 ? start[i] - (rPSum[fDay - 1] - sPSum) : start[i];
			}
			sPSum += red[i];
		}

		int nAct = 0;
		for (int i = 0; i < nD; i++) {
			nAct += dArray[i];
			long sum = red[i] * nAct + lOver[i];
			printer.print(sum + " ");
		}
		printer.println();
		printer.close();
	}

	static int binSearch(long amount) {
		int low = 0;
		int high = nD;

		while (low != high) {
			int mid = (low + high) / 2;

			if (rPSum[mid] < amount) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return low;
	}
}
