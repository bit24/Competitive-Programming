import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class HillWalk {

	public static void main(String[] args) throws IOException {
		new HillWalk().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("hillwalk.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("hillwalk.out")));
		int nS = Integer.parseInt(reader.readLine());

		Segment[] oQ = new Segment[nS];
		Segment[] eQ = new Segment[nS];
		for (int i = 0; i < nS; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			int t = Integer.parseInt(inputData.nextToken());
			oQ[i] = eQ[i] = new Segment(l, r, t, b);
		}
		reader.close();

		Arrays.sort(oQ, byL);
		Arrays.sort(eQ, byR);

		TreeSet<Segment> aSet = new TreeSet<Segment>(byH);

		int oI = 0;
		int eI = 0;

		int cI = 0;
		int cH = 0;
		int cnt = 0;
		while (true) {
			while (eI < nS && eQ[eI].right <= cI) {
				aSet.remove(eQ[eI]);
				eI++;
			}
			while (oI < nS && oQ[oI].left <= cI) {
				if (oQ[oI].right > cI) {
					aSet.add(oQ[oI]);
				}
				oI++;
			}
			Segment nxt = aSet.floor(new Segment(cI - 1, cI, cH, cH));
			if (nxt == null) {
				printer.println(cnt);
				printer.close();
				return;
			}

			cI = nxt.right;
			cH = nxt.top;
			cnt++;
		}
	}

	class Segment {
		final int left;
		final int right;
		final int top;
		final int bottom;

		Segment(int left, int right, int top, int bottom) {
			this.left = left;
			this.right = right;
			this.top = top;
			this.bottom = bottom;
		}
	}

	static Comparator<Segment> byL = new Comparator<Segment>() {
		public int compare(Segment s1, Segment s2) {
			return Integer.compare(s1.left, s2.left);
		}
	};

	static Comparator<Segment> byR = new Comparator<Segment>() {
		public int compare(Segment s1, Segment s2) {
			return Integer.compare(s1.right, s2.right);
		}
	};

	static Comparator<Segment> byH = new Comparator<Segment>() {
		public int compare(Segment s1, Segment s2) {
			if (s1 == s2) {
				return 0;
			}
			int qX = Math.min(s1.right, s2.right);
			double y1 = ((double) (s1.top - s1.bottom) / (s1.right - s1.left)) * (qX - s1.left) + s1.bottom;
			double y2 = ((double) (s2.top - s2.bottom) / (s2.right - s2.left)) * (qX - s2.left) + s2.bottom;
			return Double.compare(y1, y2);
		}
	};

}
