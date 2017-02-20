import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_399A {

	public static void main(String[] args) throws IOException {
		new Div2_399A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());
		int[] elements = new int[numE];
		TreeSet<Integer> eSet = new TreeSet<Integer>();
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
			eSet.add(elements[i]);
		}

		int count = 0;
		for (int i = 0; i < numE; i++) {
			if (eSet.lower(elements[i]) != null && eSet.higher(elements[i]) != null) {
				count++;
			}
		}
		System.out.println(count);
	}

}
