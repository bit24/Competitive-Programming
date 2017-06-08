import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_416C {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		reader.close();

		int[] elements = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] last = new int[5001];
		for (int i = 1; i <= numE; i++) {
			last[elements[i]] = i;
		}
		
		int[] first = new int[5001];
		for (int i = numE; i >= 1; i--) {
			first[elements[i]] = i;
		}

		ArrayList<Integer>[] prev = new ArrayList[numE + 1];
		ArrayList<Integer>[] value = new ArrayList[numE + 1];

		for (int i = 1; i <= numE; i++) {
			prev[i] = new ArrayList<Integer>();
			value[i] = new ArrayList<Integer>();
		}

		startL:
		for (int i = 1; i <= numE; i++) {
			if (first[elements[i]] != i) {
				continue;
			}
			boolean[] visited = new boolean[5001];
			int cXor = elements[i];
			visited[cXor] = true;
			int cEnd = last[elements[i]];

			for (int j = i + 1; j <= cEnd; j++) {
				int cElement = elements[j];
				if (first[cElement] < i) {
					continue startL;
				}
				if (!visited[cElement]) {
					cXor ^= cElement;
					visited[cElement] = true;
				}
				int pos = last[cElement];
				if (pos > cEnd) {
					cEnd = pos;
				}
			}

			prev[cEnd].add(i - 1);
			value[cEnd].add(cXor);
		}

		int[] max = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			int cMax = max[i - 1];
			for (int t = 0; t < prev[i].size(); t++) {
				int pos = value[i].get(t) + max[prev[i].get(t)];
				if (cMax < pos) {
					cMax = pos;
				}
			}
			max[i] = cMax;
		}
		System.out.println(max[numE]);
	}

}
