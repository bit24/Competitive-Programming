import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class MainB {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int M = Integer.parseInt(inputData.nextToken());
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(reader.readLine());
		}

		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int i = 0; i < M; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int c = Integer.parseInt(inputData.nextToken());
			int d = Integer.parseInt(inputData.nextToken());
			map.put(c, d);
		}

		long sum = 0;
		for (int i = 0; i < N; i++) {
			sum += map.get(a[i]);
		}
		printer.println(sum);
		printer.close();
	}
}
