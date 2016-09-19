import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: picture
*/

public class picture {

	public static void main(String[] args) throws IOException {
		new picture().completeProblem();
	}

	int numRectangles;
	int[] left;
	int[] right;
	int[] top;
	int[] bottom;

	AASegment[] vSegments;
	AASegment[] hSegments;

	public int computeAnswer() {
		int vAligned = singleAxisDifference(vSegments);
		int hAligned = singleAxisDifference(hSegments);
		return vAligned + hAligned;
	}

	public void computeFormatted() {
		vSegments = new AASegment[numRectangles * 2];
		hSegments = new AASegment[numRectangles * 2];

		for (int cIndex = 0; cIndex < numRectangles; cIndex++) {
			vSegments[cIndex * 2] = new AASegment(left[cIndex], bottom[cIndex], top[cIndex], true);
			vSegments[cIndex * 2 + 1] = new AASegment(right[cIndex], bottom[cIndex], top[cIndex], false);
			hSegments[cIndex * 2] = new AASegment(bottom[cIndex], left[cIndex], right[cIndex], true);
			hSegments[cIndex * 2 + 1] = new AASegment(top[cIndex], left[cIndex], right[cIndex], false);
		}
	}

	public void completeProblem() throws IOException {
		input();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("picture.out")));
		printer.println(computeAnswer());
		printer.close();
	}

	public void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("picture.in"));

		numRectangles = Integer.parseInt(reader.readLine());
		left = new int[numRectangles];
		right = new int[numRectangles];
		top = new int[numRectangles];
		bottom = new int[numRectangles];

		for (int i = 0; i < numRectangles; i++) {
			StringTokenizer lineData = new StringTokenizer(reader.readLine());
			left[i] = Integer.parseInt(lineData.nextToken()) + 10000;
			bottom[i] = Integer.parseInt(lineData.nextToken()) + 10000;
			right[i] = Integer.parseInt(lineData.nextToken()) + 10000;
			top[i] = Integer.parseInt(lineData.nextToken()) + 10000;
		}
		reader.close();
		computeFormatted();
	}

	public int singleAxisDifference(AASegment[] segments) {
		int[] rectangleCount = new int[20001];
		int count = 0;
		Arrays.sort(segments, new AlignedAxisComparator());

		for (int cIndex = 0; cIndex < segments.length; cIndex++) {
			AASegment cSegment = segments[cIndex];
			if (cSegment.isStart) {
				for (int i = cSegment.e1; i < cSegment.e2; i++) {
					if (rectangleCount[i] == 0) {
						count++;
					}
					rectangleCount[i]++;
				}
			} else {
				for (int i = cSegment.e1; i < cSegment.e2; i++) {
					rectangleCount[i]--;
					if (rectangleCount[i] == 0) {
						count++;
					}
				}
			}
		}
		return count;
	}

	class AASegment {
		int axisValue;
		int e1;
		int e2;
		boolean isStart;

		AASegment(int a, int b, int c, boolean d) {
			axisValue = a;
			e1 = b;
			e2 = c;
			assert (e1 < e2);
			isStart = d;
		}
	}

	class AlignedAxisComparator implements Comparator<AASegment> {
		public int compare(AASegment seg1, AASegment seg2) {
			if (seg1.axisValue != seg2.axisValue) {
				return seg1.axisValue < seg2.axisValue ? -1 : 1;
			} else if (seg1.isStart != seg2.isStart) {
				return seg1.isStart ? -1 : 1;
			} else if (seg1.e1 != seg2.e1) {
				return seg1.e1 < seg2.e1 ? -1 : 1;
			} else if (seg1.e2 != seg2.e2) {
				return seg1.e2 < seg2.e2 ? -1 : 1;
			} else {
				return 0;
			}
		}
	}

}
