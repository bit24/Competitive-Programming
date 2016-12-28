import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_387E {

	public static void main(String[] args) throws IOException {
		new Div2_387E().execute();
	}

	StringTokenizer inputData;

	ArrayList<Integer> roots = new ArrayList<Integer>();

	ArrayList<String> value = new ArrayList<String>();

	ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
	int vNum;

	ArrayList<ArrayList<Integer>> layers = new ArrayList<ArrayList<Integer>>();

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		inputData = new StringTokenizer(reader.readLine(), ",");
		reader.close();

		while (inputData.hasMoreTokens()) {
			roots.add(parseVertex());
		}

		BFS();
		printer.println(layers.size());
		for (ArrayList<Integer> layer : layers) {
			for (int vertex : layer) {
				printer.print(value.get(vertex) + " ");
			}
			printer.println();
		}
		printer.close();
	}

	void BFS() {
		ArrayList<Integer> cLayer = roots;
		ArrayList<Integer> nLayer = new ArrayList<Integer>();

		while (!cLayer.isEmpty()) {
			layers.add(cLayer);
			for (int vertex : cLayer) {
				for (int child : children.get(vertex)) {
					nLayer.add(child);
				}
			}
			cLayer = nLayer;
			nLayer = new ArrayList<Integer>();
		}
	}

	int parseVertex() {
		int cVertex = vNum++;

		value.add(inputData.nextToken());

		int numC = Integer.parseInt(inputData.nextToken());
		ArrayList<Integer> childList = new ArrayList<Integer>();
		children.add(childList);

		for (int i = 0; i < numC; i++) {
			childList.add(parseVertex());
		}

		return cVertex;
	}
}