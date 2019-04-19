import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_46C {

	public static void main(String[] args) throws IOException {
		new Edu_46C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nS = Integer.parseInt(reader.readLine());

		Sig[] sigs = new Sig[nS * 2];
		for (int i = 0; i < nS; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			sigs[i * 2] = new Sig(Long.parseLong(inputData.nextToken()), 1);
			sigs[i * 2 + 1] = new Sig(Long.parseLong(inputData.nextToken()) + 1, -1);
		}

		Arrays.sort(sigs);

		long[] cnt = new long[nS + 1];

		int cAct = 0;
		for (int i = 0; i < nS * 2; i++) {
			if (i != 0 && sigs[i].i != sigs[i - 1].i) {
				cnt[cAct] += sigs[i].i - sigs[i - 1].i;
			}
			cAct += sigs[i].op;
		}
		for (int i = 1; i <= nS; i++) {
			printer.print(cnt[i] + " ");
		}
		printer.println();
		printer.close();
	}

	class Sig implements Comparable<Sig> {
		long i;
		int op;

		Sig(long i, int op) {
			this.i = i;
			this.op = op;
		}

		public int compareTo(Sig o) {
			return Long.compare(i, o.i);
		}
	}
}