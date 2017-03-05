import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_403A {

	public static void main(String[] args) throws IOException {
		new Div2_403A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());
		boolean[] has = new boolean[numE + 1];
		int count = 0;

		int ans = 0;
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE * 2; i++) {
			int cE = Integer.parseInt(inputData.nextToken());
			if (has[cE]) {
				has[cE] = false;
				count--;
			} else {
				has[cE] = true;
				count++;
				if (count > ans) {
					ans = count;
				}
			}
		}
		System.out.println(ans);
	}

}
