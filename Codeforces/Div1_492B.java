import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
public class Div1_492B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nP = Integer.parseInt(reader.readLine());
		int[] e = new int[nP * 2];
		
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for(int i = 0; i < nP*2; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		int ans = 0;
		for (int fI = 0; fI < nP * 2; fI += 2) {
			int cE = e[fI];
			int sI = fI + 1;
			while (e[sI] != cE) {
				sI++;
			}

			for (int i = sI; i - 1 >= fI; i--) {
				e[i] = e[i - 1];
				ans++;
			}
			ans--;
		}
		printer.println(ans);
		printer.close();
	}
}
