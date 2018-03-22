import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;


/*ID: eric.ca1
LANG: JAVA
TASK: milk4
*/
public class milk4 {
	static int total;
	static int numP;
	static int[] pails;
	static int INF = Integer.MAX_VALUE / 2;
	static int[] minPails;
	static int[] lastPail;
	static int[] previous;

	public static void main(String[] args) throws IOException {
		input();
		solve();
		ArrayList<Integer> answerSet = backTrack();
		
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("milk4.out")));
		printer.print(answerSet.size());
		
		for(int e : answerSet){
			printer.print(" " + e);
		}
		printer.println();
		printer.close();
	}
	
	public static ArrayList<Integer> backTrack() {
		ArrayList<Integer> answerSet = new ArrayList<Integer>();

		int cAmount = total;
		while (cAmount > 0) {
			answerSet.add(pails[lastPail[cAmount]]);
			cAmount = previous[cAmount];
		}

		return answerSet;
	}
	
	public static boolean isBetterSet(int nAmount, int oAmount) {
		if (minPails[nAmount] != minPails[oAmount]) {
			return minPails[nAmount] < minPails[oAmount];
		}

		while (nAmount > 0 && oAmount > 0) {
			if (lastPail[nAmount] != lastPail[oAmount]) {
				return pails[lastPail[nAmount]] < pails[lastPail[oAmount]];
			}
			nAmount = previous[nAmount];
			oAmount = previous[oAmount];
		}

		return false;
	}

	public static void solve() {
		minPails = new int[total + 1];
		lastPail = new int[total + 1];
		previous = new int[total + 1];

		minPails[0] = 0;
		lastPail[0] = -1;
		previous[0] = -1;

		for (int i = 1; i <= total; i++) {
			minPails[i] = INF;
			lastPail[i] = -1;
			previous[i] = -1;
		}

		// force last pail to be cPail
		for (int cPail = 0; cPail < numP; cPail++) {
			int[] forcedMin = new int[total + 1];
			int[] forcedPrev = new int[total + 1];

			for (int i = 0; i <= total; i++) {
				forcedMin[i] = INF;
				forcedPrev[i] = -1;
			}

			for (int cAmount = pails[cPail]; cAmount <= total; cAmount++) {
				int pAmount = cAmount - pails[cPail];

				if (forcedMin[pAmount] < INF) {
					forcedMin[cAmount] = forcedMin[pAmount];
					forcedPrev[cAmount] = forcedPrev[pAmount];
				}

				if (minPails[pAmount] < INF) {
					if (forcedMin[cAmount] >= INF || isBetterSet(pAmount, forcedPrev[cAmount])) {
						forcedMin[cAmount] = minPails[pAmount] + 1;
						forcedPrev[cAmount] = pAmount;
					}
				}
			}
			
			for (int cAmount = pails[cPail]; cAmount <= total; cAmount++) {
				if(forcedMin[cAmount] <= minPails[cAmount]){
					minPails[cAmount] = forcedMin[cAmount];
					lastPail[cAmount] = cPail;
					previous[cAmount] = forcedPrev[cAmount];
				}
			}
		}
	}

	public static void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("milk4.in"));
		total = Integer.parseInt(reader.readLine());
		numP = Integer.parseInt(reader.readLine());
		pails = new int[numP];
		for (int i = 0; i < numP; i++) {
			pails[i] = Integer.parseInt(reader.readLine());
		}
		Arrays.sort(pails);
		for (int i = 0; i < numP / 2; i++) {
			int temp = pails[i];
			pails[i] = pails[numP - 1 - i];
			pails[numP - 1 - i] = temp;
		}
		reader.close();
	}

}
