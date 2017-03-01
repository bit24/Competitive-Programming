import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_402C {

	public static void main(String[] args) throws IOException {
		new Div2_402C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numK = Integer.parseInt(inputData.nextToken());

		int[] aPrice = new int[numE];
		inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < numE; i++) {
			aPrice[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] bPrice = new int[numE];
		inputData = new StringTokenizer(reader.readLine());

		int[] mods = new int[numE];
		int sum = 0;
		for (int i = 0; i < numE; i++) {
			bPrice[i] = Integer.parseInt(inputData.nextToken());
			mods[i] = aPrice[i] - bPrice[i];
			sum += bPrice[i];
		}

		Arrays.sort(mods);

		int ans = sum;
		for (int i = 0; i < numE; i++) {
			if (mods[i] >= 0 && numK <= 0) {
				break;
			}
			
			ans += mods[i];
			numK--;
		}
		System.out.println(ans);
	}

}
