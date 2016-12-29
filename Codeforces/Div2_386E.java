import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Div2_386E {

	public static void main(String[] args) throws IOException {
		new Div2_386E().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numS = Integer.parseInt(inputData.nextToken());

		HashSet<Integer> used = new HashSet<Integer>();

		int[] elements = new int[numE];

		inputData = new StringTokenizer(reader.readLine());
		reader.close();

		ArrayList<Integer> unmarked = new ArrayList<Integer>();
		ArrayList<Integer> eIndex = new ArrayList<Integer>();
		ArrayList<Integer> oIndex = new ArrayList<Integer>();

		int oCount = 0;
		for (int i = 0; i < numE; i++) {
			int next = Integer.parseInt(inputData.nextToken());
			if (!used.contains(next)) {
				elements[i] = next;
				used.add(next);
				oCount += (next & 1);
				if ((next & 1) == 0) {
					eIndex.add(i);
				} else {
					oIndex.add(i);
				}
			} else {
				unmarked.add(i);
			}
		}

		ArrayDeque<Integer> odd = new ArrayDeque<Integer>();
		ArrayDeque<Integer> even = new ArrayDeque<Integer>();

		for (int i = 1; i <= Math.min(3 * numE, numS); i++) {
			if (!used.contains(i)) {
				if ((i & 1) == 0) {
					even.add(i);
				} else {
					odd.add(i);
				}
			}
		}

		int swapped = 0;

		for (int i : unmarked) {
			if (oCount < numE / 2) {
				if (odd.isEmpty()) {
					System.out.println(-1);
					return;
				}
				elements[i] = odd.removeLast();
				oCount++;
			} else {
				if (even.isEmpty()) {
					System.out.println(-1);
					return;
				}
				elements[i] = even.removeLast();
			}
			swapped++;
		}

		for (int i : eIndex) {
			if (oCount < numE / 2) {
				if ((elements[i] & 1) == 0) {
					if (odd.isEmpty()) {
						System.out.println(-1);
						return;
					}
					elements[i] = odd.removeLast();
					swapped++;
					oCount++;
				}
			} else {
				break;
			}
		}
		for (int i : oIndex) {
			if (oCount > numE / 2) {
				if ((elements[i] & 1) == 1) {
					if (even.isEmpty()) {
						System.out.println(-1);
						return;
					}
					elements[i] = even.removeLast();
					swapped++;
					oCount--;
				}
			} else {
				break;
			}
		}
		if (oCount != numE / 2) {
			System.out.println(-1);
			return;
		}

		printer.println(swapped);
		for (int i : elements) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}

}
