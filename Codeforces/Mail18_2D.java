import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail18_2D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		// long sTime = System.currentTimeMillis();

		// BufferedReader reader = new BufferedReader(new FileReader("textoup"));
		int N = Integer.parseInt(reader.readLine());
		String[] st = new String[N];
		String[] end = new String[N];

		for (int i = 0; i < N; i++) {
			st[i] = reader.readLine();
		}
		for (int i = 0; i < N; i++) {
			end[i] = reader.readLine();
		}

		String pat = null;
		String rep = null;

		int[] sI = new int[N];
		int[] eI = new int[N];
		Arrays.fill(sI, -1);

		for (int i = 0; i < N; i++) {
			int cSI = 0;
			while (cSI < st[i].length() && st[i].charAt(cSI) == end[i].charAt(cSI)) {
				cSI++;
			}
			int cEI = st[i].length() - 1;
			while (cEI >= 0 && st[i].charAt(cEI) == end[i].charAt(cEI)) {
				cEI--;
			}

			if (cSI != st[i].length()) {
				sI[i] = cSI;
				eI[i] = cEI;

				if (pat == null) {
					pat = st[i].substring(cSI, cEI + 1);;
					rep = end[i].substring(cSI, cEI + 1);;
				} else {

					for (int j = 0; j + cSI <= cEI; j++) {
						if (st[i].charAt(j + cSI) != pat.charAt(j) || end[i].charAt(j + cSI) != rep.charAt(j)) {
							printer.println("NO");
							printer.close();
							return;
						}
					}
				}
			}
		}
		StringBuilder front = new StringBuilder();
		StringBuilder back = new StringBuilder();

		eLoop:
		while (true) {
			char aChar = ' ';
			for (int i = 0; i < N; i++) {
				if (sI[i] == -1) {
					continue;
				}
				if (sI[i] == 0) {
					break eLoop;
				}
				if (aChar == ' ') {
					aChar = st[i].charAt(sI[i] - 1);
				} else {
					if (aChar != st[i].charAt(sI[i] - 1) || aChar != end[i].charAt(sI[i] - 1)) {
						break eLoop;
					}
				}
				sI[i]--;
			}
			if (aChar == ' ') {
				break;
			}
			front.append(aChar);
		}

		eLoop:
		while (true) {
			char aChar = ' ';
			for (int i = 0; i < N; i++) {
				if (sI[i] == -1) {
					continue;
				}
				if (eI[i] == st[i].length() - 1) {
					break eLoop;
				}
				if (aChar == ' ') {
					aChar = st[i].charAt(eI[i] + 1);
				} else {
					if (aChar != st[i].charAt(eI[i] + 1) || aChar != end[i].charAt(eI[i] + 1)) {
						break eLoop;
					}
				}
				eI[i]++;
			}
			if (aChar == ' ') {
				break;
			}
			back.append(aChar);
		}

		front.reverse();
		pat = front.toString() + pat + back.toString();
		rep = front.toString() + rep + back.toString();

		Pattern pJAVA = Pattern.compile(pat);

		for (int i = 0; i < N; i++) {
			if (!pJAVA.matcher(st[i]).replaceFirst(rep).equals(end[i])) {
				printer.println("NO");
				printer.close();
				return;
			}
		}

		printer.println("YES");
		printer.println(pat);
		printer.println(rep);
		printer.close();
	}
}
