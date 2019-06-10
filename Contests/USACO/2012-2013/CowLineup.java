import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CowLineup {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("lineup.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("lineup.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int nE = Integer.parseInt(inputData.nextToken());
		int mT = Integer.parseInt(inputData.nextToken()) + 1;

		int[] type = new int[nE];

		for (int i = 0; i < nE; i++) {
			type[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();

		int tCnt = 0;

		int lI = 0;

		int ans = 0;

		for (int cI = 0; cI < nE; cI++) {
			if (!count.containsKey(type[cI])) {
				count.put(type[cI], 1);
				tCnt++;
			} else {
				count.put(type[cI], count.get(type[cI]) + 1);
			}
			while (tCnt > mT) {
				int currentCount = count.get(type[lI]);
				if (currentCount > ans) {
					ans = currentCount;
				}

				count.put(type[lI], count.get(type[lI]) - 1);
				if (count.get(type[lI]) == 0) {
					count.remove(type[lI]);
					tCnt--;
				}
				lI++;
			}
			assert (tCnt <= mT);
			int cCnt = count.get(type[lI]);
			if (cCnt > ans) {
				ans = cCnt;
			}
		}

		while (lI < nE) {
			int cCnt = count.get(type[lI]);
			if (cCnt > ans) {
				ans = cCnt;
			}

			count.put(type[lI], count.get(type[lI]) - 1);
			if (count.get(type[lI]) == 0) {
				count.remove(type[lI]);
				tCnt--;
			}
			lI++;
		}

		printer.println(ans);
		printer.close();
	}
}