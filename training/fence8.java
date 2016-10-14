import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/*ID: eric.ca1
LANG: JAVA
TASK: fence8
*/

public class fence8 {

	public static void main(String[] args) throws IOException {
		new fence8().execute();
	}

	void execute() throws IOException {
		input();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fence8.out")));
		int max = findMax();
		printer.println(max + 1);
		printer.close();
		/*
		 * for (int i = 0; i < numReq; i++) { System.out.println(i + " " + (dfs(i, 0))); }
		 */
	}

	int numReq, numSup;
	int[] req, sup;

	void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fence8.in"));
		numSup = Integer.parseInt(reader.readLine());
		sup = new int[numSup];
		for (int i = 0; i < numSup; i++) {
			sup[i] = Integer.parseInt(reader.readLine());
		}
		numReq = Integer.parseInt(reader.readLine());
		req = new int[numReq];
		for (int i = 0; i < numReq; i++) {
			req[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		Arrays.sort(sup);
		Arrays.sort(req);
		prefixSum();
	}

	int[] pSumReq;
	int supSum = 0;

	void prefixSum() {
		pSumReq = new int[numReq];

		pSumReq[0] = req[0];
		for (int i = 1; i < numReq; i++) {
			pSumReq[i] = pSumReq[i - 1] + req[i];
		}

		for (int i = 0; i < numSup; i++) {
			supSum += sup[i];
		}
	}

	int findMax() {
		int low = -1;
		int high = numReq - 1;

		while (low < high) {
			// this causes it to always round up
			int med = (low + high + 1) / 2;
			if(med == -1){
				break;
			}
			if (dfs(med, 0, supSum - pSumReq[med])) {
				low = med;
			} else {
				high = med - 1;
			}
		}
		assert (low == high);
		return low;
	}

	boolean dfs(int reqInd, int supIndL, int wHBound) {

		if (wHBound < 0) {
			return false;
		}

		if (reqInd == 0) {
			for (int supInd = supIndL; supInd < numSup; supInd++) {
				if (sup[supInd] >= req[reqInd]) {
					return true;
				}
			}
		}

		for (int supInd = supIndL; supInd < numSup; supInd++) {
			if (sup[supInd] >= req[reqInd]) {
				sup[supInd] -= req[reqInd];
				int newWHBound = wHBound;
				if (sup[supInd] < req[0]) {
					newWHBound -= sup[supInd];
				}

				if (req[reqInd - 1] == req[reqInd]) {
					if (dfs(reqInd - 1, supInd, newWHBound)) {
						sup[supInd] += req[reqInd];
						return true;
					}
				} else {
					if (dfs(reqInd - 1, 0, newWHBound)) {
						sup[supInd] += req[reqInd];
						return true;
					}
				}
				sup[supInd] += req[reqInd];
			}
		}
		return false;
	}
}
