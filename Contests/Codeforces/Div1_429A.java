import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div1_429A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());
		Integer[] a = new Integer[len];
		Integer[] b = new Integer[len];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < len; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < len; i++) {
			b[i] = Integer.parseInt(inputData.nextToken());
		}

		Arrays.sort(a);

		Integer[] bCopy = Arrays.copyOf(b, len);
		Arrays.sort(bCopy);

		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();

		for (int i = 0; i < len; i++) {
			if (i == 0 || !bCopy[i].equals(bCopy[i - 1])) {
				map.put(bCopy[i], len - 1 - i);
			}
		}

		for (int i = 0; i < len; i++) {
			Integer get = map.get(b[i]);
			printer.print(a[get] + " ");
			map.put(b[i], get - 1);
		}
		printer.println();
		printer.close();
	}

}
