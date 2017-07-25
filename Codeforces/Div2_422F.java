import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_422F {

	static PrintWriter printer;

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] iList;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numV = Integer.parseInt(reader.readLine());

		aList = new ArrayList[numV + 1];
		iList = new ArrayList[numV + 1];

		for (int i = 1; i <= numV; i++) {
			aList[i] = new ArrayList<Integer>();
			iList[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i < numV; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
			iList[a].add(i);
			iList[b].add(i);
		}

		printer.println(numV - 1);
		dfs(1, 0, 0);
		printer.close();
	}

	static void dfs(int cV, int pV, double tA) {
		int deg = aList[cV].size();

		double incr = 2.0 / deg;

		double cOff = tA + incr;

		if (cOff >= 2) {
			cOff -= 2;
		}
		for (int adjI = 0; adjI < deg; adjI++) {
			int aV = aList[cV].get(adjI);
			if (aV != pV) {
				printer.print("1 " + iList[cV].get(adjI) + " ");
				if (cOff <= 1) {
					printer.println(aV + " " + cV + " " + (1 - cOff));
					dfs(aV, cV, 1 + cOff);
				} else {
					printer.println(cV + " " + aV + " " + (1 - (cOff - 1)));
					dfs(aV, cV, cOff - 1);
				}
				cOff += incr;
				if (cOff >= 2) {
					cOff -= 2;
				}
			}
		}
	}

}
