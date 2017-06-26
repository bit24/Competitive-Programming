import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_420C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numE = Integer.parseInt(reader.readLine());
		Integer[] stackUno = new Integer[numE];
		int sLen = 0;

		int nRem = 1;
		int rCnt = 0;
		for (int i = 1; i <= 2 * numE; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("add")) {
				stackUno[sLen++] = Integer.parseInt(inputData.nextToken());
			} else {
				if (sLen != 0) {
					if (stackUno[sLen - 1] != nRem) {
						sLen = 0;
						rCnt++;
					} else {
						sLen--;
					}
				}
				nRem++;
			}
		}
		System.out.println(rCnt);
	}

}
