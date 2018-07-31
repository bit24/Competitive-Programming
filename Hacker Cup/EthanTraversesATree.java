import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class EthanTraversesATree {

	static int[][] chldn;

	static ArrayList<Integer>[] aList;

	static int[] label;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("traverses.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("traverses.out")));

		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int nV = Integer.parseInt(inputData.nextToken());
			int K = Integer.parseInt(inputData.nextToken());
			chldn = new int[nV][2];
			aList = new ArrayList[nV];
			label = new int[nV];
			for (int i = 0; i < nV; i++) {
				aList[i] = new ArrayList<>();
			}
			Arrays.fill(label, -1);

			for (int i = 0; i < nV; i++) {
				inputData = new StringTokenizer(reader.readLine());
				int l = Integer.parseInt(inputData.nextToken()) - 1;
				int r = Integer.parseInt(inputData.nextToken()) - 1;
				chldn[i][0] = l;
				chldn[i][1] = r;
			}

			preOrder = new ArrayList<>();
			postOrder = new ArrayList<>();
			preDFS(0);
			postDFS(0);

			for (int i = 0; i < nV; i++) {
				int v1 = preOrder.get(i);
				int v2 = postOrder.get(i);
				aList[v1].add(v2);
				aList[v2].add(v1);
			}

			int lLabel = 0;
			for (int i = 0; i < nV; i++) {
				if (label[i] == -1) {
					if (lLabel < K) {
						lLabel++;
					}
					lAll(i, lLabel);
				}
			}
			if (lLabel < K) {
				printer.println("Case #" + cT + ": Impossible");
			} else {
				printer.print("Case #" + cT + ":");
				for (int i = 0; i < nV; i++) {
					printer.print(" " + label[i]);
				}
				printer.println();
			}
		}
		reader.close();
		printer.close();
	}

	static void lAll(int cV, int cLabel) {
		label[cV] = cLabel;
		for (int aV : aList[cV]) {
			if (label[aV] == -1) {
				lAll(aV, cLabel);
			}
		}
	}

	static ArrayList<Integer> preOrder;
	static ArrayList<Integer> postOrder;

	static void preDFS(int cV) {
		preOrder.add(cV);
		if (chldn[cV][0] != -1) {
			preDFS(chldn[cV][0]);
		}
		if (chldn[cV][1] != -1) {
			preDFS(chldn[cV][1]);
		}
	}

	static void postDFS(int cV) {
		if (chldn[cV][0] != -1) {
			postDFS(chldn[cV][0]);
		}
		if (chldn[cV][1] != -1) {
			postDFS(chldn[cV][1]);
		}
		postOrder.add(cV);
	}
}
