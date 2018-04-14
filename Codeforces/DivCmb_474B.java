import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class DivCmb_474B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nE = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken()) + Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		int[] a = new int[nE];
		for (int i = 0; i < nE; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		PriorityQueue<Integer> elements = new PriorityQueue<Integer>(Collections.reverseOrder());
		for (int i = 0; i < nE; i++) {
			elements.add(Math.abs(Integer.parseInt(inputData.nextToken()) - a[i]));
		}

		while (k-- > 0) {
			int least = elements.remove();
			if (least == 0) {
				elements.add(1);
			} else {
				elements.add(least - 1);
			}
		}

		long sum = 0;
		for (int i : elements) {
			sum += (long) i * i;
		}
		printer.println(sum);
		printer.close();
	}
}
