import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div2_410D {

	public static void main(String[] args) throws IOException {
		new Div2_410D().execute();
	}

	int[] a;
	int[] b;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numE = Integer.parseInt(reader.readLine());
		a = new int[numE];
		b = new int[numE];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			b[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		Integer[] indices = new Integer[numE];
		for (int i = 0; i < numE; i++) {
			indices[i] = i;
		}

		Arrays.sort(indices, indexSorter);
		for (int i = 0; i < numE / 2; i++) {
			int temp = indices[i];
			indices[i] = indices[numE - 1 - i];
			indices[numE - 1 - i] = temp;
		}
		printer.println(numE / 2 + 1);

		if ((numE & 1) == 1) {
			printer.print(indices[0] + 1 + " ");
		} else {
			printer.print((b[indices[0]] >= b[indices[1]] ? indices[1] : indices[0]) + 1 + " ");
		}

		for (int i = (numE & 1); i < numE; i += 2) {
			printer.print((b[indices[i]] >= b[indices[i + 1]] ? indices[i] : indices[i + 1]) + 1 + " ");
		}

		printer.println();
		printer.close();
	}

	Comparator<Integer> indexSorter = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return Integer.compare(a[o1], a[o2]);
		}
	};

}
