import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_387A {

	public static void main(String[] args) throws IOException {
		new Div2_387A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int input = Integer.parseInt(reader.readLine());

		int rowNum = (int) Math.sqrt(input);

		while (input % rowNum != 0) {
			rowNum--;
		}

		System.out.println(rowNum + " " + input / rowNum);
	}

}
