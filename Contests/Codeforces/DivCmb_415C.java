import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;

public class DivCmb_415C {

	public static void main(String[] args) throws IOException {
		new DivCmb_415C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		Integer[][] fSets = new Integer[2][];

		int ansL = 0;

		for (int i = 0; i < 2; i++) {
			String nLine = reader.readLine();
			ansL = nLine.length();
			fSets[i] = new Integer[ansL];
			for (int j = 0; j < ansL; j++) {
				fSets[i][j] = nLine.charAt(j) - 'a';
			}
			Arrays.sort(fSets[i]);
		}
		reader.close();

		fSets[0] = Arrays.copyOf(fSets[0], (ansL + 1) / 2);
		fSets[1] = Arrays.copyOfRange(fSets[1], (ansL + 1) / 2, ansL);
		for (int i = 0; i < fSets[1].length / 2; i++) {
			int temp = fSets[1][i];
			fSets[1][i] = fSets[1][fSets[1].length - 1 - i];
			fSets[1][fSets[1].length - 1 - i] = temp;
		}

		@SuppressWarnings("unchecked")
		ArrayDeque<Integer>[] sets = new ArrayDeque[] { new ArrayDeque<Integer>(), new ArrayDeque<Integer>() };

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < fSets[i].length; j++) {
				sets[i].addLast(fSets[i][j]);
			}
		}

		int[] ans = new int[ansL];

		int secSt = 0;

		int nInd = 0;

		while (!sets[0].isEmpty()) {
			if (sets[1].isEmpty()) {
				ans[nInd++] = sets[0].removeFirst();
			} else {
				if (sets[0].peekFirst() < sets[1].peekFirst()) {
					ans[nInd++] = sets[0].removeFirst();
				} else {
					break;
				}

				if (sets[0].isEmpty() || sets[1].peekFirst() > sets[0].peekFirst()) {
					ans[nInd++] = sets[1].removeFirst();
				} else {
					secSt = 1;
					break;
				}
			}
		}

		nInd = ansL - 1;

		if (secSt == 0) {
			while (!sets[1].isEmpty()) {
				ans[nInd--] = sets[0].removeLast();
				ans[nInd--] = sets[1].removeLast();
			}
			if (!sets[0].isEmpty()) {
				ans[nInd--] = sets[0].removeLast();
			}
		}
		else{
			while (!sets[0].isEmpty()) {
				ans[nInd--] = sets[1].removeLast();
				ans[nInd--] = sets[0].removeLast();
			}
			if (!sets[1].isEmpty()) {
				ans[nInd--] = sets[1].removeLast();
			}
		}

		for (int i : ans) {
			printer.print((char) (i + 'a'));
		}
		printer.println();
		printer.close();
	}
}
