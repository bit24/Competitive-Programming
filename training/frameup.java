import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class frameup {
	static int height;
	static int width;
	static int[][] elements;
	static boolean[] isPresent;
	static int numPresent;
	static PrintWriter printer;
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static ArrayList<String> answers = new ArrayList<String>();
	
	/*ID: eric.ca1
	LANG: JAVA
	TASK: frameup
	*/

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("frameup.in"));
		printer = new PrintWriter(new BufferedWriter(new FileWriter("frameup.out")));
		StringTokenizer inputLine1 = new StringTokenizer(reader.readLine());
		height = Integer.parseInt(inputLine1.nextToken());
		width = Integer.parseInt(inputLine1.nextToken());

		elements = new int[height][width];

		for (int i = 0; i < height; i++) {
			String inputLine = reader.readLine();
			for (int j = 0; j < width; j++) {
				if (inputLine.charAt(j) == '.') {
					elements[i][j] = -1;
				} else {
					elements[i][j] = inputLine.charAt(j) - 'A';
				}
			}
		}
		reader.close();

		isPresent = new boolean[26];

		int[] leftMost = new int[26];
		Arrays.fill(leftMost, width - 1);

		int[] rightMost = new int[26];

		int[] highest = new int[26];
		Arrays.fill(highest, height - 1);

		int[] lowest = new int[26];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int reference = elements[i][j];
				if (reference == -1) {
					continue;
				}

				isPresent[reference] = true;

				leftMost[reference] = Math.min(leftMost[reference], j);
				rightMost[reference] = Math.max(rightMost[reference], j);
				highest[reference] = Math.min(highest[reference], i);
				lowest[reference] = Math.max(lowest[reference], i);
			}
		}
		ArrayList<LinkedHashSet<Integer>> aList = new ArrayList<LinkedHashSet<Integer>>();

		for (int i = 0; i < 26; i++) {
			aList.add(new LinkedHashSet<Integer>());
		}

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int topMost = elements[i][j];
				if (topMost == -1) {
					continue;
				}

				for (int otherFrame = 0; otherFrame < 26; otherFrame++) {
					if (!isPresent[otherFrame] || otherFrame == topMost) {
						continue;
					}

					if (leftMost[otherFrame] == j && lowest[otherFrame] >= i && highest[otherFrame] <= i) {
						aList.get(topMost).add(otherFrame);
					}
					if (rightMost[otherFrame] == j && lowest[otherFrame] >= i && highest[otherFrame] <= i) {
						aList.get(topMost).add(otherFrame);
					}

					if (highest[otherFrame] == i && leftMost[otherFrame] <= j && rightMost[otherFrame] >= j) {
						aList.get(topMost).add(otherFrame);
					}
					if (lowest[otherFrame] == i && leftMost[otherFrame] <= j && rightMost[otherFrame] >= j) {
						aList.get(topMost).add(otherFrame);
					}
					// left, right, up, and downs
				}
			}
		}
		// DAG complete
		for (boolean present : isPresent) {
			if (present) {
				numPresent++;
			}
		}

		int[] timesReferenced = new int[26];
		for (LinkedHashSet<Integer> subList : aList) {
			for (int i : subList) {
				timesReferenced[i]++;
			}
		}
		
		for(int i = 0; i < 26; i++){
			frameup.aList.add(new ArrayList<Integer>(aList.get(i)));
			Collections.sort(frameup.aList.get(i));
		}

		findOrdering(timesReferenced, new LinkedList<Integer>());
		Collections.sort(answers);
		for(String ans : answers){
			printer.println(ans);
		}
		
		printer.close();
	}

	// if timesReferenced == -1, already removed
	public static void findOrdering(int[] timesReferenced, LinkedList<Integer> order) {
		for (int toRemove = 0; toRemove < 26; toRemove++) {
			if (!isPresent[toRemove] || timesReferenced[toRemove] != 0) {
				continue;
			}

			timesReferenced[toRemove] = -1;
			order.add(toRemove);

			for (int neighbor : aList.get(toRemove)) {
				timesReferenced[neighbor]--;
			}
			findOrdering(timesReferenced, order);

			// undo all changes
			for (int neighbor : aList.get(toRemove)) {
				timesReferenced[neighbor]++;
			}

			order.removeLast();
			timesReferenced[toRemove] = 0;
		}
		if (order.size() == numPresent) {
			StringBuilder answer = new StringBuilder();
			for (int i : order) {
				answer.append((char) (i + 'A'));
			}
			answer.reverse();
			answers.add(answer.toString());
		}
	}

}
