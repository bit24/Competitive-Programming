import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_389A {

	public static void main(String[] args) throws IOException {
		new Div2_389A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		inputData.nextToken();
		int numR = Integer.parseInt(inputData.nextToken());
		int ID = Integer.parseInt(inputData.nextToken());

		int lane = ((ID - 1) / (2 * numR)) + 1;

		int row = (ID % (2 * numR));
		if (row == 0) {
			row = 2 * numR;
		}
		row++;

		row /= 2;

		System.out.println(lane + " " + row + " " + ((ID & 1) == 0 ? "R" : "L"));
	}
}
