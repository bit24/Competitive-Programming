import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Div1_AIM4A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());
		int[] array = new int[len];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < len; i++) {
			array[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] sorted = Arrays.copyOf(array, len);
		Arrays.sort(sorted);

		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < len; i++) {
			map.put(sorted[i], i);
		}

		for (int i = 0; i < len; i++) {
			array[i] = map.get(array[i]);
		}

		boolean[] visited = new boolean[len];

		ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			if (!visited[i]) {
				ArrayList<Integer> path = new ArrayList<>();

				int cur = i;
				while (!visited[cur]) {
					visited[cur] = true;
					path.add(cur);
					cur = array[cur];
				}

				paths.add(path);
			}
		}
		printer.println(paths.size());
		for (ArrayList<Integer> cPath : paths) {
			printer.print(cPath.size());
			for (int i : cPath) {
				printer.print(" " + (i + 1));
			}
			printer.println();
		}

		printer.close();
	}
}
