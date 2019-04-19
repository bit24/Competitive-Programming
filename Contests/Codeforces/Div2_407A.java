import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_407A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numT = Integer.parseInt(inputData.nextToken());
		int numC = Integer.parseInt(inputData.nextToken());

		int numP = 0;
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numT; i++) {
			numP += (Integer.parseInt(inputData.nextToken()) - 1) / numC + 1;
		}
		
		System.out.println((numP + 1) / 2);
	}

}
