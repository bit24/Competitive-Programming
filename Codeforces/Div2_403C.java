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

public class Div2_403C {

	public static void main(String[] args) throws IOException {
		new Div2_403C().execute();
	}

	int numV;
	ArrayList<Integer>[] aList;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		numV = Integer.parseInt(reader.readLine());
		aList = new ArrayList[numV];
		for (int i = 0; i < numV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		number = new int[numV];
		number[0] = 1;
		dfs(0, 0);

		int max = 1;
		for (int i = 0; i < numV; i++) {
			if (max < number[i]) {
				max = number[i];
			}
		}

		printer.println(max);
		for (int i = 0; i < numV; i++) {
			printer.print(number[i] + " ");
		}
		printer.println();
		printer.close();
	}

	int[] number;

	void dfs(int c, int p) {	
		int nNum = 1;

		KVPair[] adjs = new KVPair[aList[c].size()];

		for (int i = 0; i < aList[c].size(); i++) {
			int adj = aList[c].get(i);
			adjs[i] = new KVPair(aList[adj].size(), adj);
		}

		Arrays.sort(adjs, Collections.reverseOrder());

		for (KVPair adjP : adjs) {
			int adj = adjP.value;
			if (adj != p) {
				while (nNum == number[c] || nNum == number[p]) {
					nNum++;
				}
				number[adj] = nNum++;
				dfs(adj, c);
			}
		}
	}

	class KVPair implements Comparable<KVPair> {
		int key;
		int value;

		KVPair(int key, int value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(KVPair o) {
			return (key < o.key) ? -1 : ((key == o.key) ? 0 : 1);
		}
	}

}
