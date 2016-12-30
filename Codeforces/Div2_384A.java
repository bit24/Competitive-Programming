import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_384A {

	public static void main(String[] args) throws IOException {
		new Div2_384A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numV = Integer.parseInt(inputData.nextToken());
		int start = Integer.parseInt(inputData.nextToken()) - 1;
		int end = Integer.parseInt(inputData.nextToken()) - 1;

		boolean[] comp = new boolean[numV];

		if (end < start) {
			int temp = end;
			end = start;
			start = temp;
		}

		String nextLine = reader.readLine();
		for (int i = 0; i < numV; i++) {
			comp[i] = nextLine.charAt(i) == '1';
		}

		if (comp[start] == comp[end]) {
			System.out.println(0);
			return;
		} else {
			System.out.println(1);
		}
	}

}
