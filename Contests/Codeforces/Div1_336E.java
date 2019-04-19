import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_336E {

	int N;
	double P;
	double Q;
	int K;

	double[] sl;
	double[] yI;

	public static void main(String[] args) throws IOException {
		new Div1_336E().main();
	}

	double dMaxDist;

	PrintWriter printer;

	public void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		P = Integer.parseInt(inputData.nextToken()) / 1000.0;
		Q = Integer.parseInt(inputData.nextToken()) / 1000.0;
		K = Integer.parseInt(inputData.nextToken());

		sl = new double[N];
		yI = new double[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			sl[i] = Integer.parseInt(inputData.nextToken()) / 1000.0;
			yI[i] = Integer.parseInt(inputData.nextToken()) / 1000.0;
		}

		events = new Event[N * 2];
		start = new int[N * 2];
		BIT = new int[N * 2 + 1];

		nxt = new int[N * 2];
		prev = new int[N * 2];

		double maxDist = searchMaxDist();
		dMaxDist = maxDist;
		double ans = sumIntInt(maxDist);
		printer.println(ans);
		printer.close();
	}

	double searchMaxDist() {
		double low = 0;
		double high = 1e10;

		int nReps = 100;
		while (nReps-- > 0) {
			double mid = (high + low) / 2;
			if (cIntInt(mid) < K) {
				low = mid;
			} else {
				high = mid;
			}
		}
		if (low < 1e-4) {
			low = 0;
		}
		return low;
	}

	int nE;
	Event[] events;

	void fCircleInt(double r) {
		nE = 0;
		for (int i = 0; i < N; i++) {
			double cSl = sl[i];
			double cYI = yI[i];
			double a = cSl * cSl + 1;
			double b = -2 * P + 2 * cSl * cYI - 2 * cSl * Q;
			double c = P * P + cYI * cYI + Q * Q - r * r - 2 * Q * cYI;
			double dsc = b * b - 4 * a * c;

			if (dsc > 1e-8) {
				double sqrt = Math.sqrt(dsc);
				double x1 = (-b + sqrt) / (2 * a);
				double x2 = (-b - sqrt) / (2 * a);
				double y1 = cSl * x1 + cYI;
				double y2 = cSl * x2 + cYI;

				double a1 = Math.atan2(y1 - Q, x1 - P);
				double a2 = Math.atan2(y2 - Q, x2 - P);
				events[nE++] = new Event(a1, i);
				events[nE++] = new Event(a2, i);
			}
		}
		Arrays.sort(events, 0, nE);
	}

	class Event implements Comparable<Event> {
		double a;
		int i;

		Event(double a, int i) {
			this.a = a;
			this.i = i;
		}

		public int compareTo(Event o) {
			return Double.compare(a, o.a);
		}
	}

	int[] start;

	long cIntInt(double r) {
		fCircleInt(r);
		Arrays.fill(start, -1);
		Arrays.fill(BIT, 0);

		long cnt = 0;
		int nA = 0;
		for (int i = 0; i < nE; i++) {
			int ind = events[i].i;
			if (start[ind] == -1) {
				start[ind] = i;
				nA++;
				update(i, 1);
			} else {
				nA--;
				update(start[ind], -1);
				cnt += nA - query(start[ind]);
			}
		}
		return cnt;
	}

	double computeDist(int i1, int i2) {
		double x = (yI[i2] - yI[i1]) / (sl[i1] - sl[i2]);
		double y = sl[i1] * x + yI[i1];
		return Math.sqrt((P - x) * (P - x) + (Q - y) * (Q - y));
	}

	int last = -1;
	int[] nxt;
	int[] prev;

	void add(int ind) {
		if (last != -1) {
			nxt[last] = ind;
		}
		prev[ind] = last;
		last = ind;
	}

	void delete(int ind) {
		if (ind == last) {
			last = prev[ind];
			if (prev[ind] != -1) {
				nxt[prev[ind]] = -1;
			}
			return;
		}
		if (prev[ind] != -1) {
			nxt[prev[ind]] = nxt[ind];
		}
		if (nxt[ind] != -1) {
			prev[nxt[ind]] = prev[ind];
		}
	}

	String log = "";

	double sumIntInt(double r) {
		long nIntInt = cIntInt(r);
		log += r;
		log += " ";
		log += nIntInt;
		if (nIntInt > K) {
			return 0;
		}

		last = -1;
		Arrays.fill(start, -1);
		Arrays.fill(nxt, -1);
		Arrays.fill(prev, -1);

		double sum = 0;
		for (int i = 0; i < nE; i++) {
			int ind = events[i].i;
			if (start[ind] == -1) {
				start[ind] = i;
				add(i);
			} else {
				for (int cur = nxt[start[ind]]; cur != -1; cur = nxt[cur]) {
					sum += computeDist(ind, events[cur].i);
				}
				delete(start[ind]);
			}
		}
		return sum + (K - nIntInt) * r;
	}

	int[] BIT;

	void update(int ind, int delta) {
		ind++;
		while (ind < BIT.length) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	int query(int ind) {
		ind++;
		int sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}
}
