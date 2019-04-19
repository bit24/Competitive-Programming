import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Lyft_18FA {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nH = Integer.parseInt(inputData.nextToken());

		ArrayList<Integer> vert = new ArrayList<>();
		for (int i = 0; i < nV; i++) {
			vert.add(Integer.parseInt(reader.readLine()));
		}
		Collections.sort(vert);

		if (vert.isEmpty() || vert.get(vert.size() - 1) != 1_000_000_000) {
			vert.add(1_000_000_000);
		}

		int[] endB = new int[vert.size()];

		int nA = 0;
		for (int i = 0; i < nH; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int x1 = Integer.parseInt(inputData.nextToken());
			int x2 = Integer.parseInt(inputData.nextToken());
			int y = Integer.parseInt(inputData.nextToken());

			if (x1 != 1) {
				continue;
			}
			nA++;
			int prev = Collections.binarySearch(vert, x2);
			if (prev < 0) {
				prev = -(prev + 1);
				if (prev >= vert.size()) {
					continue;
				}
				endB[prev]++;
			} else {
				if (prev + 1 >= vert.size()) {
					continue;
				}
				endB[prev + 1]++;
			}
		}

		int min = Integer.MAX_VALUE;

		int nE = 0;
		for (int i = 0; i < vert.size(); i++) {
			nE += endB[i];
			min = Math.min(min, nA - nE + i);
		}
		printer.println(min);
		printer.close();
	}
}
