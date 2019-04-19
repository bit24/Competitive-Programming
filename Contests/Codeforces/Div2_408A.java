import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_408A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int tar = Integer.parseInt(inputData.nextToken());
		int amt = Integer.parseInt(inputData.nextToken());
		inputData = new StringTokenizer(reader.readLine());

		int cDist = Integer.MAX_VALUE;

		for (int i = 1; i <= numE; i++) {
			int cCost = Integer.parseInt(inputData.nextToken());
			if (cCost != 0 && cCost <= amt && Math.abs(i - tar) < cDist) {
				cDist = Math.abs(i - tar);
			}
		}

		System.out.println(cDist * 10);
	}

}
