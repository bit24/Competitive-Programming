import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*ID: eric.ca1
LANG: JAVA
TASK: rect1
*/

public class rect1 {

	public static void main(String[] args) throws IOException {
		new rect1().execute();
	}

	void execute() throws IOException {
		input();
		sweepLeftRight();

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("rect1.out")));
		for (int colorI = 1; colorI <= 2500; colorI++) {
			if (count[colorI] != 0) {
				printer.println(colorI + " " + count[colorI]);
			}
		}
		printer.close();
	}

	void sweepLeftRight() {
		int nxtIndex = 0;

		TreeSet<Event> vEvents = new TreeSet<Event>();

		while (nxtIndex < hEvents.length) {
			int cColumn = hEvents[nxtIndex].position;
			while (nxtIndex < hEvents.length && hEvents[nxtIndex].position == cColumn) {
				Event cEvent = hEvents[nxtIndex];
				if (cEvent.eventType == LEFT) {
					vEvents.add(new Event(bottom[cEvent.rectangle], cEvent.rectangle, BOTTOM));
					vEvents.add(new Event(top[cEvent.rectangle], cEvent.rectangle, TOP));
				} else {
					vEvents.remove(new Event(bottom[cEvent.rectangle], cEvent.rectangle, BOTTOM));
					vEvents.remove(new Event(top[cEvent.rectangle], cEvent.rectangle, TOP));
				}
				nxtIndex++;
			}
			if (!(nxtIndex < hEvents.length)) {
				break;
			}

			int length = hEvents[nxtIndex].position - cColumn;

			TreeSet<Integer> activeSet = new TreeSet<Integer>();

			Event cEvent = vEvents.first();
			while (true) {
				int cPosition = cEvent.position;
				while (cEvent != null && cEvent.position == cPosition) {
					if (cEvent.eventType == BOTTOM) {
						activeSet.add(cEvent.rectangle);
					} else {
						activeSet.remove(cEvent.rectangle);
					}
					cEvent = vEvents.higher(cEvent);
				}
				if (cEvent == null) {
					break;
				}

				count[color[activeSet.last()]] += length * (cEvent.position - cPosition);
			}
		}
	}

	int maxX;
	int maxY;
	int numRectangles;

	int[] left;
	int[] right;
	int[] top;
	int[] bottom;
	int[] color;
	int[] count = new int[2501];

	Event[] hEvents;

	void input() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("rect1.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		maxX = Integer.parseInt(inputData.nextToken());
		maxY = Integer.parseInt(inputData.nextToken());
		numRectangles = Integer.parseInt(inputData.nextToken());

		left = new int[numRectangles + 1];
		right = new int[numRectangles + 1];
		top = new int[numRectangles + 1];
		bottom = new int[numRectangles + 1];
		color = new int[numRectangles + 1];

		hEvents = new Event[(numRectangles + 1) * 2];

		left[0] = 0;
		bottom[0] = 0;
		right[0] = maxX;
		top[0] = maxY;
		color[0] = 1;
		hEvents[0] = new Event(left[0], 0, LEFT);
		hEvents[1] = new Event(right[0], 0, RIGHT);

		for (int i = 1; i <= numRectangles; i++) {
			inputData = new StringTokenizer(reader.readLine());
			left[i] = Integer.parseInt(inputData.nextToken());
			bottom[i] = Integer.parseInt(inputData.nextToken());
			right[i] = Integer.parseInt(inputData.nextToken());
			top[i] = Integer.parseInt(inputData.nextToken());
			color[i] = Integer.parseInt(inputData.nextToken());

			hEvents[i * 2] = new Event(left[i], i, LEFT);
			hEvents[i * 2 + 1] = new Event(right[i], i, RIGHT);
		}
		reader.close();

		Arrays.sort(hEvents);
	}

	int LEFT = 0;
	int RIGHT = 1;
	int TOP = 2;
	int BOTTOM = 3;

	class Event implements Comparable<Event> {
		int position;
		int rectangle;
		int eventType;

		Event(int index, int priority, int eventType) {
			this.position = index;
			this.rectangle = priority;
			this.eventType = eventType;
		}

		public int compareTo(Event o) {
			if (position != o.position) {
				return position < o.position ? -1 : 1;
			}
			if (eventType != o.eventType) {
				return eventType < o.eventType ? -1 : 1;
			}
			if (rectangle != o.rectangle) {
				return rectangle < o.rectangle ? -1 : 1;
			}
			return 0;
		}
	}
}
