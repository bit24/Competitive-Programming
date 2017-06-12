import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_418D {

	public static void main(String[] args) throws IOException {
		new Div2_418D().execute();
	}

	int numC;
	Circle[] circles;

	ArrayList<Integer>[] chldn;

	double[][] dp;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		numC = Integer.parseInt(reader.readLine());

		circles = new Circle[numC + 1];
		Event[] events = new Event[numC * 2 + 1];

		for (int i = 1; i <= numC; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			circles[i] = new Circle(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()));
			events[i * 2 - 1] = new Event(i, BEGIN);
			events[i * 2] = new Event(i, END);
		}
		reader.close();

		Arrays.sort(events, 1, events.length);

		int[] parent = new int[numC + 1];
		chldn = new ArrayList[numC + 1];
		for (int i = 1; i <= numC; i++) {
			chldn[i] = new ArrayList<Integer>();
		}

		// sweep line
		TreeSet<ExtPoint> actExt = new TreeSet<ExtPoint>();

		for (int eI = 1; eI <= numC * 2; eI++) {
			Event cEvent = events[eI];
			int cCircle = cEvent.sInd;

			ExtPoint cTop = new ExtPoint(cCircle, TOP);
			ExtPoint cBottom = new ExtPoint(cCircle, BOTTOM);

			if (cEvent.state == BEGIN) {
				ExtPoint higher = actExt.higher(cTop);
				if (higher != null && higher.oInd != 0) {
					if (higher.half == TOP) {
						parent[cCircle] = higher.oInd;
						chldn[higher.oInd].add(cCircle);
					} else {
						parent[cCircle] = parent[higher.oInd];
						if (parent[higher.oInd] != 0) {
							chldn[parent[higher.oInd]].add(cCircle);
						}
					}
				}
				actExt.add(cTop);
				actExt.add(cBottom);
			} else {
				actExt.remove(cTop);
				actExt.remove(cBottom);
			}
		}

		dp = new double[2][numC + 1];

		double ans = 0;
		for (int i = 1; i <= numC; i++) {
			if (parent[i] == 0) {
				computeDP(i, 1);
				ans += dp[ADD][i];
			}
		}
		System.out.printf("%.10f", ans);
	}

	final int ADD = 1;
	final int SUB = 0;

	void computeDP(int cV, int dep) {
		for (int chld : chldn[cV]) {
			computeDP(chld, dep + 1);
		}

		@SuppressWarnings("unused")
		int eff1 = SUB;
		int eff2 = (dep & 1) ^ 1;

		// case 1: cV belongs to part 1
		double sum1 = -circles[cV].area;
		for (int chldn : chldn[cV]) {
			sum1 += dp[ADD][chldn];
		}

		// case 2: cV belongs to part 2
		double sum2 = eff2 == ADD ? circles[cV].area : -circles[cV].area;
		for (int chldn : chldn[cV]) {
			sum2 += dp[SUB][chldn];
		}
		dp[SUB][cV] = Math.max(sum1, sum2);

		// case 1: cV belongs to part 1
		eff1 = ADD;
		eff2 = dep & 1;

		// case 1: cV belongs to part 1
		sum1 = circles[cV].area;
		for (int chldn : chldn[cV]) {
			sum1 += dp[SUB][chldn];
		}

		// case 2: cV belongs to part 2
		sum2 = eff2 == ADD ? circles[cV].area : -circles[cV].area;
		for (int chldn : chldn[cV]) {
			sum2 += dp[ADD][chldn];
		}
		dp[ADD][cV] = Math.max(sum1, sum2);
	}

	final int BEGIN = -1;
	final int END = 1;

	class Event implements Comparable<Event> {
		int sInd;
		int state;

		Event(int sInd, int state) {
			this.sInd = sInd;
			this.state = state;
		}

		public int compareTo(Event oth) {
			Circle tSponsor = circles[sInd];
			Circle oSponsor = circles[oth.sInd];
			int tP = tSponsor.x + state * tSponsor.r;
			int oP = oSponsor.x + oth.state * oSponsor.r;
			if (tP != oP) {
				return tP < oP ? -1 : 1;
			}

			if (state != oth.state) {
				return state == END ? -1 : 1;
			}

			if (tSponsor.r != oSponsor.r) {
				return tSponsor.r > oSponsor.r ? -1 : 1;
			}
			return Integer.compare(sInd, oth.sInd);
		}
	}

	class Circle {
		int x;
		int y;
		int r;
		double area;

		Circle(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
			area = Math.PI * (long) r * r;
		}
	}

	final int TOP = 1;
	final int BOTTOM = -1;

	// the ExtPoint class holds either the circle's topmost or bottommost point
	// sorting of the ExtPoint class is done very tactically: regular sorting except when the circles to whom the
	// extreme points belong don't overlap, in which case the extreme points are sorted by the relative positions of
	// the corresponding centers
	// this method of sorting effectively enables us to "straighten" an otherwise irregular placement of circles
	class ExtPoint implements Comparable<ExtPoint> {
		int oInd;
		int half;

		ExtPoint(int oInd, int half) {
			this.oInd = oInd;
			this.half = half;
		}

		public int compareTo(ExtPoint oth) {
			Circle tOwner = circles[oInd];
			Circle oOwner = circles[oth.oInd];
			long xDist = tOwner.x - oOwner.x;
			long yDist = tOwner.y - oOwner.y;
			long rSum = tOwner.r + oOwner.r;
			if (xDist * xDist + yDist * yDist >= rSum * rSum) { // circles do not overlap
				return Integer.compare(tOwner.y, oOwner.y);
			}

			long tExt = tOwner.y + half * tOwner.r;
			long oExt = oOwner.y + oth.half * oOwner.r;

			if (tExt != oExt) {
				return tExt < oExt ? -1 : 1;
			}

			if (half == TOP && oth.half == TOP) {
				return Integer.compare(tOwner.r, oOwner.r);
			} else if (half == BOTTOM && oth.half == BOTTOM) {
				return -Integer.compare(tOwner.r, oOwner.r);
			} else if (half == TOP) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
