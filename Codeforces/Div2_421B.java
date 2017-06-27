import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_421B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		double tar = Double.parseDouble(inputData.nextToken());

		double incre = 180.0 / numV;

		int mult = (int) (tar / incre);

		if (mult == 0 || Math.abs(incre * (mult + 1) - tar) < Math.abs(incre * (mult) - tar)) {
			mult++;
		}

		mult = Math.min(mult, numV - 2);
		System.out.println("2 1 " + (2 + mult));
	}
}
