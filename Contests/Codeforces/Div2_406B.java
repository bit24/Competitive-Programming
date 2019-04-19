import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_406B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		inputData.nextToken();
		int numG = Integer.parseInt(inputData.nextToken());

		gLoop:
		for (int i = 0; i < numG; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int numR = Integer.parseInt(inputData.nextToken());
			TreeSet<Integer> members = new TreeSet<Integer>();
			while (numR-- > 0) {
				int nxt = Integer.parseInt(inputData.nextToken());
				if (members.contains(-nxt)) {
					continue gLoop;
				}
				members.add(nxt);
			}
			System.out.println("YES");
			return;
		}
		System.out.println("NO");
	}

}
