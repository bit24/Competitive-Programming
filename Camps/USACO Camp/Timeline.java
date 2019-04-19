import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Timeline {

	static int N;
	static int M;
	static int E;

	static int[] cT;
	static int[] l;

	static int[] sV;
	static int[] eV;
	static int[] cost;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		M = Integer.parseInt(inputData.nextToken());
		E = Integer.parseInt(inputData.nextToken());

		cT = new int[N];
		l = new int[N];

		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			cT[i] = Integer.parseInt(inputData.nextToken());
			l[i] = Integer.parseInt(inputData.nextToken());
		}

		sV = new int[E];
		eV = new int[E];
		cost = new int[E];

		for (int i = 0; i < E; i++) {
			inputData = new StringTokenizer(reader.readLine());
			eV[i] = Integer.parseInt(inputData.nextToken()) - 1;
			sV[i] = Integer.parseInt(inputData.nextToken()) - 1;
			cost[i] = Integer.parseInt(inputData.nextToken());
		}

		for (int i = 0; i < N - 1; i++) {
			for (int j = 0; j < E; j++) {
				if (cT[sV[j]] + cost[j] > cT[eV[j]]) {
					cT[eV[j]] = cT[sV[j]] + cost[j];
				}
			}
		}

		for (int j = 0; j < E; j++) {
			if (cT[sV[j]] + cost[j] > cT[eV[j]]) {
				printer.println("impossible");
				printer.close();
				return;
			}
		}

		for (int i = 0; i < N; i++) {
			if (cT[i] > l[i]) {
				printer.println("impossible");
				printer.close();
				return;
			}
		}

		for (int i = 0; i < N; i++) {
			printer.println(cT[i]);
		}
		printer.close();
	}
}
