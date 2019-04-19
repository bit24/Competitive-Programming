import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_424A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int nI = Integer.parseInt(reader.readLine());
		int[] items = new int[nI];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken());
		}

		int i = 0;
		while (i + 1 < nI && items[i] < items[i + 1]) {
			i++;
		}
		while (i + 1 < nI && items[i] == items[i + 1]) {
			i++;
		}
		while (i + 1 < nI && items[i] > items[i + 1]) {
			i++;
		}

		System.out.println(i == nI - 1 ? "YES" : "NO");
	}

}
