import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

class Hierholzer {

	static int numE;

	static int numV = 500;

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static ArrayList<ArrayDeque<Integer>> mList = new ArrayList<ArrayDeque<Integer>>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < numV; i++) {
			aList.add(new ArrayList<Integer>());
		}
		numE = Integer.parseInt(reader.readLine());
		for (int i = 0; i < numE; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int startVertex = Integer.parseInt(inputData.nextToken()) - 1;
			int endVertex = Integer.parseInt(inputData.nextToken()) - 1;

			aList.get(startVertex).add(endVertex);
			aList.get(endVertex).add(startVertex);
		}
		reader.close();
		for (ArrayList<Integer> neighbors : aList) {
			Collections.sort(neighbors);
		}

		for (ArrayList<Integer> cList : aList) {
			mList.add(new ArrayDeque<Integer>(cList));
		}

		int sV = 0;
		for (int cV = 0; cV < numV; cV++) {
			if (mList.get(cV).size() % 2 != 0) {
				sV = cV;
				break;
			}
		}

		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		ArrayDeque<Integer> path = new ArrayDeque<Integer>();

		stack.push(sV);

		while (!stack.isEmpty()) {
			int cV = stack.pop();
			if (mList.get(cV).isEmpty()) {
				path.add(cV);
			} else {
				stack.push(cV);

				int neighbor = mList.get(cV).removeFirst();
				mList.get(neighbor).remove(new Integer(cV));

				stack.push(neighbor);
			}
		}

		while (!path.isEmpty()) {
			System.out.println(path.removeLast() + 1);
		}
	}
}
