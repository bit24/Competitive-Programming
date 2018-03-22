import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CowOptics {

	public static void main(String[] args) throws IOException {
		new CowOptics().execute();
	}

	Comparator<Point> sByX = new Comparator<Point>() {
		public int compare(Point o1, Point o2) {
			return o1.x != o2.x ? (o1.x < o2.x ? -1 : 1) : (o1.y < o2.y ? -1 : (o1.y == o2.y ? 0 : 1));
		}
	};

	Comparator<Point> sByY = new Comparator<Point>() {
		public int compare(Point o1, Point o2) {
			return o1.y != o2.y ? (o1.y < o2.y ? -1 : 1) : (o1.x < o2.x ? -1 : (o1.x == o2.x ? 0 : 1));
		}
	};

	Point[] points;
	ArrayList<TreeSet<Point>> byX;
	ArrayList<TreeSet<Point>> byY;

	Point start = new Point(0, 0, -1);
	Point barn;

	int xEnd;
	int yEnd;

	void init() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("optics.in"));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numP = Integer.parseInt(inputData.nextToken());
		barn = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()), -1);

		points = new Point[numP + 2];

		for (int i = 0; i < numP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			points[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					inputData.nextToken().equals("\\") ? 0 : 1);
		}
		reader.close();

		points[numP] = start;
		points[numP + 1] = barn;

		// coordinate compression only y is necessary for the BIT segTree
		Arrays.sort(points, sByX);
		int oX = points[0].x;
		int nX = 2;
		for (int i = 0; i <= numP + 1; i++) {
			if (points[i].x == oX) {
				points[i].x = nX;
			} else {
				oX = points[i].x;
				points[i].x = ++nX;
			}
		}
		Arrays.sort(points, sByY);
		int oY = points[0].y;
		int nY = 2;
		for (int i = 0; i <= numP + 1; i++) {
			if (points[i].y == oY) {
				points[i].y = nY;
			} else {
				oY = points[i].y;
				points[i].y = ++nY;
			}
		}

		byX = new ArrayList<TreeSet<Point>>();
		byY = new ArrayList<TreeSet<Point>>();
		for (int i = 0; i <= nX + 1; i++) {
			byX.add(new TreeSet<Point>(sByX));
		}

		for (int i = 0; i <= nY + 1; i++) {
			byY.add(new TreeSet<Point>(sByY));
		}

		for (Point p : points) {
			byX.get(p.x).add(p);
			byY.get(p.y).add(p);
		}

		xEnd = nX + 1;
		yEnd = nY + 1;
	}

	class Event implements Comparable<Event> {
		int key;
		int value;

		Event(int k, int v) {
			key = k;
			value = v;
		}

		public int compareTo(Event o) {
			int y = o.key;
			return (key < y) ? -1 : ((key == y) ? 0 : 1);
		}
	}

	void execute() throws IOException {
		init();
		ArrayList<Segment> set1 = new ArrayList<Segment>();
		search(barn, 0, set1);
		search(barn, 1, set1);
		search(barn, 2, set1);
		search(barn, 3, set1);

		TreeSet<Segment> barnSet = new TreeSet<Segment>(set1);
		set1 = new ArrayList<Segment>(barnSet);

		ArrayList<Segment> startSet = new ArrayList<Segment>();
		search(start, 0, startSet);

		ArrayList<Segment> cSweep = new ArrayList<Segment>();

		int finalAns = 0;
		for (Segment s : barnSet) {
			if (s.orientation == 0) {
				cSweep.add(s);
			}
		}
		for (Segment s : startSet) {
			if (s.orientation == 1) {
				cSweep.add(s);
			}
		}
		finalAns += sweep0(cSweep);

		cSweep.clear();
		for (Segment s : barnSet) {
			if (s.orientation == 1) {
				cSweep.add(s);
			}
		}
		for (Segment s : startSet) {
			if (s.orientation == 0) {
				cSweep.add(s);
			}
		}

		finalAns += sweep0(cSweep);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("optics.out")));
		printer.println(finalAns);
		printer.close();
	}

	int sweep0(ArrayList<Segment> elements) {
		Collections.sort(elements);
		BIT active = new BIT();

		int sum = 0;
		PriorityQueue<Event> events = new PriorityQueue<Event>();
		for (Segment cSegment : elements) {
			if (cSegment.orientation == 1) {
				int cX = cSegment.axis;
				while ((!events.isEmpty()) && events.peek().key < cX) {
					Event cEvent = events.remove();
					active.delete(cEvent.value);
				}
				sum += active.query(cSegment.start, cSegment.end);
			} else {
				int cX = cSegment.start;
				while ((!events.isEmpty()) && events.peek().key < cX) {
					Event cEvent = events.remove();
					active.delete(cEvent.value);
				}

				active.add(cSegment.axis);
				events.add(new Event(cSegment.end, cSegment.axis));
			}
		}
		return sum;
	}

	class BIT {
		int[] elements = new int[yEnd + 1];

		void add(int i) {
			while (i <= yEnd) {
				elements[i]++;
				i += (i & -i);
			}
		}

		void delete(int i) {
			while (i <= yEnd) {
				elements[i]--;
				i += (i & -i);
			}
		}

		int query(int i) {
			int sum = 0;
			while (i > 0) {
				sum += elements[i];
				i -= (i & -i);
			}
			return sum;
		}

		int query(int l, int r) {
			if (l == 1) {
				return query(r);
			} else {
				return query(r) - query(l - 1);
			}
		}
	}

	static final int[][] out = new int[][] { { 3, 2, 1, 0 }, { 2, 3, 0, 1 } };

	void search(Point cP, int cD, ArrayList<Segment> segments) {
		while (cP != null) {
			Point nP = null;
			if (cD == 0) {
				nP = byX.get(cP.x).higher(cP);
			} else if (cD == 1) {
				nP = byX.get(cP.x).lower(cP);
			} else if (cD == 2) {
				nP = byY.get(cP.y).higher(cP);
			} else if (cD == 3) {
				nP = byY.get(cP.y).lower(cP);
			}

			if (nP != null) {
				if (cP.x == nP.x && Math.abs(cP.y - nP.y) > 1) {
					segments.add(new Segment(1, cP.x, cP.y, nP.y));
				} else if (cP.y == nP.y && Math.abs(cP.x - nP.x) > 1) {
					segments.add(new Segment(0, cP.y, cP.x, nP.x));
				}
				if (nP.state != -1) {
					cD = out[nP.state][cD];
					cP = nP;
				} else {
					break;
				}
			} else {
				if (cD == 0 && Math.abs(yEnd - cP.y) > 1) {
					segments.add(new Segment(1, cP.x, cP.y, yEnd));
				}
				if (cD == 1 && Math.abs(cP.y - 1) > 1) {
					segments.add(new Segment(1, cP.x, cP.y, 1));
				}
				if (cD == 2 && Math.abs(xEnd - cP.y) > 1) {
					segments.add(new Segment(0, cP.y, cP.x, xEnd));
				}
				if (cD == 3 && Math.abs(cP.y - 1) > 1) {
					segments.add(new Segment(0, cP.y, cP.x, 1));
				}
				break;
			}
		}
	}

	class Point {
		int x;
		int y;
		int state;

		Point(int x, int y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	class Segment implements Comparable<Segment> {
		int orientation;
		int axis;
		int start;
		int end;

		Segment(int orientation, int axis, int a, int b) {
			this.orientation = orientation;
			this.axis = axis;
			if (a < b) {
				start = a;
				end = b;
			} else {
				start = b;
				end = a;
			}
			start++;
			end--;
		}

		// compare by x, 0 first
		public int compareTo(Segment o) {
			if (orientation != o.orientation) {
				if (orientation == 0) {
					if (start != o.axis) {
						int y = o.axis;
						return (start < y) ? -1 : ((start == y) ? 0 : 1);
					}
					return -1;
				} else {
					if (axis != o.start) {
						int y = o.start;
						return (axis < y) ? -1 : ((axis == y) ? 0 : 1);
					}
					return 1;
				}
			} else {
				if (orientation == 0) {
					if (start != o.start) {
						int y = o.start;
						return (start < y) ? -1 : ((start == y) ? 0 : 1);
					}
					int y = o.axis;
					return (axis < y) ? -1 : ((axis == y) ? 0 : 1);
				} else {
					if (axis != o.axis) {
						int y = o.axis;
						return (axis < y) ? -1 : ((axis == y) ? 0 : 1);
					}
					int y = o.start;
					return (start < y) ? -1 : ((start == y) ? 0 : 1);
				}
			}
		}
	}
}