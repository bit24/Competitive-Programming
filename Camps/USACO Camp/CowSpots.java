import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CowSpots {

	public static void main(String[] args) throws IOException {
		new CowSpots().main();
	}

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int N = Integer.parseInt(reader.readLine());
		r = new long[N];
		h = new long[N];
		k = new long[N];

		Event[] events = new Event[N * 2];

		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			h[i] = Integer.parseInt(inputData.nextToken());
			k[i] = Integer.parseInt(inputData.nextToken());
			r[i] = Integer.parseInt(inputData.nextToken());

			events[i * 2] = new Event(i, true);
			events[i * 2 + 1] = new Event(i, false);
		}

		Arrays.sort(events);

		TreeSet<Sect> ranges = new TreeSet<>();

		int[] par = new int[N];

		for (int i = 0; i < 2 * N; i++) {
			cX = events[i].x;
			if (events[i].left) {
				Sect nSect = new Sect(events[i].cI, false, events[i].cI);
				Sect cPre = ranges.lower(nSect);

				if (cPre != null) {
					par[nSect.cI] = cPre.own;
					ranges.add(nSect);
					ranges.add(new Sect(events[i].cI, true, cPre.own));
				} else {
					par[nSect.cI] = -1;
					ranges.add(nSect);
					ranges.add(new Sect(events[i].cI, true, -1));
				}
			} else {
				ass(ranges.remove(new Sect(events[i].cI, false, -1)));
				ass(ranges.remove(new Sect(events[i].cI, true, -1)));
			}
		}

		ass(ranges.isEmpty());
		
		aList = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			aList[i] = new ArrayList<>();
		}

		ArrayList<Integer> roots = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			if (par[i] == -1) {
				roots.add(i);
			} else {
				aList[par[i]].add(i);
			}
		}
		area = new long[N];
		for (int i = 0; i < N; i++) {
			area[i] = r[i] * r[i];
		}

		for (int i : roots) {
			dfs(i, Integer.MAX_VALUE);
		}

		for (int i = 0; i < N; i++) {
			printer.println(area[i]);
		}

		printer.close();
	}

	void ass(boolean inp) {
		if (!inp) {
			throw new RuntimeException();
		}
	}

	void dfs(int cV, int earl) {
		for (int aV : aList[cV]) {
			dfs(aV, Math.min(cV, earl));
		}

		if (earl != Integer.MAX_VALUE) {
			if (earl < cV) {
				area[cV] = 0;
			} else {
				area[earl] -= r[cV] * r[cV];
			}
		}
	}

	long[] r;
	long[] h;
	long[] k;

	long cX;

	ArrayList<Integer>[] aList;

	long[] area;

	class Event implements Comparable<Event> {
		long x;
		boolean left;
		int cI;

		Event(int cI, boolean left) {
			this.cI = cI;
			this.left = left;
			this.x = left ? h[cI] - r[cI] : h[cI] + r[cI];
		}

		public int compareTo(Event o) {
			if(x != o.x) {
				return Long.compare(x, o.x);
			}
			else {
				return -Boolean.compare(left, o.left);
			}
		}
	}

	class Sect implements Comparable<Sect> {
		int cI;
		boolean up;

		int own;

		Sect(int cI, boolean up, int own) {
			this.cI = cI;
			this.up = up;
			this.own = own;
		}

		public int compareTo(Sect o) {
			if (cI == o.cI && up == o.up) {
				return 0;
			}
			
			if(cI == o.cI) {
				return !up ? -1 : 1;
			}

			double calc1 = up ? k[cI] + Math.sqrt(r[cI] * r[cI] - (cX - h[cI]) * (cX - h[cI]))
					: k[cI] - Math.sqrt(r[cI] * r[cI] - (cX - h[cI]) * (cX - h[cI]));
			int oCI = o.cI;

			double calc2 = o.up ? k[oCI] + Math.sqrt(r[oCI] * r[oCI] - (cX - h[oCI]) * (cX - h[oCI]))
					: k[oCI] - Math.sqrt(r[oCI] * r[oCI] - (cX - h[oCI]) * (cX - h[oCI]));

			return Double.compare(calc1, calc2);
		}
	}
}