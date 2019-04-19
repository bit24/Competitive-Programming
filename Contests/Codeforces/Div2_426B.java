import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_426B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int len = Integer.parseInt(inputData.nextToken());
		int uB = Integer.parseInt(inputData.nextToken());

		String data = reader.readLine();

		int[] last = new int[26];

		for (int i = 0; i < len; i++) {
			last[data.charAt(i) - 'A'] = i;
		}

		boolean[] open = new boolean[26];

		int nO = 0;

		for (int i = 0; i < len; i++) {
			if (!open[data.charAt(i) - 'A']) {
				open[data.charAt(i) - 'A'] = true;
				nO++;
			}
			if (nO > uB) {
				System.out.println("YES");
				return;
			}
			if (last[data.charAt(i) - 'A'] == i) {
				nO--;
			}
		}
		System.out.println("NO");
	}

}
