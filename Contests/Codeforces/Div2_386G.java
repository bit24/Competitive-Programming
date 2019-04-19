import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_386G {

	public static void main(String[] args) throws IOException {
		new Div2_386G().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numV = Integer.parseInt(inputData.nextToken());
		int mDepth = Integer.parseInt(inputData.nextToken());
		int numL = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		int[] num = new int[mDepth + 1];
		for (int i = 0; i < mDepth; i++) {
			num[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		int count = 0;
		for (int i = 1; i <= mDepth; i++) {
			if (num[i] < num[i - 1]) {
				count += num[i - 1] - num[i];
			}
		}

		if (count > numL) {
			System.out.println(-1);
			return;
		}

		int difference = numL - count;

		ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numV; i++) {
			children.add(new ArrayList<Integer>());
		}

		int vCount = 0;

		ArrayList<ArrayList<Integer>> members = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < mDepth; i++) {
			members.add(new ArrayList<Integer>());
			for (int j = 0; j < num[i]; j++) {
				members.get(i).add(vCount++);
			}
		}

		for (int cDepth = 1; cDepth < mDepth; cDepth++) {
			children.get(members.get(cDepth - 1).get(0)).add(members.get(cDepth).get(0));

			for (int j = 1; j < num[cDepth]; j++) {
				int cVertex = members.get(cDepth).get(j);
				if (num[cDepth - 1] <= j) {
					children.get(members.get(cDepth - 1).get(0)).add(cVertex);
					continue;
				}
				if (difference != 0) {
					children.get(members.get(cDepth - 1).get(0)).add(cVertex);
					difference--;
					continue;
				}
				children.get(members.get(cDepth - 1).get(j)).add(cVertex);
			}
		}

		if (difference != 0) {
			System.out.println(-1);
			return;
		}

		printer.println(numV);
		for (int i = 0; i < numV; i++) {
			for (int j : children.get(i)) {
				printer.println((i + 2) + " " + (j + 2));
			}
		}
		for (int i : members.get(0)) {
			printer.println("1 " + (i + 2));
		}
		printer.close();

	}

}
