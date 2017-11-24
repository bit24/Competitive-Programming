import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class PromotionCounting {

	public static void main(String[] args) throws IOException {
		new PromotionCounting().execute();
	}

	int root = 1;

	int[] value;

	int numV;

	ArrayList<ArrayDeque<Integer>> children = new ArrayList<ArrayDeque<Integer>>();

	TreeMap<Integer, Integer> indexMap = new TreeMap<Integer, Integer>();

	int[] ans;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("promote.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("promote.out")));

		numV = Integer.parseInt(reader.readLine());
		value = new int[numV + 1];

		children.add(new ArrayDeque<Integer>());

		int[] vSet = new int[numV + 1];
		for (int i = 1; i <= numV; i++) {
			value[i] = Integer.parseInt(reader.readLine());
			vSet[i] = value[i];

			children.add(new ArrayDeque<Integer>());
		}

		Arrays.sort(vSet);
		TreeMap<Integer, Integer> vMap = new TreeMap<Integer, Integer>();
		for (int i = 1; i <= numV; i++) {
			vMap.put(vSet[i], (int) i);
		}

		for (int i = 1; i <= numV; i++) {
			value[i] = vMap.get(value[i]);
		}

		for (int i = 2; i <= numV; i++) {
			children.get(Integer.parseInt(reader.readLine())).add(i);
		}
		reader.close();

		BIT = new int[numV + 1];
		ans = new int[numV + 1];
		elements = new int[numV + 1];
		dfs();

		for (int i = 1; i <= numV; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	void dfs() {
		ArrayDeque<Integer> dfsStack = new ArrayDeque<Integer>();
		boolean[] started = new boolean[numV + 1];
		dfsStack.add(1);

		dfsLoop: while (!dfsStack.isEmpty()) {
			int cVertex = dfsStack.peekLast();

			int cValue = value[cVertex];
			if (!started[cVertex]) {
				started[cVertex] = true;
				int preValue = query(cValue);

				if (preValue != 0) {
					update(cValue, -preValue);
					update(cValue + 1, preValue);
				}
			}

			ArrayDeque<Integer> cChildren = children.get(cVertex);
			if (!cChildren.isEmpty()) {
				dfsStack.add(cChildren.removeFirst());
				continue dfsLoop;
			}

			ans[cVertex] = query(cValue);
			update(1, 1);
			update(cValue, -1);
			dfsStack.removeLast();
		}
	}

	// one indexed as operations on index 0 does not work
	int[] BIT;

	int[] elements;

	void update(int ind, int delta) {
		elements[ind] += delta;

		while (ind <= numV) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	int query(int ind) {
		int sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}

}
