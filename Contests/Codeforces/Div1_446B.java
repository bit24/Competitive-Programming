import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div1_446B {

	static int[] vals;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());
		vals = new int[nV];
		Integer[] indices = new Integer[nV];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			vals[i] = Integer.parseInt(inputData.nextToken());
			indices[i] = i;
		}
		Arrays.sort(indices, ByRef);

		int[] nVals = new int[nV];

		for (int i = 0; i < nV - 1; i++) {
			nVals[indices[i]] = vals[indices[i + 1]];
		}
		nVals[indices[nV - 1]] = vals[indices[0]];
		for (int i : nVals) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}

	static Comparator<Integer> ByRef = new Comparator<Integer>() {
		public int compare(Integer a, Integer b) {
			return Integer.compare(vals[a], vals[b]);
		}
	};
}
