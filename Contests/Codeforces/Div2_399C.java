import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_399C {

	public static void main(String[] args) throws IOException {
		new Div2_399C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numT = Integer.parseInt(inputData.nextToken());
		int oNum = Integer.parseInt(inputData.nextToken());

		int[] cCount = new int[1024];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			cCount[Integer.parseInt(inputData.nextToken())]++;
		}
		reader.close();

		int[] nCount = new int[1024];
		for (int i = 0; i < numT; i++) {
			int numP = 0;
			for (int cNum = 0; cNum < 1024; cNum++) {
				if ((numP & 1) == 0) {
					nCount[cNum ^ oNum] += (cCount[cNum] + 1) / 2;
					nCount[cNum] += cCount[cNum] / 2;
				} else {
					nCount[cNum ^ oNum] += cCount[cNum] / 2;
					nCount[cNum] += (cCount[cNum] + 1) / 2;
				}
				numP += cCount[cNum];
			}
			int[] old = cCount;
			cCount = nCount;
			for (int j = 0; j < 1024; j++) {
				old[j] = 0;
			}
			nCount = old;
		}

		int min = 1024;
		int max = 0;
		for (int i = 0; i < 1024; i++) {
			if (cCount[i] > 0) {
				if (i < min) {
					min = i;
				}
				if (i > max) {
					max = i;
				}
			}
		}
		System.out.println(max + " " + min);
	}

}
