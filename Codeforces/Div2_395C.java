import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_395C {

	public static void main(String[] args) throws IOException {
		new Div2_395C().execute();
	}

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	int[] color;

	boolean[] visited;
	int[] size;

	int numComp = 0;
	int[] compNumber;

	ArrayList<TreeSet<Integer>> compAList = new ArrayList<TreeSet<Integer>>();
	ArrayList<ArrayList<Integer>> members = new ArrayList<ArrayList<Integer>>();

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numV = Integer.parseInt(reader.readLine());
		for (int i = 0; i <= numV; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList.get(a).add(b);
			aList.get(b).add(a);
		}

		color = new int[numV + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numV; i++) {
			color[i] = Integer.parseInt(inputData.nextToken());
		}

		visited = new boolean[numV + 1];

		dfs(1);
		for (int i = 1; i <= numV; i++) {
			if (!visited[i]) {
				printer.println("NO");
				printer.close();
				return;
			}
		}

		visited = new boolean[numV + 1];
		size = new int[numV + 1];
		compNumber = new int[numV + 1];
		for (int i = 1; i <= numV; i++) {
			if (!visited[i]) {
				compNumber[i] = numComp++;
				size[compNumber[i]] = 1;
				members.add(new ArrayList<Integer>());
				members.get(compNumber[i]).add(i);
				compAList.add(new TreeSet<Integer>());
				compress(i);
			}
		}

		visited = new boolean[numV + 1];
		for (int i = 1; i <= numV; i++) {
			if (!visited[i]) {
				fNeighbors(i);
			}
		}

		boolean valid = true;
		int multi = -1;

		for (int i = 0; i < numComp; i++) {
			if (compAList.get(i).size() > 1) {
				if (multi == -1) {
					multi = i;
				} else {
					valid = false;
					break;
				}
			}
		}

		if (multi == -1) {
			assert (numComp <= 2);
			for (int i = 0; i < numComp; i++) {
				int sPoints = 0;
				int cPoint = members.get(i).get(0);
				for (int j : members.get(i)) {
					boolean sig = false;
					for (int n : aList.get(j)) {
						if (compNumber[n] != compNumber[j]) {
							sig = true;
						}
					}
					if (sig) {
						sPoints++;
						cPoint = j;
					}
				}
				if (sPoints <= 1) {
					printer.println("YES");
					printer.println(cPoint);
					printer.close();
					return;
				}
			}
			printer.println("NO");
		} else {
			if (valid) {
				int significant = -1;

				for (int i : members.get(multi)) {
					boolean sig = false;
					for (int n : aList.get(i)) {
						if (compNumber[n] != compNumber[i]) {
							sig = true;
						}
					}
					if (sig) {
						if (significant == -1) {
							significant = i;
						} else {
							printer.println("NO");
							printer.close();
							return;
						}
					}
				}

				printer.println("YES");
				printer.println(significant);
			} else {
				printer.println("NO");
			}
		}
		printer.close();
	}

	void dfs(int current) {
		visited[current] = true;
		for (int neighbor : aList.get(current)) {
			if (!visited[neighbor]) {
				dfs(neighbor);
			}
		}
	}

	void fNeighbors(int current) {
		visited[current] = true;
		for (int neighbor : aList.get(current)) {
			if (color[neighbor] == color[current]) {
				if (!visited[neighbor]) {
					fNeighbors(neighbor);
				}
			} else {
				compAList.get(compNumber[current]).add(compNumber[neighbor]);
			}
		}
	}

	void compress(int current) {
		visited[current] = true;
		for (int neighbor : aList.get(current)) {
			if (!visited[neighbor]) {
				if (color[neighbor] == color[current]) {
					compNumber[neighbor] = compNumber[current];
					size[compNumber[current]]++;
					members.get(compNumber[current]).add(neighbor);

					compress(neighbor);
				}
			}
		}
	}

}
