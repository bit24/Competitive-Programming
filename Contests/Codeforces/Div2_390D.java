import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_390D {

	public static void main(String[] args) throws IOException {
		new Div2_390D().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numS = Integer.parseInt(inputData.nextToken());

		PriorityQueue<DataPair> active = new PriorityQueue<DataPair>();

		Segment[] segments = new Segment[numE];

		for (int i = 0; i < numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			segments[i] = new Segment(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					i + 1);
		}
		Arrays.sort(segments);

		int ans = 0;
		int fLeft = 0;
		int fRight = 0;

		for (Segment cSegment : segments) {

			int cID = cSegment.ID;

			int cLeft = cSegment.left;

			int right = cSegment.right;
			active.add(new DataPair(right, cID));
			if (active.size() > numS) {
				active.remove();
			}

			if (active.size() == numS) {
				int cRight = active.peek().value;
				if (cLeft <= cRight) {
					if (cRight - cLeft + 1 > ans) {
						ans = cRight - cLeft + 1;
						fLeft = cLeft;
						fRight = cRight;
					}
				}
			}
		}

		printer.println(ans);

		if (ans != 0) {
			for (int i = 0; i < numE && numS > 0; i++) {
				if (segments[i].left <= fLeft && fRight <= segments[i].right) {
					printer.print(segments[i].ID + " ");
					numS--;
				}
			}
		} else {
			for (int i = 1; i <= numS; i++) {
				printer.print(i + " ");
			}
		}
		printer.println();

		printer.close();
	}

	class DataPair implements Comparable<DataPair> {
		int value;
		int index;

		public int compareTo(DataPair o) {
			return Integer.compare(value, o.value);
		}

		DataPair(int a, int b) {
			value = a;
			index = b;
		}
	}

	class Segment implements Comparable<Segment> {
		int left;
		int right;
		int ID;

		Segment(int l, int r, int ID) {
			left = l;
			right = r;
			this.ID = ID;
		}

		public int compareTo(Segment o) {
			if (left != o.left) {
				return Integer.compare(left, o.left);
			}
			return Integer.compare(right, o.right);
		}
	}

}
