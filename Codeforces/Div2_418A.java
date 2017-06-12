import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_418A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int aLen = Integer.parseInt(inputData.nextToken());
		int bLen = Integer.parseInt(inputData.nextToken());

		if (bLen >= 2) {
			System.out.println("Yes");
			return;
		}

		int[] aSeq = new int[aLen];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < aLen; i++) {
			aSeq[i] = Integer.parseInt(inputData.nextToken());
			if (aSeq[i] == 0) {
				aSeq[i] = Integer.parseInt(reader.readLine());
			}
		}

		for (int i = 1; i < aLen; i++) {
			if (aSeq[i - 1] > aSeq[i]) {
				System.out.println("Yes");
				return;
			}
		}
		System.out.println("No");
	}

}
