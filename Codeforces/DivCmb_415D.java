import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class DivCmb_415D {

	public static void main(String[] args) throws IOException {
		new DivCmb_415D().execute();
	}

	int[] comp;

	ArrayList<Integer>[] aList;

	boolean[] visited;
	int start;

	int[] label;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		int numE = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[numV];

		for (int i = 0; i < numV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}
		reader.close();

		ArrayList<AdjSelf> adjSelfs = new ArrayList<AdjSelf>();

		for (int i = 0; i < numV; i++) {
			adjSelfs.add(new AdjSelf(i, aList[i]));
		}

		Collections.sort(adjSelfs);

		comp = new int[numV];
		for (int i = 0; i < numV; i++) {
			comp[i] = i;
		}

		for (int i = 0; i < numV; i++) {
			while (i + 1 < numV && adjSelfs.get(i).compareTo(adjSelfs.get(i + 1)) == 0) {
				comp[adjSelfs.get(i + 1).self] = comp[adjSelfs.get(i).self];
				i++;
			}
		}

		int endPt = -1;

		for (int i = 0; i < numV; i++) {
			if (comp[i] == i) {
				TreeSet<Integer> comprs = new TreeSet<Integer>();
				for (int adj : aList[i]) {
					if(comp[adj] != i){
						comprs.add(comp[adj]);
					}
				}

				if (comprs.size() > 2) {
					System.out.println("NO");
					return;
				}

				if (comprs.size() <= 1) {
					endPt = i;
				}

				aList[i] = new ArrayList<Integer>(comprs);
			}
		}

		if (endPt == -1) {
			System.out.println("NO");
			return;
		}
		label = new int[numV];
		label[endPt] = 1;
		dfsLabel(endPt);

		printer.println("YES");
		for (int i = 0; i < numV; i++) {
			printer.print(label[comp[i]] + " ");
		}
		printer.println();
		printer.close();
	}

	void dfsLabel(int cV) {
		for (int adj : aList[cV]) {
			if (label[adj] == 0) {
				label[adj] = label[cV] + 1;
				dfsLabel(adj);
			}
		}
	}

	class AdjSelf implements Comparable<AdjSelf> {
		ArrayList<Integer> items;
		int self;

		AdjSelf(int self, ArrayList<Integer> adj) {
			this.self = self;
			items = new ArrayList<Integer>(adj);
			items.add(self);
			Collections.sort(items);
		}

		public int compareTo(AdjSelf oth) {
			ArrayList<Integer> oItems = oth.items;
			if (items.size() < oth.items.size()) {
				return -1;
			}
			if (items.size() > oItems.size()) {
				return 1;
			}

			for (int i = 0; i < items.size(); i++) {
				int a = items.get(i), b = oItems.get(i);
				if (a < b) {
					return -1;
				}
				if (a > b) {
					return 1;
				}
			}
			return 0;
		}
	}

}
