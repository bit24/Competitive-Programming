import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class GrassCownoisseur {

	public static void main(String[] args) throws IOException {
		new GrassCownoisseur().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("grass.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("grass.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		aList = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < nV; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			aList.get(Integer.parseInt(inputData.nextToken()) - 1).add(Integer.parseInt(inputData.nextToken()) - 1);
		}
		reader.close();
		findSCCs(nV, nE, aList);
		nC = SCCs.size();

		cSize = new int[nC];

		ArrayList<TreeSet<Integer>> SCCedgeSet = new ArrayList<TreeSet<Integer>>();
		for (int i = 0; i < nC; i++) {
			SCCedgeSet.add(new TreeSet<Integer>());
		}

		for (int i = 0; i < nV; i++) {
			cSize[id[i]]++;
			for (int j : aList.get(i)) {
				if (id[i] != id[j]) {
					SCCedgeSet.get(id[i]).add(id[j]);
				}
			}
		}

		ArrayList<ArrayList<Integer>> nAList = new ArrayList<ArrayList<Integer>>();
		for (TreeSet<Integer> cSet : SCCedgeSet) {
			nAList.add(new ArrayList<Integer>(cSet));
		}

		ArrayDeque<Integer> tOrd = new ArrayDeque<Integer>();
		TopoSortDFS(tOrd, nAList, id[0], new boolean[nV]);

		maxAway = findMaxValues(tOrd, nAList);

		ArrayList<ArrayList<Integer>> rNAList = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < nC; i++) {
			rNAList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < nC; i++) {
			for (int j : nAList.get(i)) {
				rNAList.get(j).add(i);
			}
		}

		ArrayDeque<Integer> rTOrd = new ArrayDeque<Integer>();
		TopoSortDFS(rTOrd, rNAList, id[0], new boolean[nV]);
		maxIn = findMaxValues(rTOrd, rNAList);

		int mV = 0;

		for (int i = 0; i < nC; i++) {
			for (int j : rNAList.get(i)) {
				mV = Math.max(mV, maxAway[i] + maxIn[j] - cSize[id[0]]);
			}
		}
		if (mV <= 0) {
			mV = cSize[id[0]];
		}

		printer.println(mV);
		printer.close();
	}

	public int[] findMaxValues(ArrayDeque<Integer> tOrd, ArrayList<ArrayList<Integer>> aList) {
		int[] maxValue = new int[nC];
		Arrays.fill(maxValue, Integer.MIN_VALUE / 4);
		maxValue[tOrd.peekFirst()] = cSize[tOrd.peekFirst()];
		for (int i : tOrd) {
			int currentMax = maxValue[i];
			for (int neighbor : aList.get(i)) {
				maxValue[neighbor] = Math.max(maxValue[neighbor], currentMax + cSize[neighbor]);
			}
		}
		return maxValue;
	}

	public void TopoSortDFS(ArrayDeque<Integer> tOrd, ArrayList<ArrayList<Integer>> aList, int index,
			boolean[] visited) {
		visited[index] = true;
		for (int neighbor : aList.get(index)) {
			if (!visited[neighbor]) {
				TopoSortDFS(tOrd, aList, neighbor, visited);
			}
		}
		tOrd.addFirst(index);
	}

	ArrayList<ArrayList<Integer>> aList;
	int[] low;
	int[] disc;
	int cT = 0;
	ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
	int[] id;
	ArrayList<ArrayList<Integer>> SCCs;
	int nC;

	int[] cSize;
	int[] maxAway;
	int[] maxIn;

	public void findSCCs(int nV, int nE, ArrayList<ArrayList<Integer>> aList) {
		this.aList = aList;

		low = new int[nV];
		disc = new int[nV];
		id = new int[nV];
		SCCs = new ArrayList<ArrayList<Integer>>();

		// mark "not visited"
		Arrays.fill(disc, -1);

		for (int i = 0; i < nV; i++) {
			if (disc[i] == -1) {
				SCCDFS(i);
			}
		}
	}

	void SCCDFS(int cV) {
		disc[cV] = cT;
		low[cV] = cT++;
		stack.push(cV);

		for (int aV : aList.get(cV)) {
			if (disc[aV] == -1) {
				SCCDFS(aV);
			}

			if (low[aV] < low[cV]) {
				low[cV] = low[aV];
			}
		}
		if (disc[cV] == low[cV]) {
			SCCs.add(new ArrayList<Integer>());
			while (stack.peek() != cV) {
				low[stack.peek()] = Integer.MAX_VALUE / 4;
				id[stack.peek()] = SCCs.size() - 1;
				SCCs.get(SCCs.size() - 1).add(stack.pop());
			}
			low[stack.peek()] = Integer.MAX_VALUE / 4;
			id[stack.peek()] = SCCs.size() - 1;
			SCCs.get(SCCs.size() - 1).add(stack.pop());
		}
	}

}