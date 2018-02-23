import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div1_AIM4B {

	static Random rng = new Random();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nE = Integer.parseInt(inputData.nextToken());
		int first = Integer.parseInt(inputData.nextToken());
		int target = Integer.parseInt(inputData.nextToken());

		TreeMap<Integer, Integer> qData = new TreeMap<Integer, Integer>();
		int[] nxt = new int[nE + 1];
		Arrays.fill(nxt, -1);

		printer.println("? " + first);
		printer.flush();
		inputData = new StringTokenizer(reader.readLine());
		qData.put(Integer.parseInt(inputData.nextToken()), first);
		nxt[first] = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < 500; i++) {
			int qInd = 1 + rng.nextInt(nE);
			printer.println("? " + qInd);
			printer.flush();
			inputData = new StringTokenizer(reader.readLine());
			qData.put(Integer.parseInt(inputData.nextToken()), qInd);
			nxt[qInd] = Integer.parseInt(inputData.nextToken());
		}

		Integer lowerK = qData.lowerKey(target);
		if (lowerK == null) {
			printer.println("! " + qData.firstKey());
			printer.close();
			return;
		}
		Integer lower = qData.get(lowerK);

		int cNxt = nxt[lower];
		while (true) {
			if (cNxt == -1) {
				printer.println("! -1");
				printer.close();
				return;
			}

			printer.println("? " + cNxt);
			printer.flush();
			inputData = new StringTokenizer(reader.readLine());
			int nVal = Integer.parseInt(inputData.nextToken());
			int nNxt = Integer.parseInt(inputData.nextToken());

			if (nVal >= target) {
				printer.println("! " + nVal);
				printer.close();
				return;
			}
			cNxt = nNxt;
		}
	}

}
