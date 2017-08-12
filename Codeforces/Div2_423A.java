import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_423A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nG = Integer.parseInt(inputData.nextToken());
		int nS = Integer.parseInt(inputData.nextToken());
		int nD = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());

		int nO = 0;
		int nR = 0;
		while (nG-- > 0) {
			if (inputData.nextToken().equals("1")) {
				if (nS > 0) {
					nS--;
				} else if (nD > 0) {
					nD--;
					nO++;
				} else if (nO > 0) {
					nO--;
				} else {
					nR++;
				}
			} else {
				if (nD > 0) {
					nD--;
				} else {
					nR += 2;
				}
			}
		}
		System.out.println(nR);
	}

}
