import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

	int numE;
	int numQ;

	public static void main(String[] args) throws IOException {
		new Main().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		numQ = Integer.parseInt(inputData.nextToken());

		int[] elements = new int[numE + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] sorted = Arrays.copyOf(elements, numE + 1);
		Arrays.sort(sorted, 1, numE + 1);

		TreeMap<Integer, Integer> compMap = new TreeMap<Integer, Integer>();
		int[] decMap = new int[numE + 1];

		for (int i = 1; i <= numE; i++) {
			compMap.put(sorted[i], i);
			decMap[i] = sorted[i];
		}

		for (int i = 1; i <= numE; i++) {
			elements[i] = compMap.get(elements[i]);
		}

		Node[] pstTree = new Node[numE + 1];
		pstTree[0] = blankTree(1, numE);

		for (int i = 1; i <= numE; i++) {
			pstTree[i] = NoRecursion_updateTree(pstTree[i - 1], 1, numE, elements[i]);
		}

		while (numQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			int req = Integer.parseInt(inputData.nextToken());

			printer.println(decMap[query(pstTree[l - 1], pstTree[r], 1, numE, req)]);
		}
		printer.close();
	}

	int query(Node lNode, Node rNode, int cL, int cR, int k) {

		while (cL != cR) {
			int mid = (cL + cR) / 2;

			int lCount = rNode.left.count - lNode.left.count;

			if (k <= lCount) {
				lNode = lNode.left;
				rNode = rNode.left;
				cR = mid;
			} else {
				lNode = lNode.right;
				rNode = rNode.right;
				cL = mid + 1;
				k -= lCount;
			}
		}
		return cL;
	}

	Node blankTree(int cL, int cR) {
		if (cL == cR) {
			return new Node();
		}
		int mid = (cL + cR) / 2;
		Node cNode = new Node();
		cNode.left = blankTree(cL, mid);
		cNode.right = blankTree(mid + 1, cR);
		return cNode;
	}

	// previous contains the node overseeing this part of the tree as of the most recent update
	// method returns the overseeing node of the part of the tree after the update
	// integrity of the old overseeing node and its descendants is preserved
	// assumption : cL <= uI <= cR
	Node updateTree(Node previous, int cL, int cR, int uI) {
		if (cL == cR) {
			return new Node(previous.count + 1);
		}

		Node newNode = new Node();

		int mid = (cL + cR) / 2;

		if (uI <= mid) {
			newNode.left = updateTree(previous.left, cL, mid, uI);
			newNode.right = previous.right;
		} else {
			newNode.left = previous.left;
			newNode.right = updateTree(previous.right, mid + 1, cR, uI);
		}

		newNode.count = previous.count + 1;
		return newNode;
	}

	Node NoRecursion_updateTree(Node preVer, int cL, int cR, int uI) {
		Node newRoot = new Node();

		Node cNode = newRoot;
		while (cL != cR) {
			cNode.count = preVer.count + 1;
			int mid = (cL + cR) / 2;

			if (uI <= mid) {
				cNode.right = preVer.right;

				cNode = (cNode.left = new Node());
				preVer = preVer.left;
				cR = mid;
			} else {
				cNode.left = preVer.left;

				cNode = (cNode.right = new Node());
				preVer = preVer.right;
				cL = mid + 1;
			}
		}
		cNode.count = preVer.count + 1;
		return newRoot;
	}

	class Node {
		Node left;
		Node right;
		int count;

		Node() {}

		Node(int count) {
			this.count = count;
		}
	}
}
