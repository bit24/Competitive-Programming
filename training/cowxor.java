import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*ID: eric.ca1
LANG: JAVA
TASK: cowxor
*/

public class cowxor {

	public static void main(String[] args) throws IOException {
		new cowxor().run();
	}

	int numElements;
	int numBits = 21;
	int mask = (int) (Math.pow(2, numBits) - 1);

	TreeVertex root = new TreeVertex();

	public void run() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cowxor.in"));
		numElements = Integer.parseInt(reader.readLine());

		int[] elements = new int[numElements + 1];
		int[] prefixXOR = new int[numElements + 1];
		for (int i = 1; i <= numElements; i++) {
			elements[i] = Integer.parseInt(reader.readLine());
			prefixXOR[i] = i == 1 ? elements[i] : elements[i] ^ prefixXOR[i - 1];
		}
		reader.close();

		int best = 0;
		int lIndex = 0;
		int rIndex = 1;

		insert(0, 0);
		for (int i = 1; i <= numElements; i++) {
			int target = mask & (~prefixXOR[i]);
			int bMatch = bMatch(target);
			int value = prefixXOR[i] ^ bMatch;

			if (best < value) {
				best = value;
				lIndex = findValue(bMatch);
				rIndex = i;
			}
			insert(prefixXOR[i], i);
		}

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cowxor.out")));
		printer.print((prefixXOR[lIndex] ^ prefixXOR[rIndex]) + " ");
		printer.println(lIndex + 1 + " " + rIndex);
		printer.close();
	}

	class TreeVertex {
		TreeVertex[] children = new TreeVertex[2];

		int value = -1;

		TreeVertex() {
		}
	}

	public void insert(int key, int value) {
		insert(root, key, numBits - 1, value);
	}

	public void insert(TreeVertex currentVertex, int key, int index, int value) {
		int bit = (key >> index) & 1;
		if (currentVertex.children[bit] == null) {
			currentVertex.children[bit] = new TreeVertex();
		}

		if (index != 0) {
			insert(currentVertex.children[bit], key, index - 1, value);
		} else {
			currentVertex.children[bit].value = value;
		}
	}

	public int bMatch(int value) {
		return bMatch(root, value, numBits - 1);
	}

	public int bMatch(TreeVertex currentVertex, int value, int index) {
		if (currentVertex.value != -1) {
			// currentVertex is a leaf
			return 0;
		}
		int bit = (value >> index) & 1;
		if (currentVertex.children[bit] != null) {
			return bMatch(currentVertex.children[bit], value, index - 1) | (bit << index);
		} else {
			return bMatch(currentVertex.children[bit ^ 1], value, index - 1) | ((bit ^ 1) << index);
		}
	}

	public int findValue(int key) {
		return findValue(root, key, numBits - 1);
	}

	public int findValue(TreeVertex currentVertex, int key, int index) {
		if (currentVertex.value != -1) {
			// currentVertex is a leaf
			return currentVertex.value;
		}
		int bit = (key >> index) & 1;
		return findValue(currentVertex.children[bit], key, index - 1);
	}

}
