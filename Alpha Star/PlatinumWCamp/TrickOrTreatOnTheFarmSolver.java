import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class TrickOrTreatOnTheFarmSolver {

	public static void main(String[] args) throws IOException {
		new TrickOrTreatOnTheFarmSolver().execute();
	}

	int[] next;

	ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

	boolean[] visited;
	int[] cycleLength;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numVertices = Integer.parseInt(reader.readLine());
		next = new int[numVertices];

		for (int i = 0; i < numVertices; i++) {
			next[i] = Integer.parseInt(reader.readLine()) - 1;
		}
		reader.close();

		visited = new boolean[numVertices];
		cycleLength = new int[numVertices];

		for (int i = 0; i < numVertices; i++) {
			if (!visited[i]) {
				searchCycles(i, new boolean[numVertices]);
			}
			// System.out.println(i + ": " + cycleLength[i]);
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		ans = new int[numVertices];
		Arrays.fill(ans, -1);

		for (int i = 0; i < numVertices; i++) {
			printer.println(searchDistance(i));
		}
		printer.close();
	}

	int[] ans;

	int searchDistance(int index) {
		if (ans[index] != -1) {
			return ans[index];
		}
		if (cycleLength[index] != 0) {
			return cycleLength[index];
		}
		return ans[index] = 1 + searchDistance(next[index]);
	}

	void searchCycles(int index, boolean[] cVisited) {
		if (cVisited[index]) {
			ArrayList<Integer> cycleMembers = new ArrayList<Integer>();

			while (stack.peek() != index) {
				cycleMembers.add(stack.pop());
			}
			assert (stack.peek() == index);
			cycleMembers.add(stack.pop());

			for (int i : cycleMembers) {
				cycleLength[i] = cycleMembers.size();
			}
			return;
		}

		if (visited[index]) {
			return;
		}

		cVisited[index] = true;
		visited[index] = true;
		stack.push(index);

		searchCycles(next[index], cVisited);
	}

}
