import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CountingFriends {

	public static void main(String[] args) throws IOException {
		new CountingFriends().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fcount.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fcount.out")));
		int nP = Integer.parseInt(reader.readLine()) + 1;
		int[] deg = new int[nP + 2];
		int sum = 0;
		for (int i = 1; i <= nP; i++) {
			sum += deg[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		ArrayList<Integer> pos = new ArrayList<Integer>();
		cLoop:
		for (int rV = 1; rV <= nP; rV++) {
			if (((sum + deg[rV]) & 1) != 0) {
				continue;
			}
			int[] cnt = new int[500];
			for (int iV = 1; iV <= nP; iV++) {
				if (iV == rV) {
					continue;
				}
				if (deg[iV] < 0 || 500 <= deg[iV]) {
					continue cLoop;
				}
				cnt[deg[iV]]++;
			}

			int lI = 499;
			while (true) {
				while (lI > 0 && cnt[lI] == 0) {
					lI--;
				}
				if (lI == 0) {
					break;
				}
				int nCnt = lI;
				cnt[lI]--;

				int cI = lI;
				while (nCnt > 0) {
					if (nCnt > cnt[cI]) {
						nCnt -= cnt[cI];
						cI--;
						if (cI <= 0) {
							continue cLoop;
						}
					} else {
						cnt[cI] -= nCnt;
						cnt[cI - 1] += nCnt;
						nCnt = 0;
						cI++;
					}
				}
				while (cI <= lI) {
					cnt[cI - 1] += cnt[cI];
					cnt[cI] = 0;
					cI++;
				}
			}
			pos.add(rV);
		}
		printer.println(pos.size());
		for (int i : pos) {
			printer.println(i);
		}
		printer.close();
	}

}
