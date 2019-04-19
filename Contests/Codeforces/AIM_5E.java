import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class AIM_5E {

	public static void main(String[] args) throws IOException {
		new AIM_5E().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nQ = Integer.parseInt(reader.readLine());
		int nP = 0;
		HashMap<Long, ArrayList<Frac>> groups = new HashMap<>();

		HashMap<Frac, Integer> minuend = new HashMap<>();

		while (nQ-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int t = Integer.parseInt(inputData.nextToken());
			long x = Integer.parseInt(inputData.nextToken());
			long y = Integer.parseInt(inputData.nextToken());
			long hyp = x * x + y * y;
			Frac cFrac = new Frac(y, x);

			if (t == 1) {
				nP++;
				ArrayList<Frac> cGroup = groups.get(hyp);
				if (cGroup == null) {
					cGroup = new ArrayList<>();
					groups.put(hyp, cGroup);
				}

				for (Frac o : cGroup) {
					Frac key = new Frac(o.oN + cFrac.oN, o.oD + cFrac.oD);
					Integer pCnt = minuend.get(key);
					minuend.put(key, pCnt == null ? 2 : pCnt + 2);
				}

				Integer pCnt = minuend.get(cFrac);
				minuend.put(cFrac, pCnt == null ? 1 : pCnt + 1);

				cGroup.add(cFrac);
			} else if (t == 2) {
				nP--;
				ArrayList<Frac> cGroup = groups.get(hyp);

				ass(cGroup.remove(cFrac));

				for (Frac o : cGroup) {
					Frac key = new Frac(o.oN + cFrac.oN, o.oD + cFrac.oD);
					Integer pCnt = minuend.get(key);
					ass(pCnt >= 2);
					if (pCnt > 2) {
						minuend.put(key, pCnt - 2);
					} else {
						minuend.remove(key);
					}
				}

				Integer pCnt = minuend.get(cFrac);
				ass(pCnt >= 1);
				if (pCnt > 1) {
					minuend.put(cFrac, pCnt - 1);
				} else {
					minuend.remove(cFrac);
				}

			} else {
				Integer minus = minuend.get(cFrac);
				int ans = nP - (minus == null ? 0 : minus);
				printer.println(ans);
			}
		}
		printer.close();
	}
	
	void ass(boolean inp) {
		if(!inp) {
			throw new RuntimeException();
		}
	}

	class Frac implements Comparable<Frac> {
		long oN;
		long oD;
		long n;
		long d;

		Frac(long n, long d) {
			oN = n;
			oD = d;
			this.n = n;
			this.d = d;
			long t;
			while (n != 0) {
				t = n;
				n = d % n;
				d = t;
			}
			this.n /= d;
			this.d /= d;
		}

		// assumes positive d
		public int compareTo(Frac o) {
			long x = n * o.d;
			long y = o.n * d;
			return (x < y) ? -1 : ((x == y) ? 0 : 1);
		}

		public boolean equals(Object o) {
			Frac oF = (Frac) o;
			return n == oF.n && d == oF.d;
		}

		public int hashCode() {
			return (int) (n * 3 + d);
		}
	}
}