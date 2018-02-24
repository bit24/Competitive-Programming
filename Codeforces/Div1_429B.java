import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_429B {

	static int[] label;

	static int[] ePt;
	static int[] nxt;
	static int[] start;

	static boolean[] visited;

	static ArrayList<Integer> ans = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());

		label = new int[nV];
		inputData = new StringTokenizer(reader.readLine());
		int sInd = -1;
		int sum = 0;
		for (int i = 0; i < nV; i++) {
			label[i] = Integer.parseInt(inputData.nextToken());
			if (label[i] == -1) {
				sInd = i;
			}
			sum += label[i];
		}

		ePt = new int[nE * 2];
		nxt = new int[nE * 2];
		start = new int[nV];
		Arrays.fill(start, -1);

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			ePt[i * 2] = b;
			nxt[i * 2] = start[a];
			start[a] = i * 2;

			ePt[i * 2 + 1] = a;
			nxt[i * 2 + 1] = start[b];
			start[b] = i * 2 + 1;
		}

		visited = new boolean[nV];
		if (sInd != -1) {
			pull(sInd);
		} else {
			if (sum % 2 == 1) {
				printer.println(-1);
				printer.close();
				return;
			}
			pull(0);
		}
		printer.println(ans.size());
		for (int i : ans) {
			printer.print((i + 1) + " ");
		}
		printer.close();
	}

	static void pull(int cV) {
		visited[cV] = true;
		int eI = start[cV];

		while (eI != -1) {
			int aV = ePt[eI];
			if (!visited[aV]) {
				pull(aV);
				if (label[aV] == 1) {
					label[aV] = 0;
					if (label[cV] != -1) {
						label[cV] ^= 1;
					}
					ans.add(eI / 2);
				}
			}
			eI = nxt[eI];
		}
	}

}
